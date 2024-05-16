package com.example.project1.repository

import android.content.Context
import com.example.project1.room.Appointment
import com.example.project1.room.AppointmentDB
import com.example.project1.room.AppointmentDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class repository (val context: Context) {
    private var appointmentDao: AppointmentDao = AppointmentDB.getDatabase(context).appointmentDao()

    suspend fun insertAppointment(appointment: Appointment) {
        withContext(Dispatchers.IO) {
            appointmentDao.insertAppointment(appointment)
        }
    }
}