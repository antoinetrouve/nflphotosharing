package com.invo.nflphotosharing.ui.feature.photo

import androidx.lifecycle.ViewModel
import com.invo.nflphotosharingdomain.model.Photo
import com.invo.nflphotosharingdomain.usecase.GetPhotosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val getPhotosUseCase: GetPhotosUseCase
) : AbstractPhotoViewModel() {
    private val _photos = MutableStateFlow<List<Photo>>(emptyList())
    override val photos: StateFlow<List<Photo>> = _photos

    init {
        loadPhotos()
    }

    override fun loadPhotos() {
        _photos.value = getPhotosUseCase()
    }
}

abstract class AbstractPhotoViewModel : ViewModel() {
    abstract val photos: StateFlow<List<Photo>>
    abstract fun loadPhotos()
}