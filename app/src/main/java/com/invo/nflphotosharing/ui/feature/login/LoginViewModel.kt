package com.invo.nflphotosharing.ui.feature.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.invo.nflphotosharing.ui.feature.login.LoginViewModel.State
import com.invo.nflphotosharingdomain.usecase.LoginResult
import com.invo.nflphotosharingdomain.usecase.LoginUseCase
import com.invo.nflphotosharingdomain.usecase.SetUserSessionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val setUserSessionUseCase: SetUserSessionUseCase
) : AbstractLoginViewModel() {

    private val _state = MutableStateFlow(State())
    private val state = _state.asStateFlow()

    @Composable
    override fun getState(): State = state.collectAsState().value

    override fun login(username: String, password: String) {
        _state.value = _state.value.copy(uiState = LoginUiState.Loading)

        viewModelScope.launch {
            val result = loginUseCase(username, password)
            when (result) {
                is LoginResult.Success -> {
                    setUserSessionUseCase(isLoggedIn = true)
                    _state.value = _state.value.copy(
                        uiState = LoginUiState.Success,
                        sideEffect = SideEffect.NavigateToHome
                    )
                }

                is LoginResult.InvalidUsername -> {
                    _state.value = _state.value.copy(uiState = LoginUiState.Error.InvalidUsername)
                }

                is LoginResult.InvalidPassword -> {
                    _state.value = _state.value.copy(uiState = LoginUiState.Error.InvalidPassword)
                }

                is LoginResult.GenericError -> {
                    _state.value = _state.value.copy(uiState = LoginUiState.Error.GenericError)
                }
            }
        }
    }

    override fun consumeSideEffect() {
        _state.value = _state.value.copy(sideEffect = null)
    }

    data class State(
        val uiState: LoginUiState = LoginUiState.Idle,
        val sideEffect: SideEffect? = null,
    )

    enum class SideEffect {
        NavigateToHome,
    }

    sealed class LoginUiState {
        data object Idle : LoginUiState()
        data object Loading : LoginUiState()
        data object Success : LoginUiState()
        sealed class Error : LoginUiState() {
            data object GenericError : Error()
            data object InvalidUsername : Error()
            data object InvalidPassword : Error()
        }
    }
}

abstract class AbstractLoginViewModel : ViewModel() {
    @Composable
    abstract fun getState(): State
    abstract fun login(username: String, password: String)
    abstract fun consumeSideEffect()
}