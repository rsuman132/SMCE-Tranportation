package com.android.smcetransport.app.screens.dashboard

sealed class DashboardActionEvents {

    data class OnLogoutDialogShowEvent(
        val show : Boolean
    ) : DashboardActionEvents()

}