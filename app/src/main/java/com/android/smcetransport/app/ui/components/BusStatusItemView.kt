package com.android.smcetransport.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.android.smcetransport.app.R
import com.android.smcetransport.app.core.enum.RequestStatusEnum
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont
import com.android.smcetransport.app.ui.theme.theme.theme.semiBoldFont

@Composable
fun BusStatusItemView(
    modifier: Modifier = Modifier,
    userName : String,
    statusText : String?,
    userId : String?,
    userImageUrl : String?,
    userDepartment : String,
    userYear : String?,
    amountText : String?,
    busNo : String?,
    busViaRoute : String?,
    pickUpPoint : String,
    requestStatus : RequestStatusEnum?,
    onCancelClick : () -> Unit,
    onApproveClick : () -> Unit
) {

    val fivePercentageRoundedShape = RoundedCornerShape(5)

    val userNameWithCollegeId = if (userId != null) {
         "$userName • $userId"
    } else {
        userName
    }

    val userDepartmentWithYear = if(userYear != null) {
        "$userDepartment • $userYear"
    } else {
        userDepartment
    }

    val acceptDeclareText = if (requestStatus == RequestStatusEnum.ACCEPTED) {
        stringResource(R.string.edit_text)
    } else {
        stringResource(R.string.approve_text)
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(fivePercentageRoundedShape)
            .border(
                width = 1.dp,
                color = colorResource(R.color.light_white),
                shape = fivePercentageRoundedShape
            )
            .background(color = colorResource(R.color.white))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .size(50.dp)
                .clip(RoundedCornerShape(50))
                .background(
                    color = colorResource(R.color.app_main_color)
                        .copy(alpha = 0.15f)
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userName.take(2).uppercase(),
                    fontFamily = FontFamily(semiBoldFont),
                    fontSize = 20.sp,
                    color = colorResource(R.color.app_main_color),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop,
                    model = userImageUrl,
                    contentDescription = null
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = userNameWithCollegeId,
                    fontFamily = FontFamily(mediumFont),
                    fontSize = 18.sp,
                    color = colorResource(R.color.black)
                )
                Text(
                    text = userDepartmentWithYear,
                    color = colorResource(R.color.gray_dark)
                )
            }
        }

        Text(
            text = "${stringResource(R.string.status_text)}: $statusText",
            fontSize = 16.sp,
            fontFamily = FontFamily(mediumFont),
            color = colorResource(R.color.black)
        )

        Text(
            text = "${stringResource(R.string.start_point_text)}: $pickUpPoint",
            fontSize = 16.sp,
            fontFamily = FontFamily(mediumFont),
            color = colorResource(R.color.black)
        )

        if (amountText != null) {
            Text(
                text = "${stringResource(R.string.amount_text)}: $amountText",
                fontSize = 16.sp,
                fontFamily = FontFamily(mediumFont),
                color = colorResource(R.color.black)
            )
        }

        if (busNo != null && busViaRoute != null) {
            Text(
                text = "${stringResource(R.string.bus_no_text)}: $busNo • $busViaRoute",
                fontSize = 16.sp,
                fontFamily = FontFamily(mediumFont),
                color = colorResource(R.color.black)
            )
        }

        if (requestStatus != RequestStatusEnum.CANCELLED) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(fivePercentageRoundedShape)
                        .background(color = colorResource(R.color.red_color))
                        .clickable {
                            onCancelClick()
                        }
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.cancel_text),
                        fontFamily = FontFamily(mediumFont),
                        fontSize = 16.sp,
                        color = colorResource(android.R.color.white)
                    )
                }


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .clip(fivePercentageRoundedShape)
                        .background(color = colorResource(R.color.green_dark))
                        .clickable {
                            onApproveClick()
                        }
                        .padding(10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = acceptDeclareText,
                        fontFamily = FontFamily(mediumFont),
                        fontSize = 16.sp,
                        color = colorResource(android.R.color.white)
                    )
                }

            }

        }

    }

}


@Preview
@Composable
fun PreviewBusStatusItemView() {
    BusStatusItemView(
        modifier = Modifier.fillMaxWidth(),
        userName = "Suman R",
        statusText = "Accepted",
        userId = "SMCE 123456",
        userImageUrl = null,
        userDepartment = "Mechanic",
        userYear = "First",
        amountText = "10.00",
        busNo = "1",
        busViaRoute = "Via Thuckalay",
        pickUpPoint = "Marthandam",
        requestStatus = RequestStatusEnum.REQUESTED,
        onApproveClick = {

        },
        onCancelClick = {

        }
    )
}