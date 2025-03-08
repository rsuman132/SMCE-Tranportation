package com.android.smcetransport.app.core.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class BusRequestModel(
    @SerialName("id")
    val id : String? = null,
    @JsonNames("student_id", "staff_id")
    val requesterId : String? = null,
    @JsonNames("student", "staff")
    val requesterUserModel : UserModel? = null,
    @SerialName("pickup_point")
    val pickupPoint : String? = null,
    @SerialName("amount")
    val amount : Double? = null,
    @SerialName("status")
    val status : String? = null,
    @SerialName("bus_incharge_id")
    val busInchargeId : String? = null,
    @SerialName("busincharge")
    val busInChargeUserModel: UserModel? = null,
    @SerialName("bus_id")
    val busId : String? = null,
    @SerialName("bus")
    val busAndRouteModel: BusAndRouteModel? = null,
    @SerialName("reason_for_cancellation")
    val reasonForCancellation : String? = null,
    @SerialName("timestamp")
    val timestamp : String? = null
)
