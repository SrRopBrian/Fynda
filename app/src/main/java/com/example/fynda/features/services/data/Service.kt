package com.example.fynda.features.services.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "services")
data class Service (
    @PrimaryKey (autoGenerate = true)
    val id: String,

    val serviceName: String,
    val category: String,
    val description: String,
    val cost: String,
    val availableDays: String,
    val openingHours: Int,
    val closingHours: Int,
    val imageUrl: String?,
    val userId: String
)