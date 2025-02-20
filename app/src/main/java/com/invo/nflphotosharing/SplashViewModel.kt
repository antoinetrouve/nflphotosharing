package com.invo.nflphotosharing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.invo.nflphotosharingdomain.usecase.GetUserSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getUserSessionUseCase: GetUserSessionUseCase
) : AbstractSplashViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    @Composable
    fun getState(): State = state.collectAsState().value

    override fun loadUserSession() {
        viewModelScope.launch {
            delay(1000) // Simulate some loading time
            val isUserLoggedIn = getUserSessionUseCase().first()
            _state.value = _state.value.copy(
                screenState = ScreenState.Success,
                sideEffect = if (isUserLoggedIn) SideEffect.NavigateToHome else null,
                isUserLoggedIn = isUserLoggedIn
            )
        }
    }

    data class State(
        val screenState: ScreenState = ScreenState.Loading,
        val sideEffect: SideEffect? = null,
        val isUserLoggedIn: Boolean = false
    )

    enum class SideEffect {
        NavigateToHome,
    }

    enum class ScreenState {
        Loading,
        Success,
    }

}

abstract class AbstractSplashViewModel : ViewModel() {
    abstract fun loadUserSession()
}