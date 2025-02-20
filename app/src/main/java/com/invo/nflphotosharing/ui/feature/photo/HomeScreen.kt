package com.invo.nflphotosharing.ui.feature.photo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.invo.nflphotosharing.R
import com.invo.nflphotosharing.ui.designsystem.component.MemoryCard
import com.invo.nflphotosharing.ui.feature.photo.model.MemoryView
import com.invo.nflphotosharing.ui.theme.NFLPhotoSharingTheme
import com.invo.nflphotosharing.ui.theme.NFLWhite

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: AbstractHomeViewModel = hiltViewModel<HomeViewModel>()
) {
    val state = viewModel.getState()

    LaunchedEffect(Unit) {
        viewModel.loadImages()
    }

    when (state.uiState) {
        is HomeViewModel.HomeUiState.Loading -> LoadingScreen(modifier)
        is HomeViewModel.HomeUiState.Success -> ImageList(modifier, state.memories)
        is HomeViewModel.HomeUiState.Error -> ErrorScreen(modifier)
        else -> {} // Do nothing
    }
}

@Composable
fun ImageList(modifier: Modifier, images: List<MemoryView>) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(images) { memory ->
            MemoryCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
                memory = memory
            )
        }
    }
}

@Composable
fun LoadingScreen(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(
            color = NFLWhite,
        )
    }
}

@Composable
fun ErrorScreen(modifier: Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = stringResource(R.string.home_empty), color = NFLWhite)
    }
}

@Preview
@Composable
fun ImageListPreview() {
    NFLPhotoSharingTheme {
        val mockImages = listOf(
            MemoryView(
                id = R.drawable.demo1,
                imageName = "demo1",
                userName = "John Doe"
            ),
            MemoryView(
                id = R.drawable.demo2,
                imageName = "demo2",
                userName = "Jane Doe"
            ),
            MemoryView(
                id = R.drawable.demo3,
                imageName = "demo3",
                userName = "John Smith"
            )
        )

        ImageList(
            modifier = Modifier,
            images = mockImages
        )
    }
}

@Preview
@Composable
fun LoadingScreenPreview() {
    NFLPhotoSharingTheme {
        ErrorScreen(modifier = Modifier)
    }
}
