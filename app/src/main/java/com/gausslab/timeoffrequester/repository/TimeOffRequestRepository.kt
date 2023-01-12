package com.gausslab.timeoffrequester.repository

import android.util.Log
import com.gausslab.timeoffrequester.model.TimeOffRequest
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.lang.Integer.parseInt

class TimeOffRequestRepository {
    private val collectionRef = Firebase.firestore.collection("TimeOffRequests")

    fun saveNewTimeOffRequest(timeOffRequest: TimeOffRequest) {
        val docRef =
            Firebase.firestore.collection("TimeOffRequestKey").document("TimeOffRequestKey")

        Firebase.firestore.runTransaction { transaction ->
            val currKey = transaction.get(docRef).getLong("key")!!
            transaction.update(docRef, "key", currKey + 1)
            currKey
        }.addOnSuccessListener { updatedKey ->
            collectionRef.add(timeOffRequest.copy(timeOffRequestId = updatedKey.toInt()))
        }
    }

    fun getAllTimeOffRequests(): Flow<List<TimeOffRequest>> {
        return collectionRef.snapshotFlow().map {
            it.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject<TimeOffRequest>()
            }
        }
    }
    suspend fun getTimeOffRequestById(timeOffRequestId: String): TimeOffRequest {
        val snapshot =
            collectionRef.whereEqualTo("timeOffRequestId", parseInt(timeOffRequestId)).get().await()
        return snapshot.documents.first().toObject<TimeOffRequest>()
            ?: throw NoSuchElementException()
    }
}
fun Query.snapshotFlow(): Flow<QuerySnapshot> = callbackFlow {
    val listenerRegistration = addSnapshotListener { value, error ->
        if (error != null) {
            close()
            return@addSnapshotListener
        }
        if (value != null)
            trySend(value)
    }
    awaitClose {
        listenerRegistration.remove()
    }
}