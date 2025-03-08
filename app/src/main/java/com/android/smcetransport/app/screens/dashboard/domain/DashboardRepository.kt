package com.android.smcetransport.app.screens.dashboard.domain

import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.dto.BusRequestModel
import com.android.smcetransport.app.core.model.PhoneNumberRequestModel
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.screens.dashboard.data.CancelRequestRequestModel
import com.android.smcetransport.app.screens.dashboard.data.SendRequestingRequestModel
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonObject

interface DashboardRepository {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun logoutUser(
        phoneNumberRequestModel : PhoneNumberRequestModel
    ) : Flow<NetworkResult<BaseApiClass<JsonObject>>>

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun sendNewRequestForStudentStaff(
        sendRequestingRequestModel: SendRequestingRequestModel
    ) : Flow<NetworkResult<BaseApiClass<BusRequestModel>>>

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun sendCancellationForStudentStaff(
        cancelRequestRequestModel: CancelRequestRequestModel
    ) : Flow<NetworkResult<BaseApiClass<JsonObject>>>

}