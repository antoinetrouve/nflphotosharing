package com.invo.nflphotosharing.ui.designsystem.component

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.invo.nflphotosharing.R
import com.invo.nflphotosharing.ui.designsystem.theme.NFLBlue
import com.invo.nflphotosharing.ui.designsystem.theme.NFLPhotoSharingTheme
import com.invo.nflphotosharing.ui.designsystem.theme.NFLWhite
import com.invo.nflphotosharing.ui.feature.home.model.MemoryView

@Composable
fun MemoryCard(modifier: Modifier = Modifier, memory: MemoryView) {
    Card(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(NFLBlue)
        ) {
            Image(
                painter = painterResource(id = memory.id),
                contentDescription = memory.userName,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                text = memory.userName,
                style = MaterialTheme.typography.titleMedium,
                color = NFLWhite
            )
        }
    }
}

@Composable
fun ImagePreviewCard(
    modifier: Modifier = Modifier,
    imageUri: Uri? = null,
    title: String = "Preview"
) {
    val backgroundColor = if (imageUri != null) NFLBlue else Color.Black
    Card(
        modifier = modifier.size(400.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Image(
                        painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                        contentDescription = null,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            if (imageUri != null) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    textAlign = TextAlign.Center,
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    color = NFLWhite
                )
            }
        }
    }
}


@Preview
@Composable
fun MemoryCardPreview() {
    NFLPhotoSharingTheme {
        MemoryCard(
            memory = MemoryView(
                id = R.drawable.demo1,
                imageName = "demo1",
                userName = "John Doe"
            )
        )
    }
}

@Preview
@Composable
fun ImagePreviewCardPreview() {
    NFLPhotoSharingTheme {
        ImagePreviewCard(
            imageUri = null,
            title = "Preview"
        )
    }
}