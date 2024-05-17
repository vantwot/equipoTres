package com.example.project1.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.project1.model.Appointment
import androidx.room.OnConflictStrategy

@Dao
interface AppointmentDao {

    @Query("SELECT * FROM Appointment")
    suspend fun getAllAppointment(): MutableList<Appointment>

    @Query("SELECT * FROM Appointment WHERE id = :id")
    suspend fun getAppointmentById(id: Int): Appointment

    @Update
    suspend fun updateAppointment(appointment: Appointment)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: Appointment)

    @Delete
    suspend fun deleteAppointment(appointment: Appointment)
}