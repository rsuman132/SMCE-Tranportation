package com.android.smcetransport.app.screens.otp_verification.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smcetransport.app.core.dto.UserModel
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.core.enum.RequestStatusEnum
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.android.smcetransport.app.core.utils.StringExtensions.removeCountryCodeFromPhone
import com.android.smcetransport.app.screens.otp_verification.domain.OTPVerificationUseCase
import com.android.smcetransport.app.screens.otp_verification.utils.OTPProcessEnum
import com.android.smcetransport.app.core.model.PhoneNumberRequestModel
import com.android.smcetransport.app.core.model.StatusAndIdRequestModel
import com.android.smcetransport.app.screens.splash.domain.SplashUseCase
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
import kotlinx.serialization.ExperimentalSerializationApi

class OTPVerificationViewModel(
    private val otpVerificationUseCase: OTPVerificationUseCase,
    private val splashUseCase: SplashUseCase,
    private val sharedPrefs: SharedPrefs
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
                        updateButtonLoading(false)
                    }
                    is NetworkResult.Success -> {
                        val phoneNumberWithoutPlusCode = networkResult.data?.removeCountryCodeFromPhone()
                        val loginUserTypeEnum = sharedPrefs.getLoginType()
                        if (loginUserTypeEnum != null) {
                            hitProfileApi(phoneNumber = phoneNumberWithoutPlusCode)
                        } else {
                            networkResult.message?.let {
                                _errorMessageShow.send("Something went wrong")
                            }
                            updateButtonLoading(false)
                        }
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalSerializationApi::class)
    suspend fun hitProfileApi(phoneNumber : String?) {
        splashUseCase.getUserProfile(PhoneNumberRequestModel(phone = phoneNumber)).collectLatest { networkResult ->
            when(networkResult) {
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Error -> {
                    if (networkResult.code == 404) {
                        otpVerificationUIState.update {
                            it.copy(
                                otpProcessEnum = OTPProcessEnum.SUCCESS,
                                userPhoneNumber = null
                            )
                        }
                    } else {
                        networkResult.message?.let {
                            _errorMessageShow.send(it)
                        }
                        updateButtonLoading(false)
                    }
                }
                is NetworkResult.Success -> {
                    val userModel = networkResult.data?.data
                    sharedPrefs.setUserModel(userModel)
                    if (userModel?.id != null) {
                        hitBusRequestByStatusAndId(userModel)
                    } else {
                        otpVerificationUIState.update {
                            it.copy(
                                otpProcessEnum = OTPProcessEnum.SUCCESS,
                                userPhoneNumber = userModel?.phone
                            )
                        }
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private suspend fun hitBusRequestByStatusAndId(userModel: UserModel) {
        val loginUserTypeEnum = sharedPrefs.getLoginType()
        val statusAndIdRequestModel = StatusAndIdRequestModel(
            status = listOf(RequestStatusEnum.REQUESTED, RequestStatusEnum.ACCEPTED),
            studentId = if (loginUserTypeEnum == LoginUserTypeEnum.STUDENT) userModel.id else null,
            staffId = if (loginUserTypeEnum == LoginUserTypeEnum.STAFF) userModel.id else null
        )
        splashUseCase.busRequestsByStatusAndRequesterId(
            statusAndIdRequestModel = statusAndIdRequestModel
        ).collectLatest { networkResult ->
            when(networkResult) {
                is NetworkResult.Error -> {
                    otpVerificationUIState.update {
                        it.copy(
                            otpProcessEnum = OTPProcessEnum.SUCCESS,
                            userPhoneNumber = userModel.phone
                        )
                    }
                }
                is NetworkResult.Loading -> {
                    // Loading State...
                }
                is NetworkResult.Success -> {
                    sharedPrefs.setRequestPassModelList(networkResult.data?.data)
                    otpVerificationUIState.update {
                        it.copy(
                            otpProcessEnum = OTPProcessEnum.SUCCESS,
                            userPhoneNumber = userModel.phone
                        )
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