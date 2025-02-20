package com.invo.nflphotosharing.ui.designsystem.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.invo.nflphotosharing.ui.designsystem.theme.NFLRed
import com.invo.nflphotosharing.ui.designsystem.theme.NFLWhite

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    label: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    isError: Boolean = false,
    errorMessage: String? = null,
    supportingText: String? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = NFLWhite,
                focusedTextColor = NFLWhite,
                focusedLabelColor = NFLWhite,
                focusedBorderColor = NFLWhite,
                unfocusedBorderColor = NFLWhite,
                unfocusedLabelColor = NFLWhite,
                errorTextColor = NFLRed,
                errorBorderColor = NFLRed,
                errorLabelColor = NFLRed,
            ),
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            supportingText = supportingText?.let {
                {
                    Text(
                        text = supportingText,
                        fontStyle = FontStyle.Italic,
                        color = NFLWhite,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            },
            singleLine = singleLine,
            visualTransformation = visualTransformation,
            isError = isError,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )
        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = NFLWhite,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 16.dp, top = 4.dp)
            )
        }
    }
}

@Preview
@Composable
fun InputFieldPreview() {
    InputField(
        value = "Input",
        supportingText = "Supporting Text",
        onValueChange = { },
        label = "Label"
    )
}