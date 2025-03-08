package com.android.smcetransport.app.core.model

import com.android.smcetransport.app.core.enum.RequestStatusEnum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatusAndIdRequestModel(
    @SerialName("status")
    val status : List<RequestStatusEnum>,
    @SerialName("student_id")
    val studentId : String?,
    @SerialName("staff_id")
    val staffId : String?
)
