package com.android.smcetransport.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.smcetransport.app.R
import com.android.smcetransport.app.ui.theme.theme.theme.normalFont
import com.android.smcetransport.app.ui.theme.theme.theme.semiBoldFont

@Composable
fun TitleDescCardItem(
    modifier: Modifier = Modifier,
    titleText : String,
    descText : String,
    onItemClick : (() -> Unit)? = null
) {

    val fivePercentageShape = RoundedCornerShape(5)

    Column(
        modifier = modifier.fillMaxWidth()
            .border(
                width = 1.dp,
                color = colorResource(R.color.light_white).copy(0.5f),
                fivePercentageShape
            )
            .clip(fivePercentageShape)
            .background(color = colorResource(R.color.white))
            .clickable(enabled = onItemClick != null) {
                onItemClick?.invoke()
            }.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {

        Text(
            text = titleText,
            fontFamily = FontFamily(semiBoldFont),
            fontSize = 18.sp,
            color = colorResource(R.color.black)
        )

        Text(
            text = descText,
            fontFamily = FontFamily(normalFont),
            fontSize = 14.sp,
            color = colorResource(R.color.black)
        )

    }

}


@Preview
@Composable
fun PreviewTitleDescCardItem() {
    TitleDescCardItem(
        modifier = Modifier.fillMaxWidth(),
        titleText = "Mechanic",
        descText = "MECH"
    )
}