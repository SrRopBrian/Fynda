package com.example.fynda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.fynda.ui.theme.FyndaTheme

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
