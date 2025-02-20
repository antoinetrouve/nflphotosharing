package com.invo.nflphotosharing.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.invo.nflphotosharing.ui.feature.addMemory.AddMemoryScreen
import com.invo.nflphotosharing.ui.feature.login.LoginScreen
import com.invo.nflphotosharing.ui.feature.home.HomeScreen
import com.invo.nflphotosharing.ui.feature.profile.ProfileScreen

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
        composable(Screen.Add.route) {
            AddMemoryScreen(
                modifier = modifier,
                onPhotoSaved = { navController.navigate(Screen.Profile.route) }
            )
        }
        composable(Screen.Profile.route) {
            ProfileScreen(
                modifier = modifier,
                onLogout = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Profile.route) { inclusive = true }
                    }
                }
            )
        }
    }
}