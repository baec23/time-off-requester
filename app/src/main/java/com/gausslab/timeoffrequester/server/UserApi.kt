package com.gausslab.timeoffrequester.server

import com.gausslab.timeoffrequester.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserApi {
    @POST("user")
    suspend fun saveUser(@Body toSave: User): Response<User>
    @GET
    suspend fun getUserById(@Path("userId") userId:String) : Response<User>
}