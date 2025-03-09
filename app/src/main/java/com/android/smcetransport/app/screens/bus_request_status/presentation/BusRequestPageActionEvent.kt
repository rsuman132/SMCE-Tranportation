package com.android.smcetransport.app.screens.bus_request_status.presentation

import com.android.smcetransport.app.core.enum.LoginUserTypeEnum

sealed class BusRequestPageActionEvent {

    data object OnBackPressEvent : BusRequestPageActionEvent()

    data class OnTabChangeEvent(
        val selectedPos : Int
    ) : BusRequestPageActionEvent()

    data class OnCancelReasonTextChange(
        val cancelReason : String
    ) : BusRequestPageActionEvent()

    data object ValidateCancelRequest : BusRequestPageActionEvent()

    data class UpdateCancelSelectedId(
        val id : String?,
        val requesterId : String?
    ) : BusRequestPageActionEvent()

    data object CancelDialogDismissEvent : BusRequestPageActionEvent()

    data class OnApproveButtonEvent(
        val id : String,
        val loginUserTypeEnum: LoginUserTypeEnum
    ) : BusRequestPageActionEvent()

}