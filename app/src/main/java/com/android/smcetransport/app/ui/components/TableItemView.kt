package com.android.smcetransport.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.smcetransport.app.R
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont

@Composable
fun TableItemView(
    modifier: Modifier = Modifier,
    firstText : String,
    secondText : String,
    thirdText : String
) {
    Row(
        modifier = modifier.fillMaxWidth()
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = firstText,
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(mediumFont),
            fontSize = 16.sp
        )
        VerticalDivider(modifier = Modifier
            .fillMaxHeight(),
            color = colorResource(R.color.black)
        )
        Text(
            text = secondText,
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(mediumFont),
            fontSize = 16.sp
        )
        VerticalDivider(modifier = Modifier
            .fillMaxHeight(),
            color = colorResource(R.color.black)
        )
        Text(
            text = thirdText,
            modifier = Modifier.fillMaxWidth()
                .weight(1f)
                .padding(16.dp),
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(mediumFont),
            fontSize = 16.sp
        )
    }

}


@Preview
@Composable
fun PreViewTableItemView() {
    TableItemView(
        modifier = Modifier.fillMaxWidth()
            .background(color = colorResource(R.color.white)),
        firstText = "Name",
        secondText = "Start Point",
        thirdText = "Amount"
    )
}