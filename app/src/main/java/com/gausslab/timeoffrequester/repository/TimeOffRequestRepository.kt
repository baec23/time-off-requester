package com.gausslab.timeoffrequester.repository

import android.util.Log
import com.gausslab.timeoffrequester.model.TimeOffRequest
import com.google.android.gms.tasks.OnSuccessListener
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
import java.lang.Exception

class TimeOffRequestRepository {
    private val collectionRef = Firebase.firestore.collection("TimeOffRequests")
    private var currTimeOffRequestId: Int=0

    private suspend fun getKey(): Int{
        var currKey = 0
        val docRef = Firebase.firestore.collection("TimeOffRequestKey").document("TimeOffRequestKey")
        Firebase.firestore.runTransaction { transaction ->
            transaction.get(docRef).






            currKey = transaction.get(docRef).getDouble("key")!!.toInt()
            transaction.update(docRef, "key", currKey + 1)
            return@runTransaction currKey
        }
        return currKey
    }

    suspend fun saveTimeOffRequest(timeOffRequest: TimeOffRequest): Result<TimeOffRequest>{
        return try {
            timeOffRequest.timeOffRequestId=getKey()
            Log.d("asdfasfdfasdfasdf", "saveTimeOffRequest: " + getKey())
            val savedTimeOffRequest = collectionRef.add(timeOffRequest).await()
            Result.success(savedTimeOffRequest.get().await().toObject(TimeOffRequest::class.java)!!)
        }catch (e: Exception){
            Result.failure(Exception("failed to save timeOffRequest info"))
        }
    }

    fun getAllTimeOffRequests(): Flow<List<TimeOffRequest>> {
        return collectionRef.snapshotFlow().map {
            it.documents.mapNotNull { documentSnapshot->
                documentSnapshot.toObject<TimeOffRequest>()
            }
        }
    }

    fun setTimeOffRequestId(id: Int){
        currTimeOffRequestId=id
    }
    fun getTimeOffRequestId(): Int{
        return currTimeOffRequestId
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