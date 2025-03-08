package com.android.smcetransport.app.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.android.smcetransport.app.R
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont

@Composable
fun TitleWithButtonInfoDialog (
    modifier: Modifier = Modifier,
    dialogIcon : Painter = painterResource(R.drawable.ic_info),
    dialogText : String,
    dialogButtonText : String,
    buttonAndIconColor : Color = colorResource(R.color.app_main_color),
    onDismissEvent : () -> Unit
) {

    val configuration = LocalConfiguration.current
    val infoIconWidthInDp = configuration.screenWidthDp.dp / 6
    val roundedShape = RoundedCornerShape(50)
    val fivePercentageShape = RoundedCornerShape(5)

    Dialog(
        onDismissRequest = {
            onDismissEvent()
        },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        )
    ) {

        Column(
            modifier = modifier.fillMaxWidth()
                .clip(fivePercentageShape)
                .background(color = colorResource(R.color.white)).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Box(
                modifier = Modifier
                    .width(infoIconWidthInDp)
                    .aspectRatio(1f)
                    .clip(roundedShape)
                    .background(color = buttonAndIconColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .width(infoIconWidthInDp)
                        .aspectRatio(1f)
                        .padding(5.dp)
                        .clip(roundedShape)
                        .background(color = buttonAndIconColor.copy(alpha = 0.15f)),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = dialogIcon,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        colorFilter = ColorFilter.tint(color = buttonAndIconColor)
                    )
                }
            }

            Text(
                text = dialogText,
                fontFamily = FontFamily(mediumFont),
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            AppButton(
                buttonText = dialogButtonText,
                buttonClickEvent = {
                    onDismissEvent()
                },
                buttonBgColor = buttonAndIconColor,
                modifier = Modifier.fillMaxWidth()
            )

        }
    }

}

@Preview
@Composable
fun PreviewTitleWithButtonInfoDialog() {
    TitleWithButtonInfoDialog(
        modifier = Modifier.fillMaxWidth(),
        dialogIcon = painterResource(R.drawable.ic_info),
        dialogText = "No pass available, so cancellation is not possible at the moment",
        dialogButtonText = "Okay",
        buttonAndIconColor = colorResource(R.color.app_main_color),
        onDismissEvent = {

        }
    )
}