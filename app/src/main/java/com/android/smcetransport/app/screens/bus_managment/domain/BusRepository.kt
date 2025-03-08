package com.android.smcetransport.app.screens.bus_managment.domain

import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.dto.BusAndRouteModel
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.screens.bus_managment.data.CreateBusRequestModel
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

interface BusRepository {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getAllBusList() : Flow<NetworkResult<BaseApiClass<List<BusAndRouteModel>?>>>

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun createBus(
        createBusRequestModel : CreateBusRequestModel
    ) : Flow<NetworkResult<BaseApiClass<BusAndRouteModel?>>>

}