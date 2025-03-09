package com.android.smcetransport.app.screens.bus_request_status.presentation

import com.android.smcetransport.app.core.dto.BusRequestModel

data class BusRequestStatusUIState(
    val selectedPage : Int = 0,
    val busRequestStatusStudentList : List<BusRequestModel> = listOf(),
    val busRequestStatusStaffList : List<BusRequestModel> = listOf(),
    val isBusRequestStatusStudentLoading : Boolean = false,
    val isBusRequestStatusStaffLoading : Boolean = false,
    val isShowCancelDialog : Boolean = false,
    val reasonForCancellation : String = "",
    val isShowReasonForCancellationError : Boolean = false,
    val isCancelRequestButtonLoading : Boolean = false,
    val selectedRequestId : String? = null,
    val selectedRequesterId : String? = null
)
