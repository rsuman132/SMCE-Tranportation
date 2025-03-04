package com.android.smcetransport.app.screens.otp_verification.domain

import com.google.firebase.auth.PhoneAuthCredential

class OTPVerificationUseCase(
    private val otpVerificationRepository: OTPVerificationRepository
) {
    suspend fun verifyOTP(phoneAuthCredential: PhoneAuthCredential) =
        otpVerificationRepository.verifyOTP(phoneAuthCredential)
}