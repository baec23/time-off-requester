package com.gausslab.timeoffrequester.repository

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
import java.lang.Exception

class TimeOffRequestRepository {
    private val collectionRef = Firebase.firestore.collection("TimeOffRequests")

    suspend fun saveTimeOffRequest(timeOffRequest: TimeOffRequest): Result<TimeOffRequest>{
        return try {
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

//    suspend fun getTimeOffRequestsForUser(userId: String) : Result<List<TimeOffRequest>>{
//        val timeOffRequestList: MutableList<TimeOffRequest> = mutableListOf()
//        val queryResult =
//            collectionRef
//                .whereEqualTo("userId", userId)
//                .get().await().documents
//        if (queryResult.isNotEmpty()){
//            for (i:Int in 1..queryResult.size){
//                val toAdd = queryResult[i].toObject<TimeOffRequest>()?.copy()
//                timeOffRequestList.add(toAdd!!)
//            }
//            return Result.success(timeOffRequestList)
//        }
//        return Result.failure(Exception("id랑 맞는 db가 없음"))
//    }
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