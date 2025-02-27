package com.invo.nflphotosharing.ui.feature.addMemory

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.invo.nflphotosharing.R
import com.invo.nflphotosharing.ui.designsystem.component.ImagePreviewCard
import com.invo.nflphotosharing.ui.designsystem.component.PrimaryButton
import com.invo.nflphotosharing.ui.designsystem.component.SecondaryButton
import com.invo.nflphotosharing.ui.designsystem.theme.NFLPhotoSharingTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.io.File

@Composable
fun AddMemoryScreen(
    modifier: Modifier = Modifier,
    viewModel: AbstractAddMemoryViewModel = hiltViewModel<AddMemoryViewModel>(),
    onPhotoSaved: () -> Unit = {}
) {
    val selectedImageUri by viewModel.selectedImageUri.collectAsState()
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    var showPermissionRationale by remember { mutableStateOf(false) }
    var tempCameraUri by remember { mutableStateOf<Uri?>(null) }

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.selectImage(it) }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            tempCameraUri?.let { viewModel.selectImage(it) }
        }
    }

    // Camera permission launcher
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        when (isGranted) {
            true -> tempCameraUri?.let { cameraLauncher.launch(it) }
            false -> showPermissionRationale = true
        }
    }

    fun createImageUri(): Uri {
        val imageFile = (viewModel as AddMemoryViewModel).createImageFile()
        return FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            imageFile
        )
    }

    fun launchCamera() {
        when {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                val uri = createImageUri()
                tempCameraUri = uri
                cameraLauncher.launch(uri)
            }

            else -> {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
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

        // Show permission rationale if needed
        if (showPermissionRationale) {
            Text(
                text = stringResource(R.string.camera_permission_rationale),
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        SelectSourceView(
            launchCamera = { launchCamera() },
            launchGallery = { galleryLauncher.launch("image/*") }
        )

        selectedImageUri?.let {
            Spacer(modifier = Modifier.height(10.dp))

            SecondaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.add_validate_cta),
                onClick = { viewModel.validateAndSaveImage() }
            )
        }

        // Show loading indicator when saving
        if (state.uiState is AddMemoryViewModel.AddMemoryUiState.Saving) {
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }
    }
}

@Composable
private fun SelectSourceView(launchCamera: () -> Unit, launchGallery: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        PrimaryButton(
            text = stringResource(R.string.add_take_photo_cta),
            onClick = { launchCamera() },
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(8.dp))

        PrimaryButton(
            text = stringResource(R.string.add_select_from_gallery_cta),
            onClick = { launchGallery() },
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddPhotoScreenPreview() {
    NFLPhotoSharingTheme {
        AddMemoryScreen(
            viewModel = object : AbstractAddMemoryViewModel() {
                override val selectedImageUri: StateFlow<Uri?> = MutableStateFlow(null)
                override val uiState: StateFlow<AddMemoryViewModel.State> =
                    MutableStateFlow(AddMemoryViewModel.State())

                override fun selectImage(uri: Uri) {}
                override fun validateAndSaveImage() {}
                override fun createImageFile(): File {
                    return File("")
                }
            }
        )
    }
}