package com.android.smcetransport.app.screens.department.domain

import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.dto.DepartmentModel
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.screens.department.data.DepartmentRequestModel
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

interface DepartmentRepository {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun createDepartment(
        departmentRequestModel : DepartmentRequestModel
    ) : Flow<NetworkResult<BaseApiClass<DepartmentModel?>>>

}