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


    data class OnStudentDepartmentFilterEvent(
        val isClear : Boolean
    ) : BusRequestPageActionEvent()

    data class OnStudentYearFilterEvent(
        val isClear : Boolean
    ) : BusRequestPageActionEvent()

    data class OnStaffDepartmentFilterEvent(
        val isClear : Boolean
    ) : BusRequestPageActionEvent()

    data object OnStudentFilterEvent : BusRequestPageActionEvent()

    data object OnStaffFilterEvent : BusRequestPageActionEvent()

    data class OnDialogDismissEvent(
        val isDepartmentDialogDismiss : Boolean?,
        val isYearDialogDismiss : Boolean?,
    ) : BusRequestPageActionEvent()

    data class OnSelectedDeptYearText(
        val deptText : String?,
        val yearText : String?
    ) : BusRequestPageActionEvent()

}