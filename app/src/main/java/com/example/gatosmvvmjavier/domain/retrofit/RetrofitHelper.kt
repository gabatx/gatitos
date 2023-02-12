package com.example.gatosmvvmjavier.domain.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private var retrofitService: RetrofitService? = null
    private const val URL_BASE = "https://api.thecatapi.com/v1/"

    fun getRetrofit(): RetrofitService {
        if (retrofitService == null) {
            val retrofit = Retrofit.Builder()
                .baseUrl(URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofitService = retrofit.create(RetrofitService::class.java)
        }
        return retrofitService!!
    }
}