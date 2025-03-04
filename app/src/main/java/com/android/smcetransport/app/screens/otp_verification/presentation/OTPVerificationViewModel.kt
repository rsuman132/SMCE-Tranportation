package com.android.smcetransport.app.screens.otp_verification.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smcetransport.app.core.api.NetworkResult
import com.android.smcetransport.app.screens.otp_verification.domain.OTPVerificationUseCase
import com.android.smcetransport.app.screens.otp_verification.utils.OTPProcessEnum
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OTPVerificationViewModel(
    private val otpVerificationUseCase: OTPVerificationUseCase
) : ViewModel() {


    private var timerJob : Job? = null

    companion object {
        private const val DELAY_MILLIS = 1000L
    }

    var otpVerificationUIState = MutableStateFlow(OTPVerificationUIState())
        private set

    private val _errorMessageShow = Channel<String>()
    val errorMessageShow = _errorMessageShow.receiveAsFlow()

    init {
        startTimer()
    }


    fun startTimer(timerValue: Int = 60) {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            otpVerificationUIState.update {
                it.copy(
                    timerCount = timerValue,
                    otpProcessEnum = OTPProcessEnum.TRIGGERED
                )
            }
            (timerValue - 1 downTo 0)
                .asSequence()
                .asFlow()
                .onEach {
                    delay(DELAY_MILLIS)
                }
                .onCompletion {
                    otpVerificationUIState.update {
                        it.copy(timerCount = 0, otpProcessEnum = OTPProcessEnum.PROCESSING)
                    }
                    timerJob = null
                }
                .cancellable()
                .collect { timerCount ->
                    otpVerificationUIState.update {
                        it.copy(timerCount = timerCount, otpProcessEnum = null)
                    }
                }
        }
    }


    fun updateOTPValue(otpValue : String) {
        otpVerificationUIState.update {
            it.copy(otpValue = otpValue)
        }
    }


    fun enableDisableShakeAnim(isShaking : Boolean) {
        otpVerificationUIState.update {
            it.copy(isShaking = isShaking)
        }
    }


    fun updateVerificationIdAndResendToken(
        verificationId : String?,
        token: PhoneAuthProvider.ForceResendingToken?
    ) {
        otpVerificationUIState.update {
            it.copy(
                verificationId = verificationId,
                token = token
            )
        }
    }



    fun updateButtonLoading(
        isLoading : Boolean
    ) {
        otpVerificationUIState.update {
            it.copy(isButtonLoading = isLoading)
        }
    }


    fun startOTPVerification(phoneAuthCredential: PhoneAuthCredential) {
        viewModelScope.launch(IO) {
            otpVerificationUseCase.verifyOTP(phoneAuthCredential).collectLatest { networkResult ->
                when(networkResult) {
                    is NetworkResult.Loading -> {
                        updateButtonLoading(true)
                    }
                    is NetworkResult.Error -> {
                        networkResult.message?.let {
                            _errorMessageShow.send(it)
                        }
                    }
                    is NetworkResult.Success -> {
                        otpVerificationUIState.update {
                            it.copy(otpProcessEnum = OTPProcessEnum.SUCCESS)
                        }
                    }
                }
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        timerJob?.cancel()
    }


}