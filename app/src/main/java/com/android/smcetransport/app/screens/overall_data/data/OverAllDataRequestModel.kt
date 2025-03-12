package com.android.smcetransport.app.screens.overall_data.data

import com.android.smcetransport.app.core.enum.RequestStatusEnum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OverAllDataRequestModel(
    @SerialName("status")
    val status : RequestStatusEnum,
    @SerialName("department_id")
    val departmentId : String
)
