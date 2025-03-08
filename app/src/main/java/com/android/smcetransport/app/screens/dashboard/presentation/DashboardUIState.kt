package com.android.smcetransport.app.screens.dashboard.presentation

data class DashboardUIState(
    val userName : String = "",
    val userAvatar : String = "",
    val userCollegeId : String = "",
    val isShowLogoutDialog : Boolean = false,
    val isShowRequestPassDialog : Boolean = false,
    val startingPointText : String = "",
    val showStartingPointError : Boolean = false,
    val isRequestingButtonLoading : Boolean = false,
    val isShowCancelPassDialog : Boolean = false,
    val reasonForCancellation : String = "",
    val showReasonForCancellationError : Boolean = false,
    val isCancelButtonLoading : Boolean = false,
    val showInfoDialog : Boolean = false,
    val infoDialogTitleText : String = ""
)