package com.invo.nflphotosharing.ui.designsystem.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.invo.nflphotosharing.R
import com.invo.nflphotosharing.ui.feature.photo.model.MemoryView
import com.invo.nflphotosharing.ui.theme.NFLBlue
import com.invo.nflphotosharing.ui.theme.NFLPhotoSharingTheme
import com.invo.nflphotosharing.ui.theme.NFLWhite

@Composable
fun MemoryCard(modifier: Modifier = Modifier, memory: MemoryView) {
    Card(
        modifier = modifier,
    ) {
        Column(modifier = Modifier
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