package com.example.project1.retrofit

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ConsumeApi {
    @GET("breeds/list")
    fun getBring(): Call<Breed>

    @GET("breed/{breed}/images/random")
    fun getRandomDogImage(@Path("breed") breed: String): Call<ImagenResponse>
}