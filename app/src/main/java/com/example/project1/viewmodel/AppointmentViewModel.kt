package com.example.project1.viewmodel

import androidx.lifecycle.ViewModel
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.project1.model.Appointment
import kotlinx.coroutines.launch
//import com.example.project1.model.BreedResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppointmentViewModel : ViewModel() {
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

    fun updateInventory(inventory: Appointment) {
        viewModelScope.launch {
            _progresState.value = true
            try {
                appointmentRepository.updateRepositoy(inventory)
                _progresState.value = false
            } catch (e: Exception) {
                _progresState.value = false
            }
        }
    }

}