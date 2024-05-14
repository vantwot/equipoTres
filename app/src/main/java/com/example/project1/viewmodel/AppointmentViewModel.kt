package com.example.project1.viewmodel

import androidx.lifecycle.ViewModel
import android.util.Log
//import com.example.project1.model.BreedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppointmentViewModel : ViewModel() {
/**
    // Función para obtener la lista de razas de perros
    fun getDogBreeds(onSuccess: (List<String>) -> Unit, onError: (String) -> Unit) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/breeds/list/all")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(DogService::class.java)

        service.getBreeds().enqueue(object : Callback<BreedResponse> {
            override fun onResponse(call: Call<BreedResponse>, response: Response<BreedResponse>) {
                if (response.isSuccessful) {
                    val breeds = response.body()?.breeds ?: emptyList()
                    onSuccess(breeds)
                } else {
                    onError("Error en la respuesta de la API")
                }
            }

            override fun onFailure(call: Call<BreedResponse>, t: Throwable) {
                Log.e("AppointmentViewModel", "Error al llamar a la API: ${t.message}")
                onError("Error al llamar a la API")
            }
        })
    } */
}