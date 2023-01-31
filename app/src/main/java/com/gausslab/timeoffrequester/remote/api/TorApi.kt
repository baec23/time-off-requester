package com.gausslab.timeoffrequester.remote.api

import com.gausslab.timeoffrequester.model.TimeOffRequest2
import com.gausslab.timeoffrequester.remote.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TorApi {
    @GET("user")
    suspend fun getUserByEmail(@Query("email") email: String): Response<User>
    @POST("sign-in")
    suspend fun signIn(@Query("email") email:String, @Query("auth-code") authCode: String): Response<User>
    @POST("tor")
    suspend fun submitTimeOffRequest(@Body toAdd: TimeOffRequest2)
}