package com.android.smcetransport.app.screens.bus_managment.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.smcetransport.app.R
import com.android.smcetransport.app.ui.components.AppButton
import com.android.smcetransport.app.ui.components.AppToolBar
import com.android.smcetransport.app.ui.components.CommonTextField

@Composable
fun AddBusScreen(
    modifier: Modifier = Modifier,
    addBusUIState : AddBusUIState,
    onAddBusActionEvent : (AddBusActionEvent) -> Unit
) {

    Box(modifier = modifier
        .fillMaxSize()
        .background(color = colorResource(R.color.white)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AppToolBar(
                modifier = Modifier.fillMaxWidth(),
                toolBarBgColor = colorResource(R.color.white),
                onToolBarStartIconClick = {
                    onAddBusActionEvent(AddBusActionEvent.OnBackPressEvent)
                }
            )
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                CommonTextField(
                    etValue = addBusUIState.busNumber,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onEtValueChangeListener = {
                        onAddBusActionEvent(AddBusActionEvent.OnBusTextFieldUpdateEvent(
                            busNo = it,
                            busRegisterNo = null,
                            busStartingPoint = null,
                            busRoute = null,
                            busDriverName = null
                        ))
                    },
                    etPlaceHolder = stringResource(R.string.enter_bus_number_text),
                    isShowError = addBusUIState.isShowBusNumberError,
                    etErrorText = stringResource(R.string.enter_bus_number_error_text),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )
                CommonTextField(
                    etValue = addBusUIState.busRegisterNumber,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onEtValueChangeListener = {
                        onAddBusActionEvent(AddBusActionEvent.OnBusTextFieldUpdateEvent(
                            busNo = null,
                            busRegisterNo = it,
                            busStartingPoint = null,
                            busRoute = null,
                            busDriverName = null
                        ))
                    },
                    etPlaceHolder = stringResource(R.string.enter_register_number_text),
                    isShowError = addBusUIState.isShowBusRegisterNumberError,
                    etErrorText = stringResource(R.string.enter_register_number_error_text),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Characters,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )
                CommonTextField(
                    etValue = addBusUIState.busStartingPoint,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onEtValueChangeListener = {
                        onAddBusActionEvent(AddBusActionEvent.OnBusTextFieldUpdateEvent(
                            busNo = null,
                            busRegisterNo = null,
                            busStartingPoint = it,
                            busRoute = null,
                            busDriverName = null
                        ))
                    },
                    etPlaceHolder = stringResource(R.string.enter_starting_point_text),
                    isShowError = addBusUIState.isShowBusStaringPointError,
                    etErrorText = stringResource(R.string.enter_starting_point_error_text),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )
                CommonTextField(
                    etValue = addBusUIState.busRoute,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onEtValueChangeListener = {
                        onAddBusActionEvent(AddBusActionEvent.OnBusTextFieldUpdateEvent(
                            busNo = null,
                            busRegisterNo = null,
                            busStartingPoint = null,
                            busRoute = it,
                            busDriverName = null
                        ))
                    },
                    etPlaceHolder = stringResource(R.string.enter_bus_route_text),
                    isShowError = addBusUIState.isShowBusRouteError,
                    etErrorText = stringResource(R.string.enter_bus_route_error_text),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )
                CommonTextField(
                    etValue = addBusUIState.busDriverName,
                    modifier = Modifier
                        .fillMaxWidth(),
                    onEtValueChangeListener = {
                        onAddBusActionEvent(AddBusActionEvent.OnBusTextFieldUpdateEvent(
                            busNo = null,
                            busRegisterNo = null,
                            busStartingPoint = null,
                            busRoute = null,
                            busDriverName = it
                        ))
                    },
                    etPlaceHolder = stringResource(R.string.enter_driver_name_text),
                    isShowError = addBusUIState.isShowBusDriverNameError,
                    etErrorText = stringResource(R.string.enter_driver_name_error_text),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    )
                )
            }

            AppButton(
                buttonText = stringResource(R.string.add_bus_text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                buttonClickEvent = {
                    onAddBusActionEvent(AddBusActionEvent.OnAddBusBtnEvent)
                },
                isButtonLoading = addBusUIState.isAddBusLoading
            )

        }
    }

}


@Preview
@Composable
fun PreviewAddBusScreen() {
    AddBusScreen(
        modifier = Modifier.fillMaxSize(),
        addBusUIState = AddBusUIState(),
        onAddBusActionEvent = {

        }
    )
}
