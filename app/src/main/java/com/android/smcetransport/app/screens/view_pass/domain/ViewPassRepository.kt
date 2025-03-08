package com.android.smcetransport.app.screens.view_pass.domain

import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.dto.RequestPassModel
import com.android.smcetransport.app.core.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

interface ViewPassRepository {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getBusRequestById(
        passId : String?
    ) : Flow<NetworkResult<BaseApiClass<List<RequestPassModel>>>>

}