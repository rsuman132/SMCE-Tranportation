package com.android.smcetransport.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    buttonStokeBorderColor : Color = MaterialTheme.colorScheme.primary,
    buttonBgColor : Color = MaterialTheme.colorScheme.primary,
    buttonText : String,
    buttonTextAndSpinnerColor : Color = colorResource(android.R.color.white),
    isButtonLoading : Boolean = false,
    buttonClickEvent : () -> Unit
) {
    val rounderShape = RoundedCornerShape(10)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .border(
                width = 2.dp,
                color = buttonStokeBorderColor,
                shape = rounderShape
            )
            .clip(rounderShape)
            .background(buttonBgColor)
            .clickable(enabled = !isButtonLoading) {
                buttonClickEvent()
            },
        contentAlignment = Alignment.Center
    ) {
        if (isButtonLoading) {
            InstaSpinner(
                modifier = Modifier.fillMaxHeight()
                    .aspectRatio(1f)
                    .padding(16.dp),
                color = buttonTextAndSpinnerColor,
                isRefreshing = true
            )
        } else {
            Text(
                text = buttonText,
                color = buttonTextAndSpinnerColor,
                maxLines = 1,
                fontFamily = FontFamily(mediumFont),
                fontSize = 18.sp
            )
        }
    }
}


@Preview
@Composable
fun PreviewAppButton() {
    AppButton(
        modifier = Modifier.fillMaxWidth(),
        buttonText = "Continue",
        isButtonLoading = false,
        buttonClickEvent = {

        }
    )
}