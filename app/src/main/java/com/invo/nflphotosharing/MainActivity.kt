package com.invo.nflphotosharing

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.invo.nflphotosharing.ui.navigation.AppNavigation
import com.invo.nflphotosharing.ui.navigation.BottomNavigationBar
import com.invo.nflphotosharing.ui.navigation.Screen
import com.invo.nflphotosharing.ui.designsystem.theme.NFLPhotoSharingTheme
import com.invo.nflphotosharing.ui.navigation.bottomNavItems
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            splashViewModel.state.value.screenState == SplashViewModel.ScreenState.Loading
        }

        enableEdgeToEdge()

        splashViewModel.loadUserSession()
        setContent {
            NFLPhotoSharingTheme {
                val navController = rememberNavController()
                val state = splashViewModel.getState()

                LaunchedEffect(state.sideEffect) {
                    when (state.sideEffect) {
                        SplashViewModel.SideEffect.NavigateToHome -> {
                            navController.navigate(Screen.Home.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }

                        null -> { /* No action needed */
                        }
                    }
                }

                if (state.screenState == SplashViewModel.ScreenState.Success) {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            if (state.isUserLoggedIn || navController.currentBackStackEntryAsState().value?.destination?.route in bottomNavItems.map { it.route }) {
                                BottomNavigationBar(navController)
                            }
                        }
                    ) { innerPadding ->
                        AppNavigation(
                            navController = navController,
                            modifier = Modifier
                                .padding(innerPadding)
                                .navigationBarsPadding(),
                            startDestination = if (state.isUserLoggedIn) Screen.Home else Screen.Login
                        )
                    }
                }
            }
        }
    }
}