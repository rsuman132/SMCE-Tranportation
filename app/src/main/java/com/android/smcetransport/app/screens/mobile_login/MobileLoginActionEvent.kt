package com.android.smcetransport.app.screens.mobile_login

sealed class MobileLoginActionEvent {

    data object OnBackPressEvent : MobileLoginActionEvent()

    data class OnPhoneNumberChangeEvent(
        val phoneNumber : String
    ) : MobileLoginActionEvent()

    data object OnGetOTPPressEvent : MobileLoginActionEvent()
}