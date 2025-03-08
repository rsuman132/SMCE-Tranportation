package com.android.smcetransport.app.screens.splash.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smcetransport.app.core.dto.UserModel
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.core.enum.RequestStatusEnum
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.android.smcetransport.app.core.utils.StringExtensions.removeCountryCodeFromPhone
import com.android.smcetransport.app.core.model.PhoneNumberRequestModel
import com.android.smcetransport.app.core.model.StatusAndIdRequestModel
import com.android.smcetransport.app.screens.otp_verification.utils.OTPProcessEnum
import com.android.smcetransport.app.screens.splash.domain.SplashUseCase
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

class SplashScreenViewModel(
    private val sharedPrefs: SharedPrefs,
    private val splashUseCase: SplashUseCase
) : ViewModel() {

    private val firebaseAuth = FirebaseAuth.getInstance()

    var splashScreenUIState = MutableStateFlow(SplashScreenUIState())
        private set

    init {
        triggerApiCall()
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun triggerApiCall() {
        viewModelScope.launch {
            val userPhoneNumber = firebaseAuth.currentUser?.phoneNumber?.removeCountryCodeFromPhone()
            val loginUserTypeEnum = sharedPrefs.getLoginType()
            if (!userPhoneNumber.isNullOrEmpty() && loginUserTypeEnum != null) {
                val phoneNumberRequestModel = PhoneNumberRequestModel(
                    phone = userPhoneNumber
                )
                splashUseCase.getUserProfile(phoneNumberRequestModel).collectLatest { networkResult ->
                    when(networkResult) {
                        is NetworkResult.Loading -> {
                            splashScreenUIState.update {
                                it.copy(isApiLoading = true)
                            }
                        }
                        is NetworkResult.Error -> {
                            if (networkResult.code == 404) {
                                splashScreenUIState.update {
                                    it.copy(
                                        isApiLoading = false,
                                        userPhoneNumber = null
                                    )
                                }
                            } else {
                                splashScreenUIState.update {
                                    it.copy(
                                        errorMessage = networkResult.message
                                    )
                                }
                            }
                        }
                        is NetworkResult.Success -> {
                            val userModel = networkResult.data?.data
                            sharedPrefs.setUserModel(userModel)
                            if (userModel?.id != null) {
                                hitBusRequestByStatusAndId(userModel)
                            } else {
                                splashScreenUIState.update {
                                    it.copy(
                                        isApiLoading = false,
                                        userPhoneNumber = null
                                    )
                                }
                            }
                        }
                    }
                }
            } else {
                delay(3000L)
                splashScreenUIState.update {
                    it.copy(
                        isApiLoading = false,
                        userPhoneNumber = userPhoneNumber
                    )
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
                    splashScreenUIState.update {
                        it.copy(
                            isApiLoading = false,
                            userPhoneNumber = "${userModel.phone}"
                        )
                    }
                }
                is NetworkResult.Loading -> {
                    // Loading State...
                }
                is NetworkResult.Success -> {
                    sharedPrefs.setRequestPassModelList(networkResult.data?.data)
                    splashScreenUIState.update {
                        it.copy(
                            isApiLoading = false,
                            userPhoneNumber = "${userModel.phone}"
                        )
                    }
                }
            }
        }
    }

    fun updateErrorMessage() {
        splashScreenUIState.update {
            it.copy(
                errorMessage = null
            )
        }
    }

}