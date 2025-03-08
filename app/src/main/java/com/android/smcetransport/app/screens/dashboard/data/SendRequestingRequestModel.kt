package com.android.smcetransport.app.screens.dashboard.data

import com.android.smcetransport.app.core.enum.RequestStatusEnum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SendRequestingRequestModel(
    @SerialName("staff_id")
    val staffId : String?,
    @SerialName("student_id")
    val studentId : String?,
    @SerialName("pickup_point")
    val pickupPoint : String,
    @SerialName("status")
    val status : RequestStatusEnum
)
