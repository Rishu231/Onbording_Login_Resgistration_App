package com.example.softwarelab.ApiCall

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient


object ApiClient {
    private const val BASE_URL = "https://sowlab.com/assignment/"
    private val okHttpClient = OkHttpClient.Builder()
        .build()

    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}

