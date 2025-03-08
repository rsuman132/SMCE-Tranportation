package com.android.smcetransport.app.screens.splash.domain

import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.dto.UserModel
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.core.model.PhoneNumberRequestModel
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

interface SplashRepository {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getUserProfile(
        phoneNumberRequestModel : PhoneNumberRequestModel
    ) : Flow<NetworkResult<BaseApiClass<UserModel>>>

}