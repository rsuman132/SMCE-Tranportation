package com.android.smcetransport.app.screens.bus_managment.presentation

sealed class BusListActionEvent {
    data object OnBackPressEvent : BusListActionEvent()
    data object OnAddBusEvent : BusListActionEvent()
}