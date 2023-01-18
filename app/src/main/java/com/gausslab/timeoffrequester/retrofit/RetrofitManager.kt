package com.gausslab.timeoffrequester.retrofit

import android.net.wifi.hotspot2.pps.Credential
import android.util.Log
import com.gausslab.timeoffrequester.util.API
import com.gausslab.timeoffrequester.util.RESPONSE_STATE
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.HttpRequestInitializer
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.sheets.v4.Sheets
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.api.services.sheets.v4.model.ValueRange
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDateTime

class RetrofitManager {
    companion object {
        val instance = RetrofitManager()
    }

    private val retrofitInterface: RetrofitInterface? =
        RetrofitClient.getClient(API.BASE_URL)?.create(RetrofitInterface::class.java)

//    fun searchPhotos(searchTerm: String?, completion: (RESPONSE_STATE, String) -> Unit) {
//
//        val term = searchTerm ?: ""
//        val call: Call<JsonElement> = retrofitInterface?.searchPhotos(searchTerm = term) ?: return
//
//        call.enqueue(object : retrofit2.Callback<JsonElement> {
//            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
//                Log.d("RetrofitManager", "onResponse: /response: ${response.body()}")
//                completion(RESPONSE_STATE.OK, response.body().toString())
//            }
//
//            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
//                Log.d("RetrofitManager", "onFailure: /t: $t")
//                completion(RESPONSE_STATE.FAIL, t.toString())
//            }
//
//        })
//    }

    fun getGoogleSheet(completion: (RESPONSE_STATE, String) -> Unit){
        val call: Call<JsonElement> = retrofitInterface?.getGoogleSheet()?:return

        call.enqueue(object :retrofit2.Callback<JsonElement>{
            override fun onResponse(call: Call<JsonElement>, response: Response<JsonElement>) {
                Log.d("RetrofitManager", "onResponse: /response: ${response.body()}")
                completion(RESPONSE_STATE.OK, response.body().toString())
            }

            override fun onFailure(call: Call<JsonElement>, t: Throwable) {
                Log.d("RetrofitManager", "onFailure: /t: $t")
                completion(RESPONSE_STATE.FAIL, t.toString())
            }

        })
    }


}
