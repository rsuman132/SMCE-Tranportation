package com.android.smcetransport.app.screens.department.domain

import com.android.smcetransport.app.screens.department.data.DepartmentRequestModel
import kotlinx.serialization.ExperimentalSerializationApi

class DepartmentUseCase(
    private val departmentRepository: DepartmentRepository
) {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun createDepartment(
        departmentRequestModel : DepartmentRequestModel
    ) = departmentRepository.createDepartment(departmentRequestModel)
}