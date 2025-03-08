package com.android.smcetransport.app.screens.bus_managment.presentation

data class AddBusUIState(
    val busNumber : String = "",
    val isShowBusNumberError : Boolean = false,
    val busRegisterNumber : String = "",
    val isShowBusRegisterNumberError : Boolean = false,
    val busStartingPoint : String = "",
    val isShowBusStaringPointError : Boolean = false,
    val busRoute : String = "",
    val isShowBusRouteError : Boolean = false,
    val busDriverName : String = "",
    val isShowBusDriverNameError : Boolean = false,
    val isAddBusLoading : Boolean = false
)
