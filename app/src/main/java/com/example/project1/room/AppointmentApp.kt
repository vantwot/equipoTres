package com.example.project1.room

import android.app.Application
import androidx.room.Room

class AppointmentApp:Application() {
    val room = Room
        .databaseBuilder(this, AppointmentDB::class.java, "appointment")
        .build()
}