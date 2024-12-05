package com.example.fynda.screens

import androidx.navigation.NavController
import com.example.fynda.AuthViewModel
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fynda.AuthState
import com.example.fynda.R
import com.example.fynda.ui.theme.LexendFontFamily


@Composable
fun LoginScreen(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }

    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value) {
            is AuthState.Authenticated -> navController.navigate("Main")
            is AuthState.Error -> Toast.makeText(context, (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()
            else -> Unit
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = modifier
                .fillMaxWidth(0.75f)
                .padding(bottom = 16.dp),
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            Box(
                modifier = Modifier
                    .height(150.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fynda_logo2),
                    contentDescription = "Fynda Logo",
                    contentScale = ContentScale.FillBounds
                )
            }
        }
        Text(
            text="Welcome Back",
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(6.dp))

        Text(text = "Login to your account", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {email = it },leadingIcon = {
                Icon(imageVector = Icons.Rounded.Email, contentDescription = null)
            },
            label = {
                Text(text = "Email address", style = MaterialTheme.typography.labelSmall)
            },
            textStyle = MaterialTheme.typography.labelSmall.copy(
                fontFamily = LexendFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it }, leadingIcon = {
                Icon(imageVector = Icons.Rounded.Lock, contentDescription = null)
            },
            label = {
                Text(text = "Password", style = MaterialTheme.typography.labelSmall)
            },
            textStyle = MaterialTheme.typography.labelSmall.copy(
                fontFamily = LexendFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            authViewModel.login(email, password)
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.White
            ),
            enabled = authState.value != AuthState.Loading
            ) {
            Text(text = "Login", style = MaterialTheme.typography.labelLarge)
        }
        Spacer(modifier = Modifier.height(8.dp))
        TextButton(onClick = {
            navController.navigate("Signup")
        }) {
            Text(text = "Don't have an account? Sign Up.")
        }
        Spacer(modifier = Modifier.height(4.dp))
        TextButton(onClick = { }) {
            Text(text = "Forgot Password?")
        }
    }
}