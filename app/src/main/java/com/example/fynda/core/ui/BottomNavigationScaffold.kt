package com.example.fynda.core.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fynda.R
import com.example.fynda.features.auth.AuthViewModel
import com.example.fynda.features.profile.ProfileScreen
import com.example.fynda.features.services.presentation.AddServiceScreen
import com.example.fynda.features.home.HomeScreen

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@Composable
fun currentRoute(navController: NavController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@Composable
fun BottomNavigationScaffold(
    modifier: Modifier = Modifier,
    parentNavController: NavController,
    authViewModel: AuthViewModel
) {
    val childNavController = rememberNavController()
    val currentRoute = currentRoute(childNavController)

    val bottomNavItems = listOf(
        BottomNavigationItem(
            title = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        ),
        BottomNavigationItem(
            title = "Services",
            selectedIcon = ImageVector.vectorResource(id = R.drawable.repair_tool_filled),
            unselectedIcon = ImageVector.vectorResource(id = R.drawable.repair_tool_outline)
        ),
        BottomNavigationItem(
            title = "Profile",
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person
        )
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                modifier = Modifier.height(60.dp),
                containerColor = Color(0xFFD8EDFF)
            ) {
                bottomNavItems.forEachIndexed { _, item ->
                    NavigationBarItem(
                        selected = currentRoute == item.title,
                        onClick = {
                            if (currentRoute != item.title) {
                                childNavController.navigate(item.title) {
                                    popUpTo(childNavController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (currentRoute == item.title)
                                    item.selectedIcon
                                else
                                    item.unselectedIcon,
                                contentDescription = item.title,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = { Text(item.title, style = MaterialTheme.typography.labelSmall) },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color(0xFFB7DDFC)
                        )
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = childNavController,
            startDestination = "Home",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("Home") {
                HomeScreen(modifier = modifier, navController = parentNavController, authViewModel = authViewModel)
            }
            composable("Services") {
                AddServiceScreen(navController = parentNavController, authViewModel = authViewModel)
            }
            composable("Profile") {
                ProfileScreen(modifier = modifier, navController = parentNavController, authViewModel = authViewModel)
            }
        }
    }
}