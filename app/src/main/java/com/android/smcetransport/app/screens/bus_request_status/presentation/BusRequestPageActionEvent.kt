package com.android.smcetransport.app.screens.bus_request_status.presentation

sealed class BusRequestPageActionEvent {

    data object OnBackPressEvent : BusRequestPageActionEvent()

    data class OnTabChangeEvent(
        val selectedPos : Int
    ) : BusRequestPageActionEvent()

}