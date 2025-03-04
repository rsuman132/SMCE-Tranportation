package com.android.smcetransport.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.android.smcetransport.app.R
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont
import com.android.smcetransport.app.ui.theme.theme.theme.normalFont
import com.android.smcetransport.app.ui.theme.theme.theme.semiBoldFont

@Composable
fun InformationDialog(
    modifier: Modifier = Modifier,
    isDismissible : Boolean,
    dialogIcon : Painter,
    dialogTitle : String,
    dialogDesc : String,
    buttonAndIconColor : Color = colorResource(R.color.app_main_color),
    positiveBtnText : String,
    negativeBtnText : String,
    positiveBtnIcon : Painter?,
    negativeBtnIcon : Painter?,
    onDialogDismissEvent : () -> Unit,
    onPositiveBtnEvent : () -> Unit,
    onNegativeBtnEvent : () -> Unit
) {

    val configuration = LocalConfiguration.current
    val infoIconWidthInDp = configuration.screenWidthDp.dp / 6
    val roundedShape = RoundedCornerShape(50)
    val fivePercentageShape = RoundedCornerShape(5)

    Dialog(
        onDismissRequest = {
            onDialogDismissEvent()
        },
        properties = DialogProperties(
            dismissOnClickOutside = isDismissible,
            dismissOnBackPress = isDismissible,
            usePlatformDefaultWidth = false
        )
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(fivePercentageShape)
                .background(color = colorResource(R.color.white))
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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
                Spacer(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f))
                Box(
                    modifier = Modifier
                        .size(30.dp)
                        .border(
                            width = 1.dp,
                            color = colorResource(R.color.light_white),
                            shape = fivePercentageShape
                        )
                        .clip(fivePercentageShape)
                        .background(color = colorResource(R.color.white))
                        .clickable(enabled = isDismissible) {
                            onDialogDismissEvent()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_close),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp)
                    )
                }
            }
            Text(
                text = dialogTitle,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                fontSize = 20.sp,
                fontFamily = FontFamily(semiBoldFont),
                color = colorResource(R.color.black)
            )
            Text(
                text = dialogDesc,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 8.dp
                    ),
                fontSize = 16.sp,
                fontFamily = FontFamily(normalFont),
                color = colorResource(R.color.gray_dark)
            )
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(top = 16.dp)
                    .background(color = colorResource(R.color.light_white).copy(alpha = 0.25f))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                        .padding(end = 8.dp)
                        .border(width = 1.dp, color = buttonAndIconColor, shape = fivePercentageShape)
                        .clip(fivePercentageShape)
                        .background(color = colorResource(R.color.white))
                        .clickable {
                            onNegativeBtnEvent()
                        }
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (negativeBtnIcon != null) {
                        Icon(
                            painter = negativeBtnIcon,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp).size(24.dp),
                            tint = buttonAndIconColor
                        )
                    }

                    Text(
                        text = negativeBtnText,
                        color = buttonAndIconColor,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(mediumFont),
                        maxLines = 1
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth().weight(1f)
                        .padding(start = 8.dp)
                        .border(width = 1.dp, color = buttonAndIconColor, shape = fivePercentageShape)
                        .clip(fivePercentageShape)
                        .background(color = buttonAndIconColor)
                        .clickable {
                            onPositiveBtnEvent()
                        }
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (positiveBtnIcon != null) {
                        Icon(
                            painter = positiveBtnIcon,
                            contentDescription = null,
                            modifier = Modifier.padding(end = 8.dp).size(24.dp),
                            tint = colorResource(android.R.color.white)
                        )
                    }

                    Text(
                        text = positiveBtnText,
                        color = colorResource(android.R.color.white),
                        fontSize = 16.sp,
                        fontFamily = FontFamily(mediumFont),
                        maxLines = 1
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewInformationDialog() {
    InformationDialog(
        modifier = Modifier.fillMaxWidth(),
        isDismissible = true,
        dialogIcon = painterResource(R.drawable.ic_upload),
        dialogTitle = "Upload Image",
        dialogDesc = "Please select a image from the gallery or capture image from the camera for uploading profile image",
        buttonAndIconColor = colorResource(R.color.app_main_color),
        positiveBtnText = "Camera",
        negativeBtnText = "Gallery",
        positiveBtnIcon = painterResource(R.drawable.ic_fill_photo_camera),
        negativeBtnIcon = painterResource(R.drawable.ic_fill_photo_library),
        onDialogDismissEvent = {

        },
        onPositiveBtnEvent = {

        },
        onNegativeBtnEvent = {

        }
    )
}