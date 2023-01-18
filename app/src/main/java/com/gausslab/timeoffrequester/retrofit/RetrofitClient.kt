package com.gausslab.timeoffrequester.retrofit

import android.util.Log
import com.gausslab.timeoffrequester.util.API
import com.gausslab.timeoffrequester.util.isJsonArray
import com.gausslab.timeoffrequester.util.isJsonObject
import com.google.api.Http
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private var retrofitClient: Retrofit?=null
    
    fun getClient(baseUrl: String) : Retrofit?{
        Log.d("RetrofitClient", "getClient: called")

        val client = OkHttpClient.Builder()

        val loggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger{
            override fun log(message: String) {
//                Log.d("RetrofitClient", "log: / message: $message")
                when{
                    message.isJsonObject()->
                        Log.d("RetrofitClient", JSONObject(message).toString(4))
                    message.isJsonArray()->
                        Log.d("RetrofitClient", JSONObject(message).toString(4))
                    else ->{
                        try {
                            Log.d("RetrofitClient", JSONObject(message).toString(4))
                        }catch (e: Exception){
                            Log.d("RetrofitClient", message)
                        }
                    }
                }
            }
        })

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        client.addInterceptor(loggingInterceptor)

        val baseParameterInterceptor : Interceptor = (Interceptor { chain ->
            Log.d("RetrofitClient", "intercept: ")
            val originalRequest = chain.request()
            val addedUrl = originalRequest.url.newBuilder().addQueryParameter("client_id", API.CLIENT_ID).build()
            val finalRequest = originalRequest.newBuilder().url(addedUrl).method(originalRequest.method, originalRequest.body).build()
            chain.proceed(finalRequest)
        })

        client.addInterceptor(baseParameterInterceptor)



        if(retrofitClient == null){
            retrofitClient = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())

                .client(client.build())


                .build()
        }
        return retrofitClient
    }
}