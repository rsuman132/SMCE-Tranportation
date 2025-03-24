package com.android.smcetransport.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.smcetransport.app.R
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont

@Composable
fun TextWithIconView(
    modifier: Modifier = Modifier,
    titleText : String,
    rightSideIcon : Painter? = null,
    rightSideIconTint : Color = colorResource(R.color.green_dark),
    onItemClick : (() -> Unit)? = null
) {

    val fivePercentageRoundedCorner = RoundedCornerShape(5)

    Row (
        modifier = modifier.fillMaxWidth()
            .clip(fivePercentageRoundedCorner)
            .border(
                width = 1.dp,
                color = colorResource(R.color.light_white),
                shape = fivePercentageRoundedCorner
            )
            .background(color = colorResource(R.color.white))
            .clickable(
                enabled = onItemClick != null,
                onClick = {
                    onItemClick?.invoke()
                }
            )
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = titleText,
            fontFamily = FontFamily(mediumFont),
            fontSize = 18.sp,
            color = colorResource(R.color.black),
            modifier = Modifier.fillMaxWidth().weight(1f)
        )
        if (rightSideIcon != null) {
            Icon(
                rightSideIcon,
                contentDescription = titleText,
                modifier = Modifier.size(24.dp),
                tint = rightSideIconTint
            )
        }
    }

}


@Preview
@Composable
fun PreviewTextWithIconView() {
    TextWithIconView(
        titleText = "Computer Science",
        modifier = Modifier.fillMaxWidth(),
        rightSideIcon = painterResource(R.drawable.ic_check_circle)
    )
}