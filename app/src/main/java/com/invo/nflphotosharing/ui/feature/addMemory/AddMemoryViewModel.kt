package com.invo.nflphotosharing.ui.feature.addMemory

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.invo.nflphotosharing.ui.feature.addMemory.AddMemoryViewModel.AddMemoryUiState
import com.invo.nflphotosharingdomain.usecase.SaveUserMemoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class AddMemoryViewModel @Inject constructor(
    private val saveUserMemoryUseCase: SaveUserMemoryUseCase,
    private val application: Application
) : AbstractAddMemoryViewModel() {

    private val _selectedImageUri = MutableStateFlow<Uri?>(null)
    override val selectedImageUri: StateFlow<Uri?> = _selectedImageUri

    private val _uiState = MutableStateFlow(State())
    override val uiState: StateFlow<State> = _uiState.asStateFlow()

    override fun selectImage(uri: Uri) {
        viewModelScope.launch {
            _selectedImageUri.value = uri
        }
    }

    override fun validateAndSaveImage() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(uiState = AddMemoryUiState.Saving)

            selectedImageUri.value?.let { uri ->
                if (uri.scheme == "content" && !uri.toString().startsWith("content://${application.packageName}")) {
                    try {
                        persistUri(uri)
                        saveImage(uri)
                    } catch (e: SecurityException) {
                        Log.w("AddMemoryViewModel", "Could not persist URI permission: ${e.message}")
                        _uiState.value = _uiState.value.copy(uiState = AddMemoryUiState.Error)
                    }
                } else {
                    saveImage(uri)
                }
            }
        }
    }

    override fun createImageFile(): File {
        val storageDir = application.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val imageFileName = "Memory_${timeStamp}.jpg"
        return File(storageDir, imageFileName)
    }

    private fun persistUri(uri: Uri) {
        application.contentResolver.takePersistableUriPermission(
            uri,
            Intent.FLAG_GRANT_READ_URI_PERMISSION
        )
    }

    private suspend fun saveImage(uri: Uri) {
        saveUserMemoryUseCase(uri)
            .onSuccess {
                _uiState.value = _uiState.value.copy(
                    uiState = AddMemoryUiState.Success,
                    sideEffect = SideEffect.GoToProfile
                )
                _selectedImageUri.value = null
            }
            .onFailure {
                _uiState.value = _uiState.value.copy(
                    uiState = AddMemoryUiState.Error,
                    sideEffect = null
                )
            }
    }

    data class State(
        val uiState: AddMemoryUiState = AddMemoryUiState.Idle,
        val sideEffect: SideEffect? = null,
    )

    sealed class AddMemoryUiState {
        data object Idle : AddMemoryUiState()
        data object Saving : AddMemoryUiState()
        data object Success : AddMemoryUiState()
        data object Error : AddMemoryUiState()
    }

    enum class SideEffect {
        GoToProfile,
    }
}

abstract class AbstractAddMemoryViewModel : ViewModel() {
    abstract val selectedImageUri: StateFlow<Uri?>
    abstract val uiState: StateFlow<AddMemoryViewModel.State>
    abstract fun selectImage(uri: Uri)
    abstract fun validateAndSaveImage()
    abstract fun createImageFile(): File
}