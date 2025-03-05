package com.android.smcetransport.app.screens.otp_verification.data

import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.screens.otp_verification.domain.OTPVerificationRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class OTPVerificationRepositoryImpl : OTPVerificationRepository {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override suspend fun verifyOTP(phoneAuthCredential: PhoneAuthCredential): Flow<NetworkResult<String?>> = flow {
        emit(NetworkResult.Loading())
        try {
            val firebaseAuthResult = firebaseAuth.signInWithCredential(phoneAuthCredential).await()
            emit(NetworkResult.Success(data = firebaseAuthResult.user?.phoneNumber))
        } catch (e: Exception) {
            emit(NetworkResult.Error(message = e.message))
        }
    }


}