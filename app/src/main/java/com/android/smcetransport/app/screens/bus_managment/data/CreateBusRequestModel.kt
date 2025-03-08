package com.android.smcetransport.app.screens.bus_managment.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CreateBusRequestModel(
    @SerialName("bus_number")
    val busNumber : String,
    @SerialName("registration_number")
    val registrationNumber : String,
    @SerialName("starting_point")
    val startingPoint : String,
    @SerialName("bus_route")
    val busRoute : String,
    @SerialName("driver_name")
    val driverName : String
)
