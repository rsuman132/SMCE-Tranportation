package com.android.smcetransport.app.screens.otp_verification.presentation

import com.google.firebase.auth.PhoneAuthProvider

sealed class OTPVerificationActionEvent {

    data object OnBackPressEvent : OTPVerificationActionEvent()

    data class OnOTPTextChangeEvent(
        val otpText : String
    ) : OTPVerificationActionEvent()

    data object OnVerifyOTPEvent : OTPVerificationActionEvent()

    data object OnShakeCompleteEvent : OTPVerificationActionEvent()

    data object OnResendOTPEvent : OTPVerificationActionEvent()

    data class OnVerificationIdAndResendTokenEvent(
        val verificationId : String?,
        val token: PhoneAuthProvider.ForceResendingToken?
    ) : OTPVerificationActionEvent()

    data class OnOtpButtonLoadingEvent(
        val isLoading : Boolean
    ) : OTPVerificationActionEvent()

    data object OnOtpSuccessEvent : OTPVerificationActionEvent()

}