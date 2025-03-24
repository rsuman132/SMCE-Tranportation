package com.android.smcetransport.app.screens.bus_request_status.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
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
import com.android.smcetransport.app.R
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.ui.components.AppButton
import com.android.smcetransport.app.ui.components.AppToolBar
import com.android.smcetransport.app.ui.components.BusStatusItemView
import com.android.smcetransport.app.ui.components.CommonTextField
import com.android.smcetransport.app.ui.components.DepartmentAndYearFilter
import com.android.smcetransport.app.ui.components.TextWithIconView
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont
import com.android.smcetransport.app.ui.theme.theme.theme.normalFont
import com.android.smcetransport.app.ui.theme.theme.theme.semiBoldFont
import com.android.smcetransport.app.utils.ContextExtension.requestStatusEnumToString
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@Composable
fun BusRequestStatusScreen(
    modifier: Modifier = Modifier,
    busRequestStatusUIState: BusRequestStatusUIState,
    onBusRequestPageActionEvent : (BusRequestPageActionEvent) -> Unit
) {

    val pagerState = rememberPagerState { 2 }
    val coroutineScope = rememberCoroutineScope()

    val screenHalfHeightInDp = (LocalConfiguration.current.screenHeightDp/2).dp

    val context = LocalContext.current

    LaunchedEffect(pagerState) {
        snapshotFlow {
            pagerState.currentPage
        }.distinctUntilChanged().collectLatest {
            onBusRequestPageActionEvent(BusRequestPageActionEvent.OnTabChangeEvent(pagerState.currentPage))
        }
    }


    Box(modifier = modifier
        .fillMaxSize()
        .background(color = colorResource(R.color.white)),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
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
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        DepartmentAndYearFilter(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            selectedDepartment = busRequestStatusUIState.studentSelectedDepartment,
                            selectedYearText = busRequestStatusUIState.studentSelectedYear,
                            departmentClick = {
                                onBusRequestPageActionEvent(
                                    BusRequestPageActionEvent.OnStudentDepartmentFilterEvent(it)
                                )
                            },
                            yearClick = {
                                onBusRequestPageActionEvent(
                                    BusRequestPageActionEvent.OnStudentYearFilterEvent(it)
                                )
                            },
                            applyFilterClick = {
                                onBusRequestPageActionEvent(
                                    BusRequestPageActionEvent.OnStudentFilterEvent
                                )
                            }
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            if(busRequestStatusUIState.isBusRequestStatusStudentLoading) {
                                CircularProgressIndicator()
                            } else {
                                if (busRequestStatusUIState.busRequestStatusStudentList.isNotEmpty()) {
                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 16.dp, vertical = 8.dp),
                                        verticalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        items(busRequestStatusUIState.busRequestStatusStudentList, key = {
                                            "${it.id}"
                                        }) {
                                            val amountText = if (it.amount != null && it.amount > 0.0) {
                                                "${it.amount}"
                                            } else {
                                                null
                                            }
                                            val statusText = context.requestStatusEnumToString(it.status)
                                            BusStatusItemView(
                                                modifier = Modifier.fillMaxWidth(),
                                                statusText = statusText,
                                                userName = "${it.requesterUserModel?.name}",
                                                userId = it.requesterUserModel?.collegeOrStaffId,
                                                userImageUrl = it.requesterUserModel?.imageUrl,
                                                userDepartment = "${it.requesterUserModel?.departmentModel?.departmentName}",
                                                userYear = it.requesterUserModel?.year,
                                                amountText = amountText,
                                                busNo = it.busAndRouteModel?.busNumber,
                                                busViaRoute = it.busAndRouteModel?.busRoute,
                                                pickUpPoint = "${it.pickupPoint}",
                                                requestStatus = it.status,
                                                onCancelClick = {
                                                    onBusRequestPageActionEvent(BusRequestPageActionEvent.UpdateCancelSelectedId(
                                                        id = it.id,
                                                        requesterId = it.requesterId
                                                    ))
                                                },
                                                onApproveClick = {
                                                    if (it.id == null) {
                                                        return@BusStatusItemView
                                                    }
                                                    onBusRequestPageActionEvent(
                                                        BusRequestPageActionEvent.OnApproveButtonEvent(
                                                            id = it.id,
                                                            loginUserTypeEnum = LoginUserTypeEnum.STUDENT
                                                        )
                                                    )
                                                }
                                            )
                                        }
                                    }
                                } else {
                                    Text(
                                        text = stringResource(R.string.no_bus_request_found),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .align(Alignment.Center)
                                            .padding(16.dp),
                                        textAlign = TextAlign.Center,
                                        fontFamily = FontFamily(mediumFont),
                                        fontSize = 16.sp,
                                        color = colorResource(R.color.black)
                                    )
                                }
                            }
                        }
                    }
                } else {

                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {

                        DepartmentAndYearFilter(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            selectedDepartment = busRequestStatusUIState.staffSelectedDepartment,
                            selectedYearText = null,
                            departmentClick = {
                                onBusRequestPageActionEvent(
                                    BusRequestPageActionEvent.OnStaffDepartmentFilterEvent(it)
                                )
                            },
                            yearClick = {},
                            applyFilterClick = {
                                onBusRequestPageActionEvent(BusRequestPageActionEvent.OnStaffFilterEvent)
                            }
                        )

                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            if (busRequestStatusUIState.isBusRequestStatusStaffLoading) {
                                CircularProgressIndicator()
                            } else {
                                if (busRequestStatusUIState.busRequestStatusStaffList.isNotEmpty()) {
                                    LazyColumn(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .padding(horizontal = 16.dp, vertical = 8.dp),
                                        verticalArrangement = Arrangement.spacedBy(16.dp)
                                    ) {
                                        items(busRequestStatusUIState.busRequestStatusStaffList, key = {
                                            "${it.id}"
                                        }) {
                                            val amountText = if (it.amount != null && it.amount > 0.0) {
                                                "${it.amount}"
                                            } else {
                                                null
                                            }
                                            val statusText = context.requestStatusEnumToString(it.status)
                                            BusStatusItemView(
                                                modifier = Modifier.fillMaxWidth(),
                                                userName = "${it.requesterUserModel?.name}",
                                                statusText = statusText,
                                                userId = it.requesterUserModel?.collegeOrStaffId,
                                                userImageUrl = it.requesterUserModel?.imageUrl,
                                                userDepartment = "${it.requesterUserModel?.departmentModel?.departmentName}",
                                                userYear = it.requesterUserModel?.year,
                                                pickUpPoint = "${it.pickupPoint}",
                                                amountText = amountText,
                                                busNo = it.busAndRouteModel?.busNumber,
                                                busViaRoute = it.busAndRouteModel?.busRoute,
                                                requestStatus = it.status,
                                                onCancelClick = {
                                                    onBusRequestPageActionEvent(BusRequestPageActionEvent.UpdateCancelSelectedId(
                                                        id = it.id,
                                                        requesterId = it.requesterId
                                                    ))
                                                },
                                                onApproveClick = {
                                                    if (it.id == null) {
                                                        return@BusStatusItemView
                                                    }
                                                    onBusRequestPageActionEvent(
                                                        BusRequestPageActionEvent.OnApproveButtonEvent(
                                                            id = it.id,
                                                            loginUserTypeEnum = LoginUserTypeEnum.STAFF
                                                        )
                                                    )
                                                }
                                            )
                                        }
                                    }
                                } else {
                                    Text(
                                        text = stringResource(R.string.no_bus_request_found),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .align(Alignment.Center)
                                            .padding(16.dp),
                                        textAlign = TextAlign.Center,
                                        fontFamily = FontFamily(mediumFont),
                                        fontSize = 16.sp,
                                        color = colorResource(R.color.black)
                                    )
                                }
                            }
                        }

                    }
                }
            }

        }

        if (busRequestStatusUIState.isShowCancelDialog) {
            Dialog(
                onDismissRequest = {
                    onBusRequestPageActionEvent(BusRequestPageActionEvent.CancelDialogDismissEvent)
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
                            onBusRequestPageActionEvent(BusRequestPageActionEvent.OnCancelReasonTextChange(it))
                        },
                        etPlaceHolder = stringResource(R.string.enter_cancellation_reason),
                        isShowError = busRequestStatusUIState.isShowReasonForCancellationError,
                        etErrorText = stringResource(R.string.enter_cancellation_reason_error),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done
                        )
                    )

                    AppButton(
                        buttonText = stringResource(R.string.cancel_request_text),
                        modifier = Modifier.fillMaxWidth(),
                        isButtonLoading = busRequestStatusUIState.isCancelRequestButtonLoading,
                        buttonClickEvent = {
                            onBusRequestPageActionEvent(BusRequestPageActionEvent.ValidateCancelRequest)
                        }
                    )

                }

            }
        }

        if (busRequestStatusUIState.isShowDepartmentListDialog) {

            Dialog(
                onDismissRequest = {
                    onBusRequestPageActionEvent(BusRequestPageActionEvent.OnDialogDismissEvent(
                        isDepartmentDialogDismiss = false,
                        isYearDialogDismiss = null
                    ))
                },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false
                )
            ) {

                Column(modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
                    .clip(shape = RoundedCornerShape(5))
                    .background(color = colorResource(R.color.white))
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Text(
                        text = stringResource(R.string.select_department_text),
                        fontFamily = FontFamily(semiBoldFont),
                        fontSize = 20.sp,
                        color = colorResource(R.color.black)
                    )

                    if (busRequestStatusUIState.departmentList.isNotEmpty()) {

                        LazyColumn(
                            modifier = Modifier.fillMaxWidth(),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                        ) {
                            items(
                                items = busRequestStatusUIState.departmentList
                            ) { value ->
                                val rightSideIcon = if (busRequestStatusUIState.selectedPage == 0) {
                                    if (value == busRequestStatusUIState.studentSelectedDepartment)
                                        painterResource(R.drawable.ic_check_circle) else null
                                } else {
                                    if (value == busRequestStatusUIState.staffSelectedDepartment)
                                        painterResource(R.drawable.ic_check_circle) else null
                                }
                                if (value != null) {
                                    TextWithIconView(
                                        modifier = Modifier.fillMaxWidth(),
                                        titleText = value,
                                        rightSideIcon = rightSideIcon,
                                        onItemClick = {
                                            onBusRequestPageActionEvent(BusRequestPageActionEvent.OnSelectedDeptYearText(
                                                deptText = value,
                                                yearText = null
                                            ))
                                        }
                                    )
                                }
                            }
                        }

                    } else {

                        Box(
                            modifier = Modifier.fillMaxWidth()
                                .height(screenHalfHeightInDp),
                            contentAlignment = Alignment.Center
                        ) {

                            Text(
                                text = stringResource(R.string.no_department_added),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .align(Alignment.Center)
                                    .padding(16.dp),
                                textAlign = TextAlign.Center,
                                fontFamily = FontFamily(mediumFont),
                                fontSize = 16.sp,
                                color = colorResource(R.color.black)
                            )

                        }

                    }
                }

            }

        }

        if (busRequestStatusUIState.isShowYearListDialog) {

            Dialog(
                onDismissRequest = {
                    onBusRequestPageActionEvent(BusRequestPageActionEvent.OnDialogDismissEvent(
                        isDepartmentDialogDismiss = null,
                        isYearDialogDismiss = false
                    ))
                },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false
                )
            ) {

                Column(modifier = Modifier.fillMaxWidth()
                    .padding(16.dp)
                    .clip(shape = RoundedCornerShape(5))
                    .background(color = colorResource(R.color.white))
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {

                    Text(
                        text = stringResource(R.string.select_year_text),
                        fontFamily = FontFamily(semiBoldFont),
                        fontSize = 20.sp,
                        color = colorResource(R.color.black)
                    )

                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        items(
                            items = busRequestStatusUIState.yearList
                        ) { value ->
                            val rightSideIcon = if (value == busRequestStatusUIState.studentSelectedYear)
                                painterResource(R.drawable.ic_check_circle) else null
                            TextWithIconView(
                                modifier = Modifier.fillMaxWidth(),
                                titleText = value,
                                rightSideIcon = rightSideIcon,
                                onItemClick = {
                                    onBusRequestPageActionEvent(BusRequestPageActionEvent.OnSelectedDeptYearText(
                                        deptText = null,
                                        yearText = value
                                    ))
                                }
                            )
                        }
                    }
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
        busRequestStatusUIState = BusRequestStatusUIState(),
        onBusRequestPageActionEvent = {

        }
    )
}