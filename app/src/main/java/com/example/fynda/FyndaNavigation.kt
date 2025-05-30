package com.example.fynda

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fynda.screens.LoginScreen
import com.example.fynda.screens.SignupScreen

@Composable
fun FyndaNavigation(modifier: Modifier = Modifier,authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Login") {
        composable("Login") { LoginScreen(modifier, navController, authViewModel) }
        composable("Signup") { SignupScreen(modifier, navController, authViewModel) }

        composable("Main") {
            BottomNavigationScaffold(
                modifier = modifier,
                parentNavController = navController,
                authViewModel = authViewModel)
        }
    }
}
