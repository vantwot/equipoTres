package com.example.project1.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.project1.repository.repository
import com.example.project1.room.Appointment
import kotlinx.coroutines.launch
//import com.example.project1.model.BreedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppointmentViewModel (application: Application) : AndroidViewModel(application) {
    val context = getApplication<Application>()
    private val appointmentRepository = repository(context)

    fun insertAppointment(appointment: Appointment) {
        viewModelScope.launch {
            try {
                appointmentRepository.insertAppointment(appointment)
            } catch (e: Exception) {
                Log.d("error: ", e.toString())
            }
        }
    }

}