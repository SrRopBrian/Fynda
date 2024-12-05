package com.example.fynda

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState : LiveData<AuthState> = _authState

    private val _userProfile = MutableStateFlow<FyndaUser?>(null) // manages the user profile internally
    val userProfile: StateFlow<FyndaUser?> = _userProfile // observes the user profile and exposes it to 'outsiders'

    init {
        checkAuthStatus()
    }

    // check the current authentication status
    fun checkAuthStatus(){
        if(auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
            fetchUserProfile()
        }
    }

    private fun fetchUserProfile() {
        val userId = auth.currentUser?.uid ?: return
        firestore.collection("users").document(userId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val user = document.toObject(FyndaUser::class.java)
                    _userProfile.value = user
                } else {
                    _userProfile.value = null // No user profile found
                }
            }
            .addOnFailureListener {
                _userProfile.value = null
            }
    }

    fun updateProfile(userName: String, phoneNumber: String, location: String) {
        val userId = auth.currentUser?.uid ?: return

        val updatedData = mapOf(
            "userName" to userName,
            "phoneNumber" to phoneNumber,
            "location" to location
        )

        // make updates in Firestore
        firestore.collection("users").document(userId)
            .update(updatedData)
            .addOnSuccessListener {
                val updatedUser = _userProfile.value?.copy(
                    userName = userName,
                    phoneNumber = phoneNumber,
                    location = location
                )
                _userProfile.value = updatedUser
            }
            .addOnFailureListener { exception ->
                _authState.value = AuthState.Error(exception.message ?: "Failed to update profile. Please try again.")
            }
    }

    fun login(email : String, password : String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Provide your complete details.")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    _authState.value = AuthState.Authenticated
                    fetchUserProfile()
                } else {
                    _authState.value = AuthState.Error(task.exception?.message?:"Oops! Something went wrong!")
                }
            }
    }

    fun signup(userName: String, email : String, password : String, phoneNumber: String, location: String, role: String) {
        if (userName.isEmpty() ||email.isEmpty() || password.isEmpty() || phoneNumber.isEmpty() || location.isEmpty() || role.isEmpty()) {
            _authState.value = AuthState.Error("Please provide your complete details.")
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful){
                    val userId = auth.currentUser?.uid ?: ""
                    val newUser = FyndaUser(
                        uid = userId,
                        userName = userName,
                        email = email,
                        phoneNumber = phoneNumber,
                        location = location,
                        role = role
                    )
                    saveUserToFirestore(newUser)
                } else {
                    _authState.value = AuthState.Error(task.exception?.message?:"Oops! Something went wrong!")
                }
            }
    }

    private fun saveUserToFirestore(user: FyndaUser) {
        firestore.collection("users").document(user.uid)
            .set(user)
            .addOnSuccessListener {
                _userProfile.value = user
                _authState.value = AuthState.Authenticated
            }
            .addOnFailureListener { exception ->
                _authState.value = AuthState.Error(exception.message?:"Oops! Something went wrong!")}
    }

    fun signout() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
        _userProfile.value = null
    }
}

// User data class to represent user profiles
data class FyndaUser(
    val uid: String = "",
    val userName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val location: String = "",
    val role: String = ""
)

sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message : String) : AuthState()
}