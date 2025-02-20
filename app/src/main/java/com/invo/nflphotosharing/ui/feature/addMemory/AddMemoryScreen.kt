package com.invo.nflphotosharing.ui.feature.addMemory

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.invo.nflphotosharing.R
import com.invo.nflphotosharing.ui.designsystem.component.ImagePreviewCard
import com.invo.nflphotosharing.ui.designsystem.component.PrimaryButton
import com.invo.nflphotosharing.ui.designsystem.component.SecondaryButton
import com.invo.nflphotosharing.ui.designsystem.theme.NFLPhotoSharingTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun AddMemoryScreen(
    modifier: Modifier = Modifier,
    viewModel: AbstractAddMemoryViewModel = hiltViewModel<AddMemoryViewModel>(),
    onPhotoSaved: () -> Unit = {}
) {
    val selectedImageUri by viewModel.selectedImageUri.collectAsState()
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.selectImage(it) }
    }

    LaunchedEffect(state.sideEffect) {
        if (state.sideEffect != null) onPhotoSaved()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ImagePreviewCard(
            imageUri = selectedImageUri,
            title = stringResource(R.string.add_preview_card_title)
        )

        Spacer(modifier = Modifier.height(32.dp))

        PrimaryButton(
            text = stringResource(R.string.add_select_cta),
            onClick = { galleryLauncher.launch("image/*") }
        )

        selectedImageUri?.let {
            Spacer(modifier = Modifier.height(10.dp))

            SecondaryButton(
                text = stringResource(R.string.add_validate_cta),
                onClick = {
                    viewModel.validateAndSaveImage()
                }
            )
        }

        // Show loading indicator when saving
        if (state.uiState is AddMemoryViewModel.AddMemoryUiState.Saving) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }

}

@Preview(showBackground = true)
@Composable
fun AddPhotoScreenPreview() {
    NFLPhotoSharingTheme {
        AddMemoryScreen(
            viewModel = object : AbstractAddMemoryViewModel() {
                override val selectedImageUri: StateFlow<Uri?> = MutableStateFlow(null)
                override val uiState: StateFlow<AddMemoryViewModel.State> = MutableStateFlow(AddMemoryViewModel.State())
                override fun selectImage(uri: Uri) { }
                override fun validateAndSaveImage() { }
            }
        )
    }
}