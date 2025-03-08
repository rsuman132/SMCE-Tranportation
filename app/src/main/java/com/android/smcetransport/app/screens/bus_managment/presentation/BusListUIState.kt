package com.android.smcetransport.app.screens.bus_managment.presentation

import com.android.smcetransport.app.core.dto.BusAndRouteModel

data class BusListUIState(
    val busList : List<BusAndRouteModel> = listOf(),
    val isBusListGetApiLoading : Boolean = false
)
