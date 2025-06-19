package com.example.fynda.features.services

import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel

data class Service(
    val serviceName: String = "",
    val category: String = "",
    val description: String = "",
    val cost: String = "",
    val availableDays: String = "",
    val openingHours: String = "",
    val closingHours: String = "",
    val imageUrl: List<String?>? = null,
    val userId: String = ""
)

sealed class AddServiceState {
    data class Success(val message: String): AddServiceState()
    data class Error(val message: String): AddServiceState()
}

class ServiceViewModel: ViewModel() {
    fun addService(
        serviceName: String,
        category: String,
        description: String,
        cost: String,
        availableDays: String,
        openingHours: String,
        closingHours: String,
        imageUris: List<Uri>?,
        userId: String
    ) {
        if (serviceName.isBlank() || category.isBlank() || description.isBlank() || cost.isBlank() || availableDays.isBlank() || openingHours.isBlank() || closingHours.isBlank()) {
            // ToDO: error message to user
        } else {
            val stringImageUris: List<String>? = imageUris?.map { it.toString() }

            val service = Service(
                serviceName = serviceName,
                category = category,
                description = description,
                cost = cost,
                availableDays = availableDays,
                openingHours = openingHours,
                closingHours = closingHours,
                imageUrl = stringImageUris,
                userId = userId
            )
        }
    }
}