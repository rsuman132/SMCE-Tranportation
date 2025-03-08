package com.android.smcetransport.app.screens.dashboard.presentation

data class DashboardUIState(
    val userName : String = "",
    val userAvatar : String = "",
    val userCollegeId : String = "",
    val isShowLogoutDialog : Boolean = false
)