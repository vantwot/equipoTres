package com.example.project1.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Appointment (
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var photo: String,
    val name_pet: String,
    val breed: String,
    val name_owner: String,
    val phone_number: String,
    val symptoms: String,
) : Serializable



