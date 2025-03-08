package com.android.smcetransport.app.core.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BusAndRouteModel(
    @SerialName("id")
    val id: String? = null,
    @SerialName("bus_number")
    val busNumber : String? = null,
    @SerialName("registration_number")
    val registrationNumber : String? = null,
    @SerialName("starting_point")
    val startingPoint : String? = null,
    @SerialName("bus_route")
    val busRoute : String? = null,
    @SerialName("driver_name")
    val driverName : String? = null
)
