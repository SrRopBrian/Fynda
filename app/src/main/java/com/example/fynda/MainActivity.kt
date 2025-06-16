package com.example.fynda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.fynda.core.navigation.FyndaNavigation
import com.example.fynda.features.auth.AuthViewModel
import com.example.fynda.core.theme.FyndaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val authViewModel : AuthViewModel by viewModels()

        setContent {
            FyndaTheme {
                Scaffold (
                    modifier = Modifier.fillMaxSize(),
                    content = { padding ->
                        FyndaNavigation(
                            modifier = Modifier.padding(padding),
                            authViewModel = authViewModel)
                    }
                )
            }

        }
    }
}
