package com.android.smcetransport.app.screens.otp_verification.utils

import android.app.Activity
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class VerifyPhoneNumber(
    private val activity : Activity
) {

    private val firebaseAuth : FirebaseAuth = FirebaseAuth.getInstance()

    fun initVerifyPhoneNumber(
        phoneNumber : String,
        resendToken: PhoneAuthProvider.ForceResendingToken?,
        onPhoneAuthCredentialSuccess : (PhoneAuthCredential) -> Unit,
        onCodeSendSuccess : (String, PhoneAuthProvider.ForceResendingToken) -> Unit,
        onError : (Exception) -> Unit
    ) {
        val phoneAuthOptions = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                    onPhoneAuthCredentialSuccess(phoneAuthCredential)
                }

                override fun onVerificationFailed(firebaseException: FirebaseException) {
                    onError(firebaseException)
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    super.onCodeSent(verificationId, token)
                    onCodeSendSuccess(verificationId, token)
                }
            }).apply {
                if (resendToken != null) {
                    this.setForceResendingToken(resendToken)
                }
            }.build()
        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)
    }

}