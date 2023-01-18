package com.gausslab.timeoffrequester.retrofit

import android.net.wifi.hotspot2.pps.Credential
import com.gausslab.timeoffrequester.util.API
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.services.sheets.v4.SheetsScopes
import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {

//    @GET(API.SEARCH_PHOTOS)
//    fun searchPhotos(@Query("query")searchTerm: String) : Call<JsonElement>

    @GET(API.SEARCH_PHOTOS)
    fun getGoogleSheet(): Call<JsonElement>


//    @GET("/save/newTimeOffRequest")
//    fun saveNewTimeOffRequest()
}