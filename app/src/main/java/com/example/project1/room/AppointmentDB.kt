package com.example.project1.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [Appointment::class],
    version = 2
)
abstract class AppointmentDB : RoomDatabase() {
    abstract fun appointmentDao(): AppointmentDao

}