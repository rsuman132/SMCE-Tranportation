package com.android.smcetransport.app.screens.splash.domain

import com.android.smcetransport.app.core.model.PhoneNumberRequestModel
import kotlinx.serialization.ExperimentalSerializationApi

class SplashUseCase(
    private val splashRepository: SplashRepository
) {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getUserProfile(
        phoneNumberRequestModel: PhoneNumberRequestModel
    ) = splashRepository.getUserProfile(phoneNumberRequestModel)

}