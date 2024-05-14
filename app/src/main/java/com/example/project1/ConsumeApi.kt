package com.example.project1

import retrofit2.Call
import retrofit2.http.GET

interface ConsumeApi {
    @GET("breeds/list")
    fun getBring(): Call<Breed>

}