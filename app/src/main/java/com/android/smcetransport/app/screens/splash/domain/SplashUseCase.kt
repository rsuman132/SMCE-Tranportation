package com.android.smcetransport.app.screens.splash.domain

import com.android.smcetransport.app.screens.splash.data.SplashRequestModel
import kotlinx.serialization.ExperimentalSerializationApi

class SplashUseCase(
    private val splashRepository: SplashRepository
) {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getUserProfile(
        splashRequestModel: SplashRequestModel
    ) = splashRepository.getUserProfile(splashRequestModel)

}