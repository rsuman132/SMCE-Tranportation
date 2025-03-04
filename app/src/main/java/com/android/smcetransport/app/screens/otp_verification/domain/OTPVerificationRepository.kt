package com.android.smcetransport.app.screens.otp_verification.domain

import com.android.smcetransport.app.core.api.NetworkResult
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.flow.Flow

interface OTPVerificationRepository {

    suspend fun verifyOTP(phoneAuthCredential: PhoneAuthCredential) : Flow<NetworkResult<String?>>

}