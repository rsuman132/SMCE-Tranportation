package com.android.smcetransport.app.screens.bus_request_status.data

import com.android.smcetransport.app.core.enum.RequestStatusEnum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BusRequestApproveRequestModel(
    @SerialName("pickup_point")
    val pickUpPoint : String,
    @SerialName("amount")
    val amount : Double,
    @SerialName("status")
    val status : RequestStatusEnum,
    @SerialName("bus_incharge_id")
    val busInChargeId : String?,
    @SerialName("bus_id")
    val busId : String,
    @SerialName("student_id")
    val studentId : String?,
    @SerialName("staff_id")
    val staffId : String?
)
