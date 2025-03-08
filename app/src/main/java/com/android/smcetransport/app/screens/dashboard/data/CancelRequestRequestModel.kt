package com.android.smcetransport.app.screens.dashboard.data

import com.android.smcetransport.app.core.enum.RequestStatusEnum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CancelRequestRequestModel(
    @SerialName("staff_id")
    val staffId : String?,
    @SerialName("student_id")
    val studentId : String?,
    @SerialName("reason_for_cancellation")
    val reasonForCancellation : String,
    @SerialName("status")
    val status : RequestStatusEnum

)
