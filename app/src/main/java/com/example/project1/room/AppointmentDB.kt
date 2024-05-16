package com.example.project1.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Appointment::class],
    version = 1
)
abstract class AppointmentDB : RoomDatabase() {
    abstract fun appointmentDao(): AppointmentDao
    companion object{
        fun getDatabase(context: Context): AppointmentDB {
            return Room.databaseBuilder(
                context.applicationContext,
                AppointmentDB::class.java,
                "AppointmentDB"
            ).build()
        }
    }

}