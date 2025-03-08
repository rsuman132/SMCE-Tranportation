package com.android.smcetransport.app.screens.bus_managment.presentation

sealed class AddBusActionEvent {

    data object OnBackPressEvent : AddBusActionEvent()

    data object OnAddBusBtnEvent : AddBusActionEvent()

    data class OnBusTextFieldUpdateEvent(
        val busNo : String?,
        val busRegisterNo : String?,
        val busStartingPoint : String?,
        val busRoute : String?,
        val busDriverName : String?
    ) : AddBusActionEvent()

}