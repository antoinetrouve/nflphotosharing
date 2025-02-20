package com.invo.nflphotosharing.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.invo.nflphotosharing.ui.feature.login.LoginScreen
import com.invo.nflphotosharing.ui.feature.photo.HomeScreen

@Composable
fun AppNavigation(navController: NavHostController, modifier: Modifier = Modifier, startDestination: Screen) {
    NavHost(
        navController = navController,
        startDestination = startDestination.route
    ) {
        composable(Screen.Login.route) { LoginScreen {
            navController.navigate(Screen.Home.route) {
                popUpTo(Screen.Login.route) { inclusive = true }
            }
        } }
        composable(Screen.Home.route) { HomeScreen(modifier = modifier) }
        composable(Screen.Discovery.route) {  }
        composable(Screen.Add.route) {  }
        composable(Screen.Highlight.route) {  }
        composable(Screen.Profile.route) {  }
    }
}