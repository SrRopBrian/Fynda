package com.example.fynda.screens

import android.app.TimePickerDialog
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import java.util.Calendar
import androidx.activity.result.contract.ActivityResultContracts.PickMultipleVisualMedia
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fynda.AuthViewModel
import com.example.fynda.R

data class Service(
    val serviceName: String = "",
    val category: String = "",
    val description: String = "",
    val cost: Double = 0.0,
    val imageUrl: String? = null,
    val availableDays: String = "",
    val startTime: String = "",
    val endTime: String = ""
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceCategoryDropDown(
    categories: List<String>,
    onCategorySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {expanded = !expanded}
    ) {
        OutlinedTextField(
            value = selectedCategory,
            onValueChange = { },
            label = { Text("Category", style = MaterialTheme.typography.labelSmall)},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            categories.forEach { category ->
                DropdownMenuItem(
                    text = {Text(category, style = MaterialTheme.typography.bodyMedium)},
                    onClick = {
                        selectedCategory = category
                        onCategorySelected(category)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun AddServiceScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // state variables to capture user input
    var serviceName by remember { mutableStateOf("")}
    var category by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var cost by remember { mutableStateOf("") }
    var startTime by remember { mutableStateOf("") }
    var endTime by remember { mutableStateOf("") }
    var availableDays by remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    fun showTimePicker(onTimeSelected: (String) -> Unit) {
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            context,
            { _, hourOfDay, minute ->
                val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
                onTimeSelected(formattedTime)
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            false
        )
        timePickerDialog.show()
    }

    val pickMultipleMedia = rememberLauncherForActivityResult(
        contract = PickMultipleVisualMedia(),
        onResult = { uris ->
            if (uris.isNotEmpty()) {
                uris.forEach { uri ->
                    Log.d("PhotoPicker", "Selected URI: $uri")
                }
            } else {
                Log.d("PhotoPicker", "No media selected")
            }
        })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.services),
                contentDescription = "Services",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Add a New service", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
            value = serviceName,
            onValueChange = { serviceName = it },
            supportingText = { Text("e.g. Plumbing", style = MaterialTheme.typography.bodySmall)},
            label = { Text("Service Name", style = MaterialTheme.typography.labelSmall) },
            textStyle = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        val categories =
            listOf("Plumbing", "Electric Works", "Carpentry", "Cleaning", "Gardening", "Others")
        ServiceCategoryDropDown(categories = categories, onCategorySelected = { selectedCategory ->
            category = selectedCategory
        })
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            supportingText = { Text("e.g. Repair, Installation and Maintenance", style = MaterialTheme.typography.bodySmall) },
            label = { Text("Service Details", style = MaterialTheme.typography.labelSmall) },
            singleLine = false,
            maxLines = 5,
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = availableDays,
            onValueChange = { availableDays = it },
            supportingText = { Text("e.g. 'Monday - Friday'", style = MaterialTheme.typography.bodySmall) },
            label = { Text("Working Days", style = MaterialTheme.typography.labelSmall) },
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(0.78f),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Start-Time Picker
            OutlinedTextField(
                value = startTime,
                onValueChange = {}, // Read-only
                label = { Text("Start Time", style = MaterialTheme.typography.labelSmall ) },
                textStyle = MaterialTheme.typography.bodyMedium,
                trailingIcon = {
                    IconButton(onClick = {
                        showTimePicker { time ->
                            startTime = time
                        }
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Pick Start Time")
                    }
                },
                modifier = Modifier.weight(1f)
            )

            // End-Time Picker
            OutlinedTextField(
                value = endTime,
                onValueChange = {}, // Read-only
                label = { Text("End Time",  style = MaterialTheme.typography.labelSmall) },
                textStyle = MaterialTheme.typography.bodyMedium,
                trailingIcon = {
                    IconButton(onClick = {
                        showTimePicker { time ->
                            endTime = time
                        }
                    }) {
                        Icon(Icons.Default.Add, contentDescription = "Pick End Time")
                    }
                },
                modifier = Modifier.weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = cost,
            onValueChange = { cost = it },
            supportingText = { Text("e.g. Ksh. 1000", style = MaterialTheme.typography.bodySmall) },
            label = { Text("Service Cost",  style = MaterialTheme.typography.labelSmall) },
            textStyle = MaterialTheme.typography.bodyMedium,
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier
                .background(color = MaterialTheme.colorScheme.surface)
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                pickMultipleMedia.launch(
                    PickVisualMediaRequest(PickVisualMedia.ImageOnly)
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.secondary),
            shape = MaterialTheme.shapes.medium
        ) {
            Icon(Icons.Default.Add, contentDescription = "Pick Image")
            Text("Add Images", style = MaterialTheme.typography.labelSmall)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val service = Service(
                    serviceName = serviceName,
                    category = category,
                    description = description,
                    cost = cost.toDouble(),
                    availableDays = availableDays,
                    startTime = startTime,
                    endTime = endTime,
                    imageUrl = imageUri?.toString()
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary,
                contentColor = Color.White
            )
        ) {
            Text(text = "Save", style = MaterialTheme.typography.labelLarge)
        }
    }
}