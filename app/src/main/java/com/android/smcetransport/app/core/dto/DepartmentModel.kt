package com.android.smcetransport.app.core.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DepartmentModel(
    @SerialName("id")
    val id : String?,
    @SerialName("department_name")
    val departmentName : String?,
    @SerialName("department_code")
    val departmentCode : String?
)
