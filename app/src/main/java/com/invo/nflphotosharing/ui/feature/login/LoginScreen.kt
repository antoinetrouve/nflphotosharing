package com.invo.nflphotosharing.ui.feature.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.invo.nflphotosharing.R
import com.invo.nflphotosharing.ui.designsystem.component.InputField
import com.invo.nflphotosharing.ui.designsystem.component.PrimaryButton
import com.invo.nflphotosharing.ui.designsystem.theme.NFLPhotoSharingTheme
import com.invo.nflphotosharing.ui.feature.login.LoginViewModel.LoginUiState

@Composable
fun LoginScreen(
    viewModel: AbstractLoginViewModel = hiltViewModel<LoginViewModel>(),
    onLoginSuccess: () -> Unit
) {
    val state = viewModel.getState()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(state.sideEffect) {
        state.sideEffect?.let {
            if (it == LoginViewModel.SideEffect.NavigateToHome) onLoginSuccess()
            viewModel.consumeSideEffect()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "NFL Logo",
            contentScale = ContentScale.Crop ,
            modifier = Modifier.size(150.dp).clip(CircleShape)
        )
        Spacer(modifier = Modifier.height(24.dp))

        InputField(
            value = username,
            onValueChange = { username = it },
            label = stringResource(R.string.login_username),
            isError = state.uiState is LoginUiState.Error && state.uiState is LoginUiState.Error.InvalidUsername,
            errorMessage = if (state.uiState is LoginUiState.Error && state.uiState is LoginUiState.Error.InvalidUsername) stringResource(
                R.string.login_error_invalid_username
            ) else null,
            supportingText = "Identifiant : demo",
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
        )

        Spacer(modifier = Modifier.height(12.dp))

        InputField(
            value = password,
            onValueChange = { password = it },
            label = stringResource(R.string.login_password),
            visualTransformation = PasswordVisualTransformation(),
            isError = state.uiState is LoginUiState.Error && state.uiState is LoginUiState.Error.InvalidPassword,
            errorMessage = if (state.uiState is LoginUiState.Error && state.uiState is LoginUiState.Error.InvalidPassword) stringResource(
                R.string.login_error_invalid_password
            ) else null,
            supportingText = "Mot de passe : demo",
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.login_cta),
            onClick = { viewModel.login(username, password) },
            enabled = state.uiState !is LoginUiState.Loading
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    NFLPhotoSharingTheme {
        LoginScreen(onLoginSuccess = {}, viewModel = object : AbstractLoginViewModel() {
            @Composable
            override fun getState(): LoginViewModel.State {
                return LoginViewModel.State()
            }
            override fun login(username: String, password: String) {}
            override fun consumeSideEffect() {}
        })
    }
}
