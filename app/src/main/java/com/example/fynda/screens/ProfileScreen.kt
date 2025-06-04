package com.example.fynda.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fynda.AuthViewModel
import com.example.fynda.R
import com.example.fynda.ui.theme.LexendFontFamily

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel
//    onSave: () -> Unit
) {
    val userProfile = authViewModel.userProfile.collectAsState().value
    var userName by remember { mutableStateOf(userProfile?.userName ?: "") }
    var phoneNumber by remember { mutableStateOf(userProfile?.phoneNumber ?: "") }
    var location by remember { mutableStateOf(userProfile?.location ?: "") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF8AD2FF)))
        {
            Image(
                painter = painterResource(id = R.drawable.fynda_logo2),
                contentDescription = "Fynda Logo",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier.align(Alignment.Center)
            )
            Image(
                painter = painterResource(id = R.drawable.user),
                contentDescription = "Fynda Logo",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(100.dp)
                    .offset( y= (100).dp)

            )
        }
        Spacer(modifier = Modifier.height(90.dp))

        Text(text = "Profile", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(16.dp))

        // user input fields
        OutlinedTextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Name", style = MaterialTheme.typography.labelSmall)
            },
            textStyle = MaterialTheme.typography.labelSmall.copy(
                fontFamily = LexendFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = phoneNumber,
            onValueChange = { phoneNumber = it },
            label = { Text("Phone Number", style = MaterialTheme.typography.labelSmall)
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
            onValueChange = { location = it },
            label = { Text("Location", style = MaterialTheme.typography.labelSmall)
            },
            textStyle = MaterialTheme.typography.labelSmall.copy(
                fontFamily = LexendFontFamily,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Save Button
        Button(
            onClick = {
                authViewModel.updateProfile(userName, phoneNumber, location)
//                onSave()
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.White
            )
        ) {
            Text("Save")
        }
    }

}
