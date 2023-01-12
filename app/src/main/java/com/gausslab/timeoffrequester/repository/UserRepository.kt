package com.gausslab.timeoffrequester.repository

import com.gausslab.timeoffrequester.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.tasks.await
import java.lang.Exception

@ActivityScoped
class UserRepository {
    var currUser: User? = null
        private set
    private val collectionRef = Firebase.firestore.collection("users")

    suspend fun tryLogin(id: String, password: String): Result<User> {
        val queryResult =
            collectionRef
                .whereEqualTo("id", id)
                .whereEqualTo("password", password)
                .get().await().documents
        if (queryResult.isNotEmpty()) {
            val doc = queryResult.first()
            val user = doc.toObject<User>()?.copy()
            return if (user != null) {
                currUser = user
                Result.success(user)
            } else {
                Result.failure(Exception("response 온 데이터 user로 바꾸는데 에러"))
            }
        }
        return Result.failure(Exception("id or password 잘못입력"))
    }

    suspend fun tryAutoLogin(id:String): Result<User>{
        val queryResult =
            collectionRef
                .whereEqualTo("id", id)
                .get().await().documents
        if (queryResult.isNotEmpty()) {
            val doc = queryResult.first()
            val user = doc.toObject<User>()?.copy()
            return if (user != null) {
                currUser = user
                Result.success(user)
            } else {
                Result.failure(Exception("response 온 데이터 user로 바꾸는데 에러"))
            }
        }
        return Result.failure(Exception("id or password 잘못입력"))
    }

    suspend fun reduceRemainingTimeOffRequests(id: String): Result<User> {
        val queryResult =
            collectionRef
                .whereEqualTo("id", id)
                .get().await().documents
        if (queryResult.isNotEmpty()) {
            val doc = queryResult.first()
            val user = doc.toObject<User>()?.copy(
                remainingTimeOffRequests = doc.getLong("remainingTimeOffRequests")!!.toInt() - 1
            )
            return if (user != null) {
                currUser = user
                collectionRef.document(doc.id).set(currUser!!).await()
                Result.success(user)

            } else {
                Result.failure(Exception("response 온 데이터 user로 바꾸는데 에러"))
            }
        }
        return Result.failure(Exception("id랑 맞는 db가 없음"))
    }

}