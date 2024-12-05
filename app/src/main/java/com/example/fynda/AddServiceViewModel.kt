package com.example.fynda

import androidx.lifecycle.ViewModel
import com.example.fynda.screens.Service
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class AddServiceState {
    object Idle : AddServiceState()
    object Loading : AddServiceState()
    data class Success(val message: String) : AddServiceState()
    data class Error(val message: String) : AddServiceState()
}

class AddServiceViewModel(private val authViewModel: AuthViewModel, private val repository: ServiceRepository) : ViewModel() {
    // UI State flow to handle loading, success or error status
    private val _uiState = MutableStateFlow<AddServiceState>(AddServiceState.Idle) //internal state
    val uiState = _uiState // exposes state

    fun addService(service: Service) {
        _uiState.value = AddServiceState.Loading

        repository.addServiceToFirestore(
            service,
            onSuccess = { _uiState.value = AddServiceState.Success("Service added successfully") },
            onError = { message -> _uiState.value = AddServiceState.Error(message) }
        )

    }
}
