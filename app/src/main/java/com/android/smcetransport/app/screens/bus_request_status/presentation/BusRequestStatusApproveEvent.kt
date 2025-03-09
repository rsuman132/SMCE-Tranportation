package com.android.smcetransport.app.screens.bus_request_status.presentation

sealed class BusRequestStatusApproveEvent {

    data object OnBackPressEvent : BusRequestStatusApproveEvent()

    data object OnBusDropDownListApiHitEvent : BusRequestStatusApproveEvent()

    data class OnDropDownChangeEvent(
        val busSelectedText : String?,
        val selectedBusId : String?,
        val isExpanded : Boolean
    ) : BusRequestStatusApproveEvent()


    data class OnTextChangeEvent(
        val pickUpPoint : String?,
        val amountText : String?
    ) : BusRequestStatusApproveEvent()

    data object OnApproveButtonEvent : BusRequestStatusApproveEvent()

}