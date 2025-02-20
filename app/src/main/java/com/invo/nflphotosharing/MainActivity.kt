package com.invo.nflphotosharing

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.invo.nflphotosharing.ui.navigation.AppNavigation
import com.invo.nflphotosharing.ui.navigation.BottomNavigationBar
import com.invo.nflphotosharing.ui.navigation.Screen
import com.invo.nflphotosharing.ui.navigation.bottomNavItems
import com.invo.nflphotosharing.ui.designsystem.theme.NFLPhotoSharingTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        requestPermissions()
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
                                .background(Color.Black)
                                .padding(innerPadding),
                            startDestination = if (state.isUserLoggedIn) Screen.Home else Screen.Login
                        )
                    }
                }
            }
        }
    }

    private fun requestPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(READ_MEDIA_IMAGES, READ_EXTERNAL_STORAGE, READ_MEDIA_VIDEO),
                    READ_MEDIA_IMAGES_REQUEST_CODE
                )
            }
        }
    }

    companion object {
        private const val READ_MEDIA_IMAGES_REQUEST_CODE = 1
    }
}