package com.example.project1.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.project1.model.Appointment
import com.example.project1.repository.AppointmentRepository
import kotlinx.coroutines.launch
//import com.example.project1.model.BreedResponse

class AppointmentViewModel (application: Application) : AndroidViewModel(application) {
    val context = getApplication<Application>()
    private val appointmentRepository = AppointmentRepository(context)

    fun insertAppointment(appointment: Appointment) {
        viewModelScope.launch {
            try {
                appointmentRepository.insertAppointment(appointment)
            } catch (e: Exception) {
                Log.d("error: ", e.toString())
            }
        }
    }


    fun getAppointmentById(id: Int): Appointment {
        lateinit var appoint : Appointment
        viewModelScope.launch {
            try {
                appoint = appointmentRepository.getOneAppointment(id)
            } catch (e: Exception) {
                Log.d("error: ", e.toString())
            }
        }
        return appoint
    }

    fun updateAppointment(appointment: Appointment) {
        viewModelScope.launch {
            try {
                appointmentRepository.updateRepositoy(appointment)
            } catch (e: Exception) {
                Log.d("error: ", e.toString())
            }
        }
    }

}