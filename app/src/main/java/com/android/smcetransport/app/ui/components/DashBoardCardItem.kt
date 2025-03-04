package com.android.smcetransport.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
fun DashBoardCardItem(
    modifier: Modifier = Modifier,
    cardItemText : String,
    cardItemIcon : Painter,
    cardItemBgColor : Color,
    onItemClick : () -> Unit
) {

    val tenPercentageRoundedCorner = RoundedCornerShape(10)

    Column(
        modifier = modifier.fillMaxWidth()
            .clip(tenPercentageRoundedCorner)
            .background(cardItemBgColor)
            .clickable {
                onItemClick()
            }
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            painter = cardItemIcon,
            contentDescription = null,
            tint = colorResource(android.R.color.white),
            modifier = Modifier.size(40.dp)
                .border(
                    width = 1.dp,
                    color = colorResource(android.R.color.white),
                    shape = tenPercentageRoundedCorner
                )
                .clip(tenPercentageRoundedCorner)
                .padding(5.dp)
        )
        Text(
            text = cardItemText,
            color = colorResource(android.R.color.white),
            fontFamily = FontFamily(mediumFont),
            fontSize = 24.sp
        )
    }

}

@Preview
@Composable
fun PreviewDashBoardCardItem() {
    DashBoardCardItem(
        modifier = Modifier.fillMaxWidth(),
        cardItemIcon = painterResource(R.drawable.ic_schedule_send),
        cardItemText = "Apply new pass",
        cardItemBgColor = colorResource(R.color.app_main_color),
        onItemClick = {

        }
    )
}