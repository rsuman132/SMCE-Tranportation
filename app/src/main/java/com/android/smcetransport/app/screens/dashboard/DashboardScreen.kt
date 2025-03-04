package com.android.smcetransport.app.screens.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.android.smcetransport.app.R
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.ui.components.DashBoardCardItem
import com.android.smcetransport.app.ui.theme.theme.theme.normalFont
import com.android.smcetransport.app.ui.theme.theme.theme.semiBoldFont

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    loginUserTypeEnum: LoginUserTypeEnum?
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(color = colorResource(R.color.white))
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(50))
                .background(
                    color = colorResource(R.color.app_main_color)
                        .copy(alpha = 0.15f)
                ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "SR",
                    fontSize = 30.sp,
                    fontFamily = FontFamily(semiBoldFont),
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center,
                    color = colorResource(R.color.app_main_color)
                )
                AsyncImage(
                    model = "https://www.shutterstock.com/image-photo/profile-picture-smiling-successful-young-260nw-2040223583.jpg",
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(text = "Suman R",
                    fontSize = 20.sp,
                    color = colorResource(R.color.black),
                    fontFamily = FontFamily(semiBoldFont)
                )
                Text(
                    text = "SMCE123456",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(normalFont),
                    color = colorResource(R.color.black),
                )
            }

            Box(modifier = Modifier
                .size(40.dp)
                .border(
                    width = 1.dp,
                    color = colorResource(R.color.light_white),
                    shape = RoundedCornerShape(10)
                )
                .background(color = colorResource(R.color.white)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_logout),
                    tint = colorResource(R.color.red_color),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                )
            }

        }

        Text(
            text = stringResource(R.string.virtual_pass_management_text),
            modifier = Modifier.padding(all = 16.dp),
            fontSize = 30.sp,
            fontFamily = FontFamily(semiBoldFont),
            lineHeight = 36.sp
        )

        if (loginUserTypeEnum != null) {
            when (loginUserTypeEnum) {
                LoginUserTypeEnum.STUDENT -> {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)) {

                        DashBoardCardItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = 16.dp, end = 8.dp),
                            cardItemText = stringResource(R.string.view_pass),
                            cardItemBgColor = colorResource(android.R.color.holo_orange_dark),
                            cardItemIcon = painterResource(R.drawable.ic_hand_eye),
                            onItemClick = {

                            }
                        )

                        DashBoardCardItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = 8.dp, end = 16.dp),
                            cardItemText = stringResource(R.string.apply_new),
                            cardItemBgColor = colorResource(android.R.color.holo_purple),
                            cardItemIcon = painterResource(R.drawable.ic_schedule_send),
                            onItemClick = {

                            }
                        )
                    }

                    DashBoardCardItem(
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        cardItemText = stringResource(R.string.cancel_pass),
                        cardItemBgColor = colorResource(android.R.color.holo_blue_bright),
                        cardItemIcon = painterResource(R.drawable.ic_contract_delete),
                        onItemClick = {

                        }
                    )

                }
                LoginUserTypeEnum.STAFF -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {

                        DashBoardCardItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = 16.dp, end = 8.dp),
                            cardItemText = stringResource(R.string.view_pass),
                            cardItemBgColor = colorResource(android.R.color.holo_orange_dark),
                            cardItemIcon = painterResource(R.drawable.ic_hand_eye),
                            onItemClick = {

                            }
                        )

                        DashBoardCardItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = 8.dp, end = 16.dp),
                            cardItemText = stringResource(R.string.apply_new),
                            cardItemBgColor = colorResource(android.R.color.holo_purple),
                            cardItemIcon = painterResource(R.drawable.ic_schedule_send),
                            onItemClick = {

                            }
                        )
                    }
                }
                LoginUserTypeEnum.INCHARGE -> {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)) {

                        DashBoardCardItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = 16.dp, end = 8.dp),
                            cardItemText = stringResource(R.string.application_request),
                            cardItemBgColor = colorResource(android.R.color.holo_orange_dark),
                            cardItemIcon = painterResource(R.drawable.ic_tab_new),
                            onItemClick = {

                            }
                        )

                        DashBoardCardItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = 8.dp, end = 16.dp),
                            cardItemText = stringResource(R.string.cancellation_request),
                            cardItemBgColor = colorResource(android.R.color.holo_purple),
                            cardItemIcon = painterResource(R.drawable.ic_tab_close),
                            onItemClick = {

                            }
                        )
                    }

                    DashBoardCardItem(
                        modifier = Modifier
                            .padding(top = 8.dp, bottom = 16.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        cardItemText = stringResource(R.string.over_all_data),
                        cardItemBgColor = colorResource(android.R.color.holo_blue_bright),
                        cardItemIcon = painterResource(R.drawable.ic_data_check),
                        onItemClick = {

                        }
                    )

                    Text(
                        text = stringResource(R.string.category_management),
                        modifier = Modifier.padding(all = 16.dp),
                        fontSize = 30.sp,
                        fontFamily = FontFamily(semiBoldFont),
                        lineHeight = 36.sp
                    )


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {

                        DashBoardCardItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = 16.dp, end = 8.dp),
                            cardItemText = stringResource(R.string.department_management),
                            cardItemBgColor = colorResource(android.R.color.holo_orange_dark),
                            cardItemIcon = painterResource(R.drawable.ic_list_alt),
                            onItemClick = {

                            }
                        )

                        DashBoardCardItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = 8.dp, end = 16.dp),
                            cardItemText = stringResource(R.string.year_management),
                            cardItemBgColor = colorResource(android.R.color.holo_blue_bright),
                            cardItemIcon = painterResource(R.drawable.ic_format_list_numbered),
                            onItemClick = {

                            }
                        )
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {

                        DashBoardCardItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = 16.dp, end = 8.dp),
                            cardItemText = stringResource(R.string.bus_routes),
                            cardItemBgColor = colorResource(android.R.color.holo_blue_bright),
                            cardItemIcon = painterResource(R.drawable.ic_route),
                            onItemClick = {

                            }
                        )

                        DashBoardCardItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = 8.dp, end = 16.dp),
                            cardItemText = stringResource(R.string.bus_management),
                            cardItemBgColor = colorResource(android.R.color.holo_orange_dark),
                            cardItemIcon = painterResource(R.drawable.ic_directions_bus),
                            onItemClick = {

                            }
                        )
                    }

                }
            }
        }

    }

}



@Preview
@Composable
fun PreviewDashboardScreen() {
    DashboardScreen(
        modifier = Modifier.fillMaxSize(),
        loginUserTypeEnum = LoginUserTypeEnum.INCHARGE
    )
}