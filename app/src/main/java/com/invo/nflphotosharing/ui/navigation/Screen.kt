package com.invo.nflphotosharing.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val icon: ImageVector, val label: String) {
    data object Home : Screen("home", Icons.Filled.Home, "Home")
    data object Discovery : Screen("discovery", Icons.Filled.Search, "Discovery")
    data object Add : Screen("add", Icons.Filled.Add, "Add")
    data object Highlight : Screen("highlight", Icons.Filled.Star, "Highlight")
    data object Profile : Screen("profile", Icons.Filled.AccountCircle, "Profile")
}

val bottomNavItems = listOf(
    Screen.Home,
    Screen.Discovery,
    Screen.Add,
    Screen.Highlight,
    Screen.Profile
)