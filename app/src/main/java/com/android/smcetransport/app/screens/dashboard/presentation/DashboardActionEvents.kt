package com.android.smcetransport.app.screens.dashboard.presentation

import com.android.smcetransport.app.core.enum.RequestStatusEnum

sealed class DashboardActionEvents {

    data class OnLogoutDialogShowEvent(
        val show : Boolean
    ) : DashboardActionEvents()

    data object OnLogoutTriggerEvent : DashboardActionEvents()

    data object OnManageDepartmentClickEvent : DashboardActionEvents()

    data object OnManageBusClickEvent : DashboardActionEvents()

    data class OnStartingPointCancellationTextUpdateEvent(
        val staringPoint : String?,
        val cancellationReason : String?
    ) : DashboardActionEvents()

    data object OnSendRequestCardClick : DashboardActionEvents()

    data object OnSendRequestButtonClick : DashboardActionEvents()

    data object OnRequestDialogDismissEvent : DashboardActionEvents()

    data object OnCancelRequestCardClick : DashboardActionEvents()

    data object OnCancelRequestButtonClick : DashboardActionEvents()

    data object OnCancelDialogDismissEvent : DashboardActionEvents()

    data class OnInfoDialogOpenDismissEvent(
        val show : Boolean
    ) : DashboardActionEvents()

    data object OnViewPassClickEvent : DashboardActionEvents()

    data class OnBusStatusRequestClickEvent(
        val requestStatusEnum: RequestStatusEnum
    ) : DashboardActionEvents()
}
