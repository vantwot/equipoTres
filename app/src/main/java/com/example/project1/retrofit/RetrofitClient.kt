package com.example.project1.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.example.project1.utils.Constants.BASE_URL

object RetrofitClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val consumeApi= retrofit.create(ConsumeApi::class.java)

}