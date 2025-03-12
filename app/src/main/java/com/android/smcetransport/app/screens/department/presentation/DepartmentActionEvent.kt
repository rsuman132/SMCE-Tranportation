package com.android.smcetransport.app.screens.department.presentation

import com.android.smcetransport.app.core.enum.LoginUserTypeEnum

sealed class DepartmentActionEvent {

    data object OnBackPressEvent : DepartmentActionEvent()

    data object OnAddDepartmentEvent : DepartmentActionEvent()

    data class OnDepartmentNameUpdate(
        val name : String
    ) : DepartmentActionEvent()

    data class OnDepartmentCodeUpdate(
        val code : String
    ) : DepartmentActionEvent()

    data object OnAddDepartmentEventInDialog : DepartmentActionEvent()

    data object OnAddDepartmentDialogDismiss : DepartmentActionEvent()

    data class OnStudentYearListDialogShowHideEvent(
        val show : Boolean,
        val selectedId : String?,
        val selectedDepartmentName : String?
    ) : DepartmentActionEvent()


    data class OnSelectedYearOrStaffId(
        val selectedYearOrStaff : String?,
        val loginUserTypeEnum: LoginUserTypeEnum
    ) : DepartmentActionEvent()

}