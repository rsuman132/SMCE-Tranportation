package com.android.smcetransport.app.screens.bus_managment.domain

import com.android.smcetransport.app.screens.bus_managment.data.CreateBusRequestModel
import kotlinx.serialization.ExperimentalSerializationApi

class BusUseCase(
    private val busRepository: BusRepository
) {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getAllBusList() = busRepository.getAllBusList()

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun createBus(
        createBusRequestModel : CreateBusRequestModel
    ) = busRepository.createBus(createBusRequestModel)

}