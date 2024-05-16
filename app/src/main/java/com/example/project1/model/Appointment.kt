package com.example.project1.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Appointment (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name_pet") val namePet: String?,
    @ColumnInfo(name = "breed_pet") val breedPet: String?,
    @ColumnInfo(name = "name_owner") val nameOwner: String?,
    @ColumnInfo(name = "phone_number") val phoneNumber: String?,
    @ColumnInfo(name = "symptoms_pet") val symptomsPet: String?
) : Serializable



