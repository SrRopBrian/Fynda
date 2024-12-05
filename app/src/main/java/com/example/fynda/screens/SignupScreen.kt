package com.example.fynda.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fynda.AuthState
import com.example.fynda.AuthViewModel
import com.example.fynda.R
import com.example.fynda.ui.theme.LexendFontFamily

@Composable
fun SignupScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val scrollState = rememberScrollState()

    // State variables for user input
    var userName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var userRole by remember { mutableStateOf("Client") }

    // Observing authentication state
    val authState = authViewModel.authState.observeAsState()
    val context = LocalContext.current

    // Side effect for authentication state
    LaunchedEffect(authState.value) {
        when (authState.value) {
            // navigate to home if authenticated
            is AuthState.Authenticated -> navController.navigate("Home")
            // show error message id authentication failed
            is AuthState.Error -> Toast.makeText(
                context,
                (authState.value as AuthState.Error).message,
                Toast.LENGTH_SHORT
            ).show()
            else -> Unit
        }
    }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // logo card
        Card(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .padding(top = 20.dp, bottom = 16.dp),
            shape = RoundedCornerShape(15.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 5.dp
            )
        ) {
            Box(modifier = Modifier.height(150.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.fynda_logo2),
                    contentDescription = "Fynda Logo",
                    contentScale = ContentScale.FillBounds
                )
            }
        }

        Text(
            text = "Create a Fynda account",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        // User Inputs
        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            leadingIcon = {
                Icon(imageVector = Icons.Rounded.AccountCircle, contentDescription = null)
            },
            label = {
                Text(text = "Username", style = MaterialTheme.typography.labelSmall)
            },
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = LexendFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            leadingIcon = {
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
            onValueChange = {password = it},
            leadingIcon = {
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

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it},
            leadingIcon = {
                Icon(imageVector = Icons.Rounded.Call, contentDescription = null)
            },
            label = {
                Text(text = "Phone Number", style = MaterialTheme.typography.labelSmall)
            },
            textStyle = MaterialTheme.typography.labelSmall.copy(
                fontFamily = LexendFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = location,
            onValueChange = { location = it},
            leadingIcon = {
                Icon(imageVector = Icons.Rounded.Place, contentDescription = null)
            },
            label = {
                Text(text = "Location", style = MaterialTheme.typography.labelSmall)
            },
            textStyle = MaterialTheme.typography.labelSmall.copy(
                fontFamily = LexendFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

       // User Role selection
        Text(text = "Select Role", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(6.dp))
        val roles = listOf("Client", "Service Provider")
        roles.forEach { role ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(vertical = 2.dp)
                    .clickable { userRole = role },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = userRole == role,
                    onClick = { userRole = role }
                )
                Spacer(modifier = Modifier.width(2.dp))
                Text(text = role, style = MaterialTheme.typography.labelSmall)
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Signup Button
        Button(onClick = {
            authViewModel.signup(userName, email, password, phoneNumber, location, userRole)
        },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.White
            ),
            enabled = authState.value != AuthState.Loading
        ) {
            Text(text = "Create Account")
        }
        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = {
            navController.navigate("Login")
        }, modifier = Modifier.padding(bottom = 4.dp)) {
            Text(text = "Already have an account? Log In.")
        }
    }
}