package com.gausslab.timeoffrequester.remote.api

import com.gausslab.timeoffrequester.model.TimeOffRequest
import com.gausslab.timeoffrequester.model.UserSavedDefaults
import com.gausslab.timeoffrequester.remote.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TorApi {
    @GET("user")
    suspend fun getUserByEmail(@Query("email") email: String): Response<User>
    @POST("user")
    suspend fun saveUser(@Body user: User): Response<User>
    @POST("sign-in")
    suspend fun signIn(@Query("email") email:String, @Query("auth-code") authCode: String? = null): Response<User>
    @POST("tor")
    suspend fun submitTimeOffRequest(@Body toAdd: TimeOffRequest): Response<TimeOffRequest>
    @GET("tor/user")
    suspend fun getRemainingTimeOffRequests(@Query("email") email: String): Response<String>
    @GET("tor")
    suspend fun getTimeOffRequestsByUser(@Query("email")email: String): Response<List<TimeOffRequest>>
    @GET("user/defaults")
    suspend fun getUserSavedDefaults(@Query("email")email: String):Response<UserSavedDefaults>
    @POST("user/defaults")
    suspend fun saveUserSavedDefaults(@Body userSavedDefaults: UserSavedDefaults): Response<UserSavedDefaults>
}