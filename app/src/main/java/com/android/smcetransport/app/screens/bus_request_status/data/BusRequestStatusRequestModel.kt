package com.android.smcetransport.app.screens.bus_request_status.data

import com.android.smcetransport.app.core.enum.RequestStatusEnum
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BusRequestStatusRequestModel(
    @SerialName("status")
    val status : RequestStatusEnum?
)
