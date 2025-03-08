package com.android.smcetransport.app.screens.bus_request_status.domain

import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.dto.BusRequestModel
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.screens.bus_request_status.data.BusRequestStatusRequestModel
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

interface BusRequestStatusRepository {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getStudentBusRequestByStatus(
        busRequestStatusRequestModel : BusRequestStatusRequestModel
    ) : Flow<NetworkResult<BaseApiClass<List<BusRequestModel>>>>


    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getStaffBusRequestByStatus(
        busRequestStatusRequestModel : BusRequestStatusRequestModel
    ) : Flow<NetworkResult<BaseApiClass<List<BusRequestModel>>>>

}