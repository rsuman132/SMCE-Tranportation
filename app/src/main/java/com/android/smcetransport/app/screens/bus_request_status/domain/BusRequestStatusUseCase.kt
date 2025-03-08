package com.android.smcetransport.app.screens.bus_request_status.domain

import com.android.smcetransport.app.screens.bus_request_status.data.BusRequestStatusRequestModel
import kotlinx.serialization.ExperimentalSerializationApi

class BusRequestStatusUseCase(
    private val busRequestStatusRepository: BusRequestStatusRepository
) {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getStudentBusRequestByStatus(
        busRequestStatusRequestModel : BusRequestStatusRequestModel
    ) = busRequestStatusRepository.getStudentBusRequestByStatus(busRequestStatusRequestModel)


    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getStaffBusRequestByStatus(
        busRequestStatusRequestModel : BusRequestStatusRequestModel
    ) = busRequestStatusRepository.getStaffBusRequestByStatus(busRequestStatusRequestModel)

}