package com.android.smcetransport.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.smcetransport.app.R
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont

@Composable
fun AppToolBar(
    modifier: Modifier = Modifier,
    toolBarBgColor : Color = MaterialTheme.colorScheme.background,
    toolBarText : String = "",
    toolBarStartIcon: Painter = painterResource(R.drawable.ic_arrow_back),
    toolBarTextAndIconTintColor : Color = colorResource(R.color.black),
    isShowLoading : Boolean = false,
    firstRightIcon : Painter? = null,
    onFirstRightIconClick : (() -> Unit)? = null,
    onToolBarStartIconClick : () -> Unit
) {
    val trackColor = if (isShowLoading)
        toolBarTextAndIconTintColor else colorResource(android.R.color.transparent)
    Column(
        modifier = modifier.fillMaxWidth()
            .background(toolBarBgColor)
    ) {
        Row(
            modifier = modifier.fillMaxWidth()
                .height(64.dp)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = toolBarStartIcon,
                contentDescription = null,
                modifier = Modifier.size(45.dp)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(50))
                    .clickable {
                        onToolBarStartIconClick()
                    }
                    .padding(5.dp),
                tint = toolBarTextAndIconTintColor
            )
            Text(
                text = toolBarText,
                color = toolBarTextAndIconTintColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontFamily = FontFamily(mediumFont),
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .weight(1f)
            )

            if (firstRightIcon != null) {
                Icon(
                    painter = firstRightIcon,
                    contentDescription = null,
                    modifier = Modifier.size(45.dp)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(50))
                        .clickable {
                            onFirstRightIconClick?.invoke()
                        }
                        .padding(5.dp),
                    tint = toolBarTextAndIconTintColor
                )
            }
        }
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth()
                .height(3.dp),
            trackColor = trackColor,
            color = toolBarBgColor,
            strokeCap = StrokeCap.Square,
            gapSize = 3.dp
        )
    }
}


@Preview
@Composable
fun PreviewAppToolBar() {
    AppToolBar(
        modifier = Modifier.fillMaxWidth(),
        toolBarText = "OTP Verification",
        isShowLoading = true,
        onToolBarStartIconClick = {

        }
    )
}