package com.android.smcetransport.app.screens.request_success

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.smcetransport.app.R
import com.android.smcetransport.app.ui.components.AppToolBar
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont

@Composable
fun RequestSuccessScreen(
    modifier: Modifier = Modifier,
    messageText : String,
    onBackPress : () -> Unit
) {

    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val configuration = LocalConfiguration.current
    val roundedCornerShape = RoundedCornerShape(50)

    Column(modifier = modifier
        .fillMaxSize()
        .background(color = colorResource(R.color.white))
    ) {
        AppToolBar(
            modifier = Modifier.fillMaxWidth(),
            toolBarBgColor = colorResource(R.color.white),
            onToolBarStartIconClick = {
                onBackPress()
            }
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(
                32.dp,
                alignment = Alignment.CenterVertically
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            Box(
                modifier = Modifier
                    .width(configuration.screenWidthDp.dp / 2)
                    .aspectRatio(1f)
                    .clip(roundedCornerShape)
                    .background(color = colorResource(R.color.green_dark).copy(alpha = 0.15f))
                    .scale(scale),
                contentAlignment = Alignment.Center
            ) {


                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .clip(roundedCornerShape)
                        .background(color = colorResource(R.color.green_dark).copy(alpha = 0.15f))
                        .scale(scale),
                    contentAlignment = Alignment.Center
                ) {

                    Icon(
                        painter = painterResource(R.drawable.ic_check_circle),
                        contentDescription = null,
                        tint = colorResource(R.color.green_dark),
                        modifier = Modifier.fillMaxSize()
                            .padding(32.dp)
                    )

                }

            }


            Text(
                text = messageText,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                lineHeight = 36.sp,
                fontFamily = FontFamily(mediumFont)
            )

        }
    }
}

@Preview
@Composable
fun PreviewRequestSuccessScreen() {
    RequestSuccessScreen(
        modifier = Modifier.fillMaxSize(),
        messageText = stringResource(R.string.your_request_send_successfully),
        onBackPress = {

        }
    )
}