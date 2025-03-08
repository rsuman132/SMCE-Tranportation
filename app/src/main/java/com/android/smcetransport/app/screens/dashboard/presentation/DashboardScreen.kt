package com.android.smcetransport.app.screens.dashboard.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.android.smcetransport.app.R
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.core.enum.RequestStatusEnum
import com.android.smcetransport.app.ui.components.AppButton
import com.android.smcetransport.app.ui.components.CommonTextField
import com.android.smcetransport.app.ui.components.DashBoardCardItem
import com.android.smcetransport.app.ui.components.InformationDialog
import com.android.smcetransport.app.ui.components.TitleWithButtonInfoDialog
import com.android.smcetransport.app.ui.theme.theme.theme.normalFont
import com.android.smcetransport.app.ui.theme.theme.theme.semiBoldFont

@Composable
fun DashboardScreen(
    modifier: Modifier = Modifier,
    loginUserTypeEnum: LoginUserTypeEnum?,
    dashboardUIState: DashboardUIState,
    onDashboardActionEvents: (DashboardActionEvents) -> Unit
) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
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
                        text = dashboardUIState.userName.take(2).uppercase(),
                        fontSize = 30.sp,
                        fontFamily = FontFamily(semiBoldFont),
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center,
                        color = colorResource(R.color.app_main_color)
                    )
                    AsyncImage(
                        model = dashboardUIState.userAvatar,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(text = dashboardUIState.userName,
                        fontSize = 20.sp,
                        color = colorResource(R.color.black),
                        fontFamily = FontFamily(semiBoldFont)
                    )
                    Text(
                        text = dashboardUIState.userCollegeId,
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
                    .background(color = colorResource(R.color.white))
                    .clickable {
                        onDashboardActionEvents(DashboardActionEvents.OnLogoutDialogShowEvent(true))
                    },
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
                                    onDashboardActionEvents(DashboardActionEvents.OnViewPassClickEvent)
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
                                    onDashboardActionEvents(DashboardActionEvents.OnSendRequestCardClick)
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
                                onDashboardActionEvents(DashboardActionEvents.OnCancelRequestCardClick)
                            }
                        )

                    }
                    LoginUserTypeEnum.STAFF -> {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(IntrinsicSize.Max)
                                .padding(vertical = 16.dp)
                        ) {

                            DashBoardCardItem(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .padding(start = 16.dp, end = 8.dp),
                                cardItemText = stringResource(R.string.view_pass),
                                cardItemBgColor = colorResource(android.R.color.holo_orange_dark),
                                cardItemIcon = painterResource(R.drawable.ic_hand_eye),
                                onItemClick = {
                                    onDashboardActionEvents(DashboardActionEvents.OnViewPassClickEvent)
                                }
                            )

                            DashBoardCardItem(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .padding(start = 8.dp, end = 16.dp),
                                cardItemText = stringResource(R.string.apply_new),
                                cardItemBgColor = colorResource(android.R.color.holo_purple),
                                cardItemIcon = painterResource(R.drawable.ic_schedule_send),
                                onItemClick = {
                                    onDashboardActionEvents(DashboardActionEvents.OnSendRequestCardClick)
                                }
                            )
                        }
                    }
                    LoginUserTypeEnum.BUSINCHARGE -> {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .padding(vertical = 16.dp)) {

                            DashBoardCardItem(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .padding(start = 16.dp, end = 8.dp),
                                cardItemText = stringResource(R.string.application_request),
                                cardItemBgColor = colorResource(android.R.color.holo_orange_dark),
                                cardItemIcon = painterResource(R.drawable.ic_add_box),
                                onItemClick = {
                                    onDashboardActionEvents(DashboardActionEvents.OnBusStatusRequestClickEvent(
                                        requestStatusEnum = RequestStatusEnum.REQUESTED
                                    ))
                                }
                            )

                            DashBoardCardItem(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .padding(start = 8.dp, end = 16.dp),
                                cardItemText = stringResource(R.string.acceptance_request),
                                cardItemBgColor = colorResource(android.R.color.holo_green_light),
                                cardItemIcon = painterResource(R.drawable.ic_tab_new),
                                onItemClick = {
                                    onDashboardActionEvents(DashboardActionEvents.OnBusStatusRequestClickEvent(
                                        requestStatusEnum = RequestStatusEnum.ACCEPTED
                                    ))
                                }
                            )
                        }

                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min)
                            .padding(vertical = 16.dp)) {

                            DashBoardCardItem(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .padding(start = 16.dp, end = 8.dp),
                                cardItemText = stringResource(R.string.cancellation_request),
                                cardItemBgColor = colorResource(android.R.color.holo_purple),
                                cardItemIcon = painterResource(R.drawable.ic_tab_close),
                                onItemClick = {
                                    onDashboardActionEvents(DashboardActionEvents.OnBusStatusRequestClickEvent(
                                        requestStatusEnum = RequestStatusEnum.ACCEPTED
                                    ))
                                }
                            )


                            DashBoardCardItem(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f)
                                    .padding(start = 8.dp, end = 16.dp),
                                cardItemText = stringResource(R.string.over_all_data),
                                cardItemBgColor = colorResource(android.R.color.holo_blue_bright),
                                cardItemIcon = painterResource(R.drawable.ic_data_check),
                                onItemClick = {

                                }
                            )

                        }

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
                                    onDashboardActionEvents(DashboardActionEvents.OnManageDepartmentClickEvent)
                                }
                            )

                            DashBoardCardItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .padding(start = 8.dp, end = 16.dp),
                                cardItemText = stringResource(R.string.bus_management),
                                cardItemBgColor = colorResource(android.R.color.holo_blue_bright),
                                cardItemIcon = painterResource(R.drawable.ic_directions_bus),
                                onItemClick = {
                                    onDashboardActionEvents(DashboardActionEvents.OnManageBusClickEvent)
                                }
                            )
                        }

                    }
                }
            }

        }

        if (dashboardUIState.isShowLogoutDialog) {
            InformationDialog(
                isDismissible = true,
                negativeBtnIcon = null,
                positiveBtnIcon = null,
                positiveBtnText = stringResource(R.string.yes_logout),
                negativeBtnText = stringResource(R.string.cancel_text),
                onPositiveBtnEvent = {
                    onDashboardActionEvents(DashboardActionEvents.OnLogoutTriggerEvent)
                },
                onNegativeBtnEvent = {
                    onDashboardActionEvents(DashboardActionEvents.OnLogoutDialogShowEvent(false))
                },
                onDialogDismissEvent = {
                    onDashboardActionEvents(DashboardActionEvents.OnLogoutDialogShowEvent(false))
                },
                dialogIcon = painterResource(R.drawable.ic_logout),
                dialogDesc = stringResource(R.string.logout_desc),
                dialogTitle = stringResource(R.string.logout_text),
                buttonAndIconColor = colorResource(R.color.red_color)
            )
        }

        if (dashboardUIState.isShowRequestPassDialog) {
            Dialog(
                onDismissRequest = {
                    onDashboardActionEvents(DashboardActionEvents.OnRequestDialogDismissEvent)
                },
                properties = DialogProperties(
                    dismissOnClickOutside = !dashboardUIState.isRequestingButtonLoading,
                    dismissOnBackPress = !dashboardUIState.isRequestingButtonLoading,
                    usePlatformDefaultWidth = false
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(shape = RoundedCornerShape(5))
                        .background(color = colorResource(R.color.white))
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {


                    Text(
                        text = stringResource(R.string.requesting_new_pass),
                        fontFamily = FontFamily(semiBoldFont),
                        fontSize = 24.sp
                    )
                    Text(
                        text = stringResource(R.string.requesting_new_pass_desc),
                        fontFamily = FontFamily(normalFont),
                        fontSize = 18.sp
                    )

                    CommonTextField(
                        modifier = Modifier.fillMaxWidth(),
                        etValue = dashboardUIState.startingPointText,
                        onEtValueChangeListener = {
                            onDashboardActionEvents(
                                DashboardActionEvents.OnStartingPointCancellationTextUpdateEvent(
                                    staringPoint = it,
                                    cancellationReason = null
                                )
                            )
                        },
                        etPlaceHolder = stringResource(R.string.enter_starting_point_text),
                        isShowError = dashboardUIState.showStartingPointError,
                        etErrorText = stringResource(R.string.enter_starting_point_error_text),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    )

                    AppButton(
                        buttonText = stringResource(R.string.send_request_text),
                        modifier = Modifier.fillMaxWidth(),
                        isButtonLoading = dashboardUIState.isRequestingButtonLoading,
                        buttonClickEvent = {
                            onDashboardActionEvents(DashboardActionEvents.OnSendRequestButtonClick)
                        }
                    )

                }

            }
        }

        if (dashboardUIState.isShowCancelPassDialog) {
            Dialog(
                onDismissRequest = {
                    onDashboardActionEvents(DashboardActionEvents.OnCancelDialogDismissEvent)
                },
                properties = DialogProperties(
                    dismissOnClickOutside = !dashboardUIState.isCancelButtonLoading,
                    dismissOnBackPress = !dashboardUIState.isCancelButtonLoading,
                    usePlatformDefaultWidth = false
                )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(shape = RoundedCornerShape(5))
                        .background(color = colorResource(R.color.white))
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {


                    Text(
                        text = stringResource(R.string.cancelling_new_pass),
                        fontFamily = FontFamily(semiBoldFont),
                        fontSize = 24.sp
                    )
                    Text(
                        text = stringResource(R.string.cancelling_new_pass_desc),
                        fontFamily = FontFamily(normalFont),
                        fontSize = 18.sp
                    )

                    CommonTextField(
                        modifier = Modifier.fillMaxWidth(),
                        etValue = dashboardUIState.reasonForCancellation,
                        onEtValueChangeListener = {
                            onDashboardActionEvents(
                                DashboardActionEvents.OnStartingPointCancellationTextUpdateEvent(
                                    staringPoint = null,
                                    cancellationReason = it
                                )
                            )
                        },
                        etPlaceHolder = stringResource(R.string.enter_cancellation_reason),
                        isShowError = dashboardUIState.showReasonForCancellationError,
                        etErrorText = stringResource(R.string.enter_cancellation_reason_error),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    )

                    AppButton(
                        buttonText = stringResource(R.string.cancel_request_text),
                        modifier = Modifier.fillMaxWidth(),
                        isButtonLoading = dashboardUIState.isCancelButtonLoading,
                        buttonClickEvent = {
                            onDashboardActionEvents(DashboardActionEvents.OnCancelRequestButtonClick)
                        }
                    )

                }

            }
        }

        if (dashboardUIState.showInfoDialog) {
            TitleWithButtonInfoDialog(
                modifier = Modifier.fillMaxWidth().padding(16.dp),
                dialogText = dashboardUIState.infoDialogTitleText,
                dialogButtonText = stringResource(R.string.okay_text),
                onDismissEvent = {
                    onDashboardActionEvents(DashboardActionEvents.OnInfoDialogOpenDismissEvent(false))
                }
            )
        }
    }

}



@Preview
@Composable
fun PreviewDashboardScreen() {
    DashboardScreen(
        modifier = Modifier.fillMaxSize(),
        loginUserTypeEnum = LoginUserTypeEnum.BUSINCHARGE,
        dashboardUIState = DashboardUIState(
            userName = "Suman",
            userAvatar = "uhhh.jpg",
            userCollegeId = "SMCE12345",
            isShowCancelPassDialog = false
        ),
        onDashboardActionEvents = {

        }
    )
}