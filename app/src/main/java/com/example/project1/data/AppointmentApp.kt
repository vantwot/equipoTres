package com.example.project1.room

import android.app.Application
import androidx.room.Room
import com.example.project1.data.AppointmentDB

class AppointmentApp:Application() {
    val room = Room
        .databaseBuilder(this, AppointmentDB::class.java, "appointment")
        .build()
}