package com.android.smcetransport.app.screens.bus_request_status.presentation

import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.ui.components.model.DropDownModel

data class BusRequestStatusApproveUIState(
    val pickupPointText : String = "",
    val isShowPickupPointError : Boolean = false,
    val amountText : String = "",
    val isShowAmountError : Boolean = false,
    val selectedBusDropDown : Boolean = false,
    val selectedBusText : String = "",
    val selectedBusDropDownId : String = "",
    val selectedBusShowError : Boolean = false,
    val isShowButtonLoading : Boolean = false,
    val id : String = "",
    val loginUserTypeEnum: LoginUserTypeEnum = LoginUserTypeEnum.STUDENT,
    val isBusListApiLoading : Boolean = false,
    val isBusRequestListLoading : Boolean = false,
    val busListDropDownList : List<DropDownModel> = listOf(),
    val requesterId : String? = null
)
