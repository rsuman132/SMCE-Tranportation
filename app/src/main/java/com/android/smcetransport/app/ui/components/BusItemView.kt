package com.android.smcetransport.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.smcetransport.app.R
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont
import com.android.smcetransport.app.ui.theme.theme.theme.normalFont
import com.android.smcetransport.app.ui.theme.theme.theme.semiBoldFont

@Composable
fun BusItemView(
    modifier: Modifier = Modifier,
    busNumber : String,
    busDriverName : String,
    busRegisterNumber : String,
    routeStartPoint : String,
    viaRoute : String
) {

    val roundedShape = RoundedCornerShape(5)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = colorResource(R.color.light_white).copy(0.5f),
                shape = roundedShape
            )
            .clip(roundedShape)
            .background(color = colorResource(R.color.white))
            .padding(16.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {

                Text(
                    text = busDriverName,
                    color = colorResource(R.color.black),
                    fontSize = 18.sp,
                    fontFamily = FontFamily(mediumFont)
                )
                Text(
                    text = busRegisterNumber,
                    color = colorResource(R.color.gray_dark),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(normalFont)
                )

            }

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .size(50.dp)
                    .background(color = colorResource(R.color.app_main_color).copy(0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = busNumber,
                    color = colorResource(R.color.app_main_color),
                    fontFamily = FontFamily(semiBoldFont),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }

        Row (
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Spacer(
                modifier = Modifier
                    .size(16.dp)
                    .border(width = 4.dp, shape = RoundedCornerShape(50), color = colorResource(R.color.app_main_color))
                    .clip(RoundedCornerShape(50))
            )


            Text(
                text = routeStartPoint,
                color = colorResource(R.color.black),
                fontFamily = FontFamily(mediumFont),
                fontSize = 18.sp
            )

        }

        Row (
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {

            Spacer(
                modifier = Modifier
                    .size(16.dp)
                    .border(width = 4.dp, shape = RoundedCornerShape(50), color = colorResource(R.color.app_main_color))
                    .clip(RoundedCornerShape(50))
            )


            Text(
                text = viaRoute,
                color = colorResource(R.color.black),
                fontFamily = FontFamily(mediumFont),
                fontSize = 18.sp
            )

        }

    }

}


@Preview
@Composable
fun PreviewBusItemView() {
    BusItemView(
        modifier = Modifier.fillMaxWidth(),
        busNumber = "1",
        busDriverName = "Suman R",
        busRegisterNumber = "TN74 10T 2020",
        routeStartPoint = "Nagercoil",
        viaRoute = "Marthandam"
    )
}