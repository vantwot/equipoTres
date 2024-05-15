package com.example.project1.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Appointment (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val name_pet: String,
    val breed: String,
    val name_owner: String,
    val phone_number: String,
    val symptoms: String,
)

