package com.android.smcetransport.app.screens.department.presentation

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

}