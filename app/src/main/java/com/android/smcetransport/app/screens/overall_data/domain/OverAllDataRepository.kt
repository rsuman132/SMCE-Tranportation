package com.android.smcetransport.app.screens.overall_data.domain

import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.dto.BusRequestModel
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.screens.overall_data.data.OverAllDataRequestModel
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

interface OverAllDataRepository {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getAllBusRequestByStatusAndDepartment(
        overAllDataRequestModel: OverAllDataRequestModel,
        loginUserTypeEnum: LoginUserTypeEnum?
    ) : Flow<NetworkResult<BaseApiClass<List<BusRequestModel>>>>

}