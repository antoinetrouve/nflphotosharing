package com.invo.nflphotosharing.ui.feature.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.invo.nflphotosharing.R
import com.invo.nflphotosharing.ui.feature.home.model.MemoryView
import com.invo.nflphotosharingdomain.usecase.GetMemoriesUseCase
import com.invo.nflphotosharingdomain.usecase.MemoriesResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getRandomHomeImagesUseCase: GetMemoriesUseCase
) : AbstractHomeViewModel() {

    private val _state = MutableStateFlow(State())
    private val state = _state.asStateFlow()

    @Composable
    override fun getState(): State = state.collectAsState().value

    override fun loadImages() {
        _state.value = _state.value.copy(uiState = HomeUiState.Loading)
        viewModelScope.launch {
            when (val result = getRandomHomeImagesUseCase()) {
                is MemoriesResult.Success -> {
                    _state.value = _state.value.copy(
                        memories = result.memories.map {
                            MemoryView(
                                id = getDrawableResIdById(it.id),
                                imageName = it.imageName,
                                userName = it.userName,
                            )
                        },
                        uiState = HomeUiState.Success,
                        sideEffect = SideEffect.ShowImages
                    )
                }
                is MemoriesResult.Error -> {
                    _state.value = _state.value.copy(uiState = HomeUiState.Error)
                }
            }
        }
    }

    override fun consumeSideEffect() {
        _state.value = _state.value.copy(sideEffect = null)
    }

    private fun getDrawableResIdById(id: Int): Int {
        return when (id) {
            0 -> R.drawable.demo1
            1 -> R.drawable.demo2
            2 -> R.drawable.demo3
            3 -> R.drawable.demo4
            4 -> R.drawable.demo5
            5 -> R.drawable.demo6
            6 -> R.drawable.demo7
            7 -> R.drawable.demo8
            8 -> R.drawable.demo9
            9 -> R.drawable.demo10
            else -> R.drawable.logo
        }
    }

    data class State(
        val uiState: HomeUiState = HomeUiState.Idle,
        val sideEffect: SideEffect? = null,
        val memories: List<MemoryView> = emptyList(),
    )

    enum class SideEffect {
        ShowImages,
    }

    sealed class HomeUiState {
        data object Idle : HomeUiState()
        data object Loading : HomeUiState()
        data object Success : HomeUiState()
        data object Error : HomeUiState()
    }
}

abstract class AbstractHomeViewModel : ViewModel() {
    @Composable
    abstract fun getState(): HomeViewModel.State
    abstract fun loadImages()
    abstract fun consumeSideEffect()
}