package com.android.smcetransport.app.screens.otp_verification.presentation

import androidx.compose.runtime.Stable
import com.android.smcetransport.app.screens.otp_verification.utils.OTPProcessEnum
import com.google.firebase.auth.PhoneAuthProvider

@Stable
data class OTPVerificationUIState(
    val phoneNumber : String = "",
    val otpValue : String = "",
    val timerCount : Int = 60,
    val otpLength : Int = 6,
    val isShaking : Boolean = false,
    val otpProcessEnum : OTPProcessEnum? = null,
    val isButtonLoading : Boolean = false,
    val verificationId : String? = null,
    val token: PhoneAuthProvider.ForceResendingToken? = null,
    val userPhoneNumber : String? = null
)
