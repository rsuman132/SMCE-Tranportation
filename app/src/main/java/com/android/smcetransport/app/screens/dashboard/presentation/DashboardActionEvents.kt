package com.android.smcetransport.app.screens.dashboard.presentation

sealed class DashboardActionEvents {

    data class OnLogoutDialogShowEvent(
        val show : Boolean
    ) : DashboardActionEvents()

    data object OnLogoutTriggerEvent : DashboardActionEvents()

    data object OnManageDepartmentClickEvent : DashboardActionEvents()

    data object OnManageBusClickEvent : DashboardActionEvents()
}