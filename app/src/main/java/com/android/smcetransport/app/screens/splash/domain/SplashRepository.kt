package com.android.smcetransport.app.screens.splash.domain

import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.dto.BusRequestModel
import com.android.smcetransport.app.core.dto.UserModel
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.core.model.PhoneNumberRequestModel
import com.android.smcetransport.app.core.model.StatusAndIdRequestModel
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

interface SplashRepository {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getUserProfile(
        phoneNumberRequestModel : PhoneNumberRequestModel
    ) : Flow<NetworkResult<BaseApiClass<UserModel>>>

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun busRequestsByStatusAndRequesterId(
        statusAndIdRequestModel : StatusAndIdRequestModel
    ) : Flow<NetworkResult<BaseApiClass<List<BusRequestModel>?>>>

}