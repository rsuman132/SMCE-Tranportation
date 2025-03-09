package com.android.smcetransport.app.screens.bus_request_status.domain

import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.screens.bus_request_status.data.BusRequestApproveRequestModel
import com.android.smcetransport.app.screens.bus_request_status.data.BusRequestStatusRequestModel
import com.android.smcetransport.app.screens.dashboard.data.CancelRequestRequestModel
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

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun requestCancelStatusChange(
        cancelRequestRequestModel: CancelRequestRequestModel,
        loginUserTypeEnum: LoginUserTypeEnum,
        id : String?
    ) = busRequestStatusRepository.requestCancelStatusChange(
        cancelRequestRequestModel = cancelRequestRequestModel,
        loginUserTypeEnum = loginUserTypeEnum,
        id = id
    )

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getBusRequestById(
        id: String,
        loginUserTypeEnum: LoginUserTypeEnum
    ) = busRequestStatusRepository.getBusRequestById(
        id = id,
        loginUserTypeEnum = loginUserTypeEnum
    )

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun requestApproveStatusChange(
        busRequestApproveRequestModel: BusRequestApproveRequestModel,
        loginUserTypeEnum: LoginUserTypeEnum,
        id : String?
    ) = busRequestStatusRepository.requestApproveStatusChange(
        busRequestApproveRequestModel = busRequestApproveRequestModel,
        loginUserTypeEnum = loginUserTypeEnum,
        id = id
    )

}