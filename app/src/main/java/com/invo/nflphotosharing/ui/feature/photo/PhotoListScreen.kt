package com.invo.nflphotosharing.ui.feature.photo

import androidx.compose.foundation.background
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.invo.nflphotosharing.ui.designsystem.theme.NFLPhotoSharingTheme
import com.invo.nflphotosharingdomain.model.Photo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun PhotoListScreen(
    modifier: Modifier = Modifier,
    viewModel: AbstractPhotoViewModel = hiltViewModel<PhotoViewModel>()
) {
    val photos by viewModel.photos.collectAsState()

    LazyColumn(modifier = modifier.background(MaterialTheme.colorScheme.background)) {
        items(photos) { photo ->
            Text(text = photo.url)
        }
    }
}

@Preview
@Composable
fun PhotoListScreenPreview() {
    NFLPhotoSharingTheme {
        PhotoListScreen(
            modifier = Modifier,
            viewModel = object : AbstractPhotoViewModel() {
                val mockData: StateFlow<List<Photo>> = MutableStateFlow(listOf(Photo(1, "photo 1"), Photo(2, "photo 2"), Photo(3, "photo 3")))
            override val photos: StateFlow<List<Photo>> = mockData
            override fun loadPhotos() {}
        })
    }
}