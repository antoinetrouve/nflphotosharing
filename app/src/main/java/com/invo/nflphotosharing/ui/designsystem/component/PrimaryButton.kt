package com.invo.nflphotosharing.ui.designsystem.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.invo.nflphotosharing.ui.designsystem.theme.NFLBlue
import com.invo.nflphotosharing.ui.designsystem.theme.NFLWhite

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = NFLBlue,
            contentColor = NFLWhite
        ),
        enabled = enabled,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text)
    }
}

@Composable
fun SecondaryButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = NFLWhite,
            contentColor = NFLBlue
        ),
        border = ButtonDefaults.outlinedButtonBorder,
        enabled = enabled,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun PrimaryButtonPreview() {
    PrimaryButton(
        text = "Button",
        onClick = { }
    )
}

@Preview(showBackground = true)
@Composable
fun SecondaryButtonPreview() {
    SecondaryButton(
        text = "Button",
        onClick = { }
    )
}