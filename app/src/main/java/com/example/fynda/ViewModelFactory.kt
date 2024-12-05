package com.example.fynda

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.fynda.screens.Service
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class ViewModelFactory(
    private val authViewModel: AuthViewModel,
    private val repository: ServiceRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddServiceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AddServiceViewModel(authViewModel, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class ServiceRepository(
    private val firestore: FirebaseFirestore,
    private val authViewModel: AuthViewModel
) {
    fun getCurrentUserId(): String? {
        return authViewModel.userProfile.value?.uid
    }

    fun addServiceToFirestore(service: Service, onSuccess: () -> Unit, onError: (String) -> Unit) {
        val userId = getCurrentUserId()
        if (userId == null) {
            onError("You must be logged in to add a service.")
            return
        }

        val serviceData = hashMapOf(
            "userId" to userId,
            "serviceName" to service.serviceName,
            "category" to service.category,
            "description" to service.description,
            "cost" to service.cost,
            "imageUrl" to service.imageUrl,
            "availableDays" to service.availableDays,
            "startTime" to service.startTime,
            "endTime" to service.endTime
        )

        firestore.collection("Services")
            .add(serviceData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e.message ?: "An error occurred!") }
    }
}