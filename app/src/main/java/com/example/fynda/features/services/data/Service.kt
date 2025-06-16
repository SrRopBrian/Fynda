package com.example.fynda.features.services.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "services")
data class Services (
    @PrimaryKey (autoGenerate = true) val id: String,
    val name: String,
    val category: String,
    val details: String?,
    val workingDays: String,
    val openingHours: Int,
    val closingHours: Int,
    val price: String,
    val userId: String
)