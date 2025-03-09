package com.android.smcetransport.app.screens.bus_request_status.domain

import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.dto.BusRequestModel
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.screens.bus_request_status.data.BusRequestApproveRequestModel
import com.android.smcetransport.app.screens.bus_request_status.data.BusRequestStatusRequestModel
import com.android.smcetransport.app.screens.dashboard.data.CancelRequestRequestModel
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonObject

interface BusRequestStatusRepository {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getStudentBusRequestByStatus(
        busRequestStatusRequestModel : BusRequestStatusRequestModel
    ) : Flow<NetworkResult<BaseApiClass<List<BusRequestModel>>>>


    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getStaffBusRequestByStatus(
        busRequestStatusRequestModel : BusRequestStatusRequestModel
    ) : Flow<NetworkResult<BaseApiClass<List<BusRequestModel>>>>

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun requestCancelStatusChange(
        cancelRequestRequestModel: CancelRequestRequestModel,
        loginUserTypeEnum: LoginUserTypeEnum,
        id : String?
    ) : Flow<NetworkResult<BaseApiClass<JsonObject>>>

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getBusRequestById(
        id: String,
        loginUserTypeEnum: LoginUserTypeEnum
    ) : Flow<NetworkResult<BaseApiClass<List<BusRequestModel>>>>

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun requestApproveStatusChange(
        busRequestApproveRequestModel: BusRequestApproveRequestModel,
        loginUserTypeEnum: LoginUserTypeEnum,
        id : String?
    ) : Flow<NetworkResult<BaseApiClass<JsonObject>>>

}