package com.android.smcetransport.app.screens.bus_request_status.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
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
import com.android.smcetransport.app.R
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.screens.dashboard.presentation.DashboardActionEvents
import com.android.smcetransport.app.ui.components.AppButton
import com.android.smcetransport.app.ui.components.AppToolBar
import com.android.smcetransport.app.ui.components.BusStatusItemView
import com.android.smcetransport.app.ui.components.CommonTextField
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont
import com.android.smcetransport.app.ui.theme.theme.theme.normalFont
import com.android.smcetransport.app.ui.theme.theme.theme.semiBoldFont
import kotlinx.coroutines.launch

@Composable
fun BusRequestStatusScreen(
    modifier: Modifier = Modifier,
    busRequestStatusUIState: BusRequestStatusUIState,
    onBusRequestPageActionEvent : (BusRequestPageActionEvent) -> Unit
) {

    val pagerState = rememberPagerState { 2 }
    val coroutineScope = rememberCoroutineScope()


    Box(modifier = modifier.fillMaxSize()
        .background(color = colorResource(R.color.white)),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.white))
        ) {

            AppToolBar(
                modifier = Modifier.fillMaxWidth(),
                onToolBarStartIconClick = {
                    onBusRequestPageActionEvent(BusRequestPageActionEvent.OnBackPressEvent)
                }
            )

            TabRow(
                selectedTabIndex = pagerState.currentPage,
                modifier = Modifier.fillMaxWidth(),
                containerColor = colorResource(R.color.white)
            ) {
                val isSelected = pagerState.currentPage == busRequestStatusUIState.selectedPage
                Tab(
                    selected = isSelected,
                    text = {
                        Text(
                            text = stringResource(R.string.student_text),
                            fontFamily = FontFamily(mediumFont)
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    }
                )
                Tab(
                    selected = isSelected,
                    text = {
                        Text(
                            text = stringResource(R.string.staff_text),
                            fontFamily = FontFamily(mediumFont)
                        )
                    },
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    }
                )
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
            ) { page ->
                if (page == 0) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if(busRequestStatusUIState.isBusRequestStatusStudentLoading) {
                            CircularProgressIndicator()
                        } else {
                            if (busRequestStatusUIState.busRequestStatusStudentList.isNotEmpty()) {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(busRequestStatusUIState.busRequestStatusStudentList, key = {
                                        "${it.id}"
                                    }) {
                                        BusStatusItemView(
                                            modifier = Modifier.fillMaxWidth(),
                                            userName = "${it.requesterUserModel?.name}",
                                            userId = it.requesterUserModel?.collegeOrStaffId,
                                            userImageUrl = it.requesterUserModel?.imageUrl,
                                            userDepartment = "${it.requesterUserModel?.departmentModel?.departmentName}",
                                            userYear = it.requesterUserModel?.year,
                                            userLoginType = LoginUserTypeEnum.STUDENT,
                                            pickUpPoint = "${it.pickupPoint}",
                                            requestStatus = it.status,
                                            onCancelClick = {

                                            },
                                            onApproveClick = {

                                            }
                                        )
                                    }
                                }
                            } else {
                                Text(
                                    text = stringResource(R.string.no_bus_request_found),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .align(Alignment.Center),
                                    textAlign = TextAlign.Center,
                                    fontFamily = FontFamily(mediumFont),
                                    fontSize = 16.sp,
                                    color = colorResource(R.color.black)
                                )
                            }
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        if(busRequestStatusUIState.isBusRequestStatusStaffLoading) {
                            CircularProgressIndicator()
                        } else {
                            if (busRequestStatusUIState.busRequestStatusStaffList.isNotEmpty()) {
                                LazyColumn(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    items(busRequestStatusUIState.busRequestStatusStaffList, key = {
                                        "${it.id}"
                                    }) {
                                        BusStatusItemView(
                                            modifier = Modifier.fillMaxWidth(),
                                            userName = "${it.requesterUserModel?.name}",
                                            userId = it.requesterUserModel?.collegeOrStaffId,
                                            userImageUrl = it.requesterUserModel?.imageUrl,
                                            userDepartment = "${it.requesterUserModel?.departmentModel?.departmentName}",
                                            userYear = it.requesterUserModel?.year,
                                            userLoginType = LoginUserTypeEnum.STAFF,
                                            pickUpPoint = "${it.pickupPoint}",
                                            requestStatus = it.status,
                                            onCancelClick = {

                                            },
                                            onApproveClick = {

                                            }
                                        )
                                    }
                                }
                            } else {
                                Text(
                                    text = stringResource(R.string.no_bus_request_found),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .align(Alignment.Center),
                                    textAlign = TextAlign.Center,
                                    fontFamily = FontFamily(mediumFont),
                                    fontSize = 16.sp,
                                    color = colorResource(R.color.black)
                                )
                            }
                        }
                    }
                }
                onBusRequestPageActionEvent(BusRequestPageActionEvent.OnTabChangeEvent(page))
            }

        }

        if (busRequestStatusUIState.isShowCancelDialog) {
            Dialog(
                onDismissRequest = {

                },
                properties = DialogProperties(
                    dismissOnClickOutside = !busRequestStatusUIState.isCancelRequestButtonLoading,
                    dismissOnBackPress = !busRequestStatusUIState.isCancelRequestButtonLoading,
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
                        text = stringResource(R.string.cancel_confirmation_title),
                        fontFamily = FontFamily(semiBoldFont),
                        fontSize = 24.sp
                    )
                    Text(
                        text = stringResource(R.string.cancel_confirmation_desc),
                        fontFamily = FontFamily(normalFont),
                        fontSize = 18.sp
                    )

                    CommonTextField(
                        modifier = Modifier.fillMaxWidth(),
                        etValue = busRequestStatusUIState.reasonForCancellation,
                        onEtValueChangeListener = {

                        },
                        etPlaceHolder = stringResource(R.string.enter_cancellation_reason),
                        isShowError = busRequestStatusUIState.isShowReasonForCancellationError,
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
                        isButtonLoading = busRequestStatusUIState.isCancelRequestButtonLoading,
                        buttonClickEvent = {

                        }
                    )

                }

            }
        }

    }
}


@Preview
@Composable
fun PreviewRequestStatusScreen() {
    BusRequestStatusScreen(
        modifier = Modifier.fillMaxSize(),
        busRequestStatusUIState = BusRequestStatusUIState(
            isShowCancelDialog = true
        ),
        onBusRequestPageActionEvent = {

        }
    )
}