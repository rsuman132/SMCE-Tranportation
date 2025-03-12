package com.android.smcetransport.app.screens.bus_request_status.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.android.smcetransport.app.R
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.ui.components.AppButton
import com.android.smcetransport.app.ui.components.AppToolBar
import com.android.smcetransport.app.ui.components.CommonDropDown
import com.android.smcetransport.app.ui.components.CommonTextField

@Composable
fun BusRequestStatusApproveScreen(
    modifier: Modifier = Modifier,
    busRequestStatusApproveUIState : BusRequestStatusApproveUIState,
    onBusRequestApproveActionEvent : (BusRequestStatusApproveEvent) -> Unit
) {

    Column(
        modifier = modifier.fillMaxSize()
            .background(color = colorResource(R.color.white))
    ) {
        AppToolBar(
            modifier = Modifier.fillMaxWidth(),
            toolBarBgColor = colorResource(R.color.white),
            onToolBarStartIconClick = {
                onBusRequestApproveActionEvent(BusRequestStatusApproveEvent.OnBackPressEvent)
            },
            isShowLoading = busRequestStatusApproveUIState.isBusRequestListLoading
        )

        if (!busRequestStatusApproveUIState.isBusRequestListLoading) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CommonTextField(
                    etValue = busRequestStatusApproveUIState.pickupPointText,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    onEtValueChangeListener = {
                        onBusRequestApproveActionEvent(BusRequestStatusApproveEvent.OnTextChangeEvent(
                            pickUpPoint = it,
                            amountText = null
                        ))
                    },
                    etPlaceHolder = stringResource(R.string.enter_starting_point_text),
                    isShowError = busRequestStatusApproveUIState.isShowPickupPointError,
                    etErrorText = stringResource(R.string.enter_starting_point_error_text),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )
                if (busRequestStatusApproveUIState.loginUserTypeEnum == LoginUserTypeEnum.STUDENT) {
                    CommonTextField(
                        etValue = busRequestStatusApproveUIState.amountText,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        onEtValueChangeListener = {
                            onBusRequestApproveActionEvent(BusRequestStatusApproveEvent.OnTextChangeEvent(
                                pickUpPoint = null,
                                amountText = it
                            ))
                        },
                        etPlaceHolder = stringResource(R.string.enter_amount_text),
                        isShowError = busRequestStatusApproveUIState.isShowAmountError,
                        etErrorText = stringResource(R.string.enter_amount_error_text),
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        singleLine = false
                    )
                }
                CommonDropDown(
                    modifier = modifier
                        .fillMaxWidth().padding(vertical = 4.dp),
                    etValue = busRequestStatusApproveUIState.selectedBusText,
                    onEtValueChangeListener = {},
                    etPlaceHolder = stringResource(R.string.select_your_bus),
                    isShowError = busRequestStatusApproveUIState.selectedBusShowError,
                    etErrorText = stringResource(R.string.select_your_bus_error),
                    dropDownList = busRequestStatusApproveUIState.busListDropDownList,
                    isDropDownExpanded = busRequestStatusApproveUIState.selectedBusDropDown,
                    isEnable = !busRequestStatusApproveUIState.isBusListApiLoading,
                    onDropDownClickAndExpandedState = { dropDownModel, isExpanded ->
                        if (busRequestStatusApproveUIState.busListDropDownList.isNotEmpty()) {
                            onBusRequestApproveActionEvent(
                                BusRequestStatusApproveEvent.OnDropDownChangeEvent(
                                    busSelectedText = dropDownModel?.dropDownText,
                                    selectedBusId = dropDownModel?.dropDownId,
                                    isExpanded = isExpanded
                                )
                            )
                        } else {
                            onBusRequestApproveActionEvent(BusRequestStatusApproveEvent.OnBusDropDownListApiHitEvent)
                        }
                    }
                )
            }
            AppButton(
                buttonText = stringResource(R.string.approve_text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                buttonClickEvent = {
                    onBusRequestApproveActionEvent(BusRequestStatusApproveEvent.OnApproveButtonEvent)
                },
                isButtonLoading = busRequestStatusApproveUIState.isShowButtonLoading
            )
        }

    }

}