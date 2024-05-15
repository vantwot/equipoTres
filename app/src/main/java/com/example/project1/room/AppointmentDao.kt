package com.example.project1.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface AppointmentDao {
    @Query("SELECT * FROM Appointment")
    suspend fun getAll():List<Appointment>

    @Query("SELECT * FROM Appointment WHERE id = :id")
    suspend fun getAppointmentById(id: Int): Appointment

    @Update
    suspend fun updateAppointment(appointment: Appointment)

    @Insert
    suspend fun insertAppointment(appointment: Appointment)

    @Delete
    suspend fun deleteAppointment(appointment: Appointment)
}