package com.gausslab.timeoffrequester.repository

import com.gausslab.timeoffrequester.repository.datainterface.TimeOffRequestRepository
import com.gausslab.timeoffrequester.model.TimeOffRequest
import com.gausslab.timeoffrequester.util.snapshotFlow
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import java.lang.Integer.parseInt

class TimeOffRequestRepositoryImpl : TimeOffRequestRepository {
    private val collectionRef = Firebase.firestore.collection("TimeOffRequests")

    override fun saveNewTimeOffRequest(timeOffRequest: TimeOffRequest) {
        val docRef =
            Firebase.firestore.collection("TimeOffRequestKey").document("TimeOffRequestKey")

        Firebase.firestore.runTransaction { transaction ->
            val currKey = transaction.get(docRef).getLong("key")!!
            transaction.update(docRef, "key", currKey + 1)
            currKey
        }.addOnSuccessListener {
        }
    }

    override fun getAllTimeOffRequests(): Flow<List<TimeOffRequest>> {
        return collectionRef.snapshotFlow().map {
            it.documents.mapNotNull { documentSnapshot ->
                documentSnapshot.toObject<TimeOffRequest>()
            }
        }
    }
    override suspend fun getTimeOffRequestById(timeOffRequestId: String): TimeOffRequest {
        val snapshot =
            collectionRef.whereEqualTo("timeOffRequestId", parseInt(timeOffRequestId)).get().await()
        return snapshot.documents.first().toObject<TimeOffRequest>()
            ?: throw NoSuchElementException()
    }

}
