package com.gausslab.timeoffrequester.repository

import com.gausslab.timeoffrequester.model.TimeOffRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
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
}