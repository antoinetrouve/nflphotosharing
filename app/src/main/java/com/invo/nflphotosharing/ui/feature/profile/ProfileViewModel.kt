package com.invo.nflphotosharing.ui.feature.profile

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.invo.nflphotosharingdomain.usecase.GetProfileUseCase
import com.invo.nflphotosharingdomain.usecase.LogoutUseCase
import com.invo.nflphotosharingdomain.usecase.ProfileResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val application: Application
) : AbstractProfileViewModel() {
    private val _state = MutableStateFlow(State())
    private val state = _state.asStateFlow()

    @Composable
    override fun getState(): State = state.collectAsState().value

    override fun load() {
        viewModelScope.launch {
            _state.value = _state.value.copy(uiState = ProfileUiState.Loading)
            when (val result = getProfileUseCase()) {
                is ProfileResult.Success -> {
                    // Take persistent permissions for each URI only for content URIs (Gallery
                    result.memories.forEach { uri ->
                        // Only take persistable permissions for content:// URIs (gallery)
                        if (uri.scheme == "content" && !uri.toString().startsWith("content://${application.packageName}")) {
                            try {
                                application.contentResolver.takePersistableUriPermission(
                                    uri,
                                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                                )
                            } catch (e: SecurityException) {
                                Log.w("ProfileViewModel", "Could not take persistable permission for $uri: ${e.message}")
                                _state.value = _state.value.copy(uiState = ProfileUiState.Error, username = result.username)
                            }
                        }
                    }
                    Log.d("ProfileViewModel", "Success: ${result.memories}")
                    _state.value = _state.value.copy(
                        uiState = ProfileUiState.Success,
                        memories = result.memories,
                        username = result.username
                    )
                }
                ProfileResult.Error -> {
                    _state.value = _state.value.copy(uiState = ProfileUiState.Error)
                }
            }

        }
    }

    override fun logout() {
        viewModelScope.launch {
            logoutUseCase()
            _state.value = _state.value.copy(uiState = ProfileUiState.Success, sideEffect = SideEffect.GoToLogin)
        }
    }

    data class State(
        val uiState: ProfileUiState = ProfileUiState.Idle,
        val sideEffect: SideEffect? = null,
        val memories: List<Uri> = emptyList(),
        val username: String = "",
    )

    enum class SideEffect {
        GoToLogin
    }

    sealed class ProfileUiState {
        data object Idle : ProfileUiState()
        data object Loading : ProfileUiState()
        data object Success : ProfileUiState()
        data object Error : ProfileUiState()
    }
}

abstract class AbstractProfileViewModel : ViewModel() {
    @Composable
    abstract fun getState(): ProfileViewModel.State
    abstract fun load()
    abstract fun logout()
}