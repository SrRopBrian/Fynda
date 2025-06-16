package com.example.fynda.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fynda.core.ui.BottomNavigationScaffold
import com.example.fynda.features.auth.AuthViewModel
import com.example.fynda.features.auth.LoginScreen
import com.example.fynda.features.auth.SignupScreen

@Composable
fun FyndaNavigation(modifier: Modifier = Modifier,authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "Login") {
        composable("Login") { LoginScreen(modifier, navController, authViewModel) }
        composable("Signup") { SignupScreen(modifier, navController, authViewModel) }

        composable("Home") {
            BottomNavigationScaffold(
                modifier = modifier,
                parentNavController = navController,
                authViewModel = authViewModel)
        }
    }
}
