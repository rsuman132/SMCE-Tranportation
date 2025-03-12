package com.android.smcetransport.app.screens.overall_data.domain

import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.screens.overall_data.data.OverAllDataRequestModel
import kotlinx.serialization.ExperimentalSerializationApi

class OverAllDataUseCase(
    private val overAllDataRepository: OverAllDataRepository
) {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getAllBusRequestByStatusAndDepartment(
        overAllDataRequestModel: OverAllDataRequestModel,
        loginUserTypeEnum: LoginUserTypeEnum?
    ) = overAllDataRepository.getAllBusRequestByStatusAndDepartment(
        overAllDataRequestModel = overAllDataRequestModel,
        loginUserTypeEnum = loginUserTypeEnum
    )

}