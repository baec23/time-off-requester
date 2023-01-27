package com.gausslab.timeoffrequester.repository.datainterface

import com.gausslab.timeoffrequester.model.User

interface UserRepository {
    var currUser: User?
    suspend fun tryLogin(id: String, password: String) :Result<User>
    suspend fun tryAutoLogin(id: String) : Result<User>
    suspend fun reduceRemainingTimeOffRequests(id: String) : Result<User>
    suspend fun getUserById(userId: String) : User
    suspend fun saveUser(user:User)
}