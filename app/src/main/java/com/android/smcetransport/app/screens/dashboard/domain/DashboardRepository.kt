package com.android.smcetransport.app.screens.dashboard.domain

import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.model.PhoneNumberRequestModel
import com.android.smcetransport.app.core.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonObject

interface DashboardRepository {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun logoutUser(
        phoneNumberRequestModel : PhoneNumberRequestModel
    ) : Flow<NetworkResult<BaseApiClass<JsonObject>>>

}