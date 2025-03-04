package com.android.smcetransport.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.android.smcetransport.app.R

@Composable
fun ImageUploadView(
    modifier: Modifier = Modifier,
    bgColor : Color = colorResource(R.color.app_main_color),
    placeholderPainter : Painter = painterResource(R.drawable.ic_fill_photo_camera),
    profileImage : Any? = null,
    onItemClick : (() -> Unit)? = null
) {

    Box(
        modifier = modifier.fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(50))
            .background(color = bgColor)
            .clickable(enabled = onItemClick != null) {
                onItemClick?.invoke()
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = placeholderPainter,
            modifier = Modifier.fillMaxSize(fraction = 0.35f),
            contentDescription = null,
            colorFilter = ColorFilter.tint(color = colorResource(android.R.color.white))
        )
        if (profileImage != null) {
            AsyncImage(
                model = profileImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Preview
@Composable
fun PreviewImageUploadView() {
    ImageUploadView(
        modifier = Modifier.width(100.dp),
        profileImage = "https://picsum.photos/200"
    )
}