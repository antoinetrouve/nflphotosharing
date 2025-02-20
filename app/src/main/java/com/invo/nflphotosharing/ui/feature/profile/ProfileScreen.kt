package com.invo.nflphotosharing.ui.feature.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.invo.nflphotosharing.ui.feature.profile.ProfileViewModel.ProfileUiState

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    viewModel: AbstractProfileViewModel = hiltViewModel<ProfileViewModel>(),
    onLogout: () -> Unit = {}
) {
    val state = viewModel.getState()

    LaunchedEffect(Unit) {
        viewModel.load()
    }

    LaunchedEffect(state.sideEffect) {
        state.sideEffect?.let {
            if (it == ProfileViewModel.SideEffect.GoToLogin) onLogout()
        }
    }

    Column(
        modifier = modifier.fillMaxSize()
    ) {

        when (state.uiState) {
            ProfileUiState.Loading -> {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                )
            }

            ProfileUiState.Error -> {
                Text(
                    text = "No NFL sharing",
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineMedium
                )
            }

            else -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Welcome ${state.username}",
                        textAlign = TextAlign.Start,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    IconButton(onClick = { viewModel.logout() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Logout"
                        )
                    }
                }

                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                ) {
                    items(state.memories) { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = null,
                            modifier = Modifier
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }

    }

}

@Preview
@Composable
fun ProfileScreenErrorPreview() {
    MaterialTheme {
        ProfileScreen(
            viewModel = object : AbstractProfileViewModel() {
                @Composable
                override fun getState() = ProfileViewModel.State(uiState = ProfileUiState.Error)
                override fun load() {}
                override fun logout() {}
            }
        )
    }
}