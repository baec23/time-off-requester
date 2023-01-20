package com.gausslab.timeoffrequester.server

import com.gausslab.timeoffrequester.model.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerUserRepository {
    private var retrofit: UserApi = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/api/v1/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(UserApi::class.java)

    suspend fun saveUser(toSave: User): User{
        val response = retrofit.saveUser(toSave = toSave)
        if(response.isSuccessful && response.body() != null){
            return response.body()!!
        }
        throw Exception()
    }
    suspend fun getUserById(userId: String): User{
        val response = retrofit.getUserById(userId = userId)
        if(response.isSuccessful && response.body() != null){
            return response.body()!!
        }
        throw Exception()
    }
}