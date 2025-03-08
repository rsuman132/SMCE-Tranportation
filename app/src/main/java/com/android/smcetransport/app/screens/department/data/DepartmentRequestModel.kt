package com.android.smcetransport.app.screens.department.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DepartmentRequestModel(
    @SerialName("department_name")
    val departmentName : String,
    @SerialName("department_code")
    val departmentCode : String
)
