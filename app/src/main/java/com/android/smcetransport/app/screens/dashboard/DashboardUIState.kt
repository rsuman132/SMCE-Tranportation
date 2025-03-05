package com.android.smcetransport.app.screens.dashboard

data class DashboardUIState(
    val userName : String = "",
    val userAvatar : String = "",
    val userCollegeId : String = "",
    val isShowLogoutDialog : Boolean = false
)