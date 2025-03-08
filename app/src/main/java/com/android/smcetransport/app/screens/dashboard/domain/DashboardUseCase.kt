package com.android.smcetransport.app.screens.dashboard.domain

import com.android.smcetransport.app.core.model.PhoneNumberRequestModel
import com.android.smcetransport.app.screens.dashboard.data.CancelRequestRequestModel
import com.android.smcetransport.app.screens.dashboard.data.SendRequestingRequestModel
import kotlinx.serialization.ExperimentalSerializationApi

class DashboardUseCase(
    private val dashboardRepository: DashboardRepository
) {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun logoutUser(
        phoneNumberRequestModel : PhoneNumberRequestModel
    ) = dashboardRepository.logoutUser(phoneNumberRequestModel)

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun sendNewRequestForStudentStaff(
        sendRequestingRequestModel: SendRequestingRequestModel
    ) = dashboardRepository.sendNewRequestForStudentStaff(sendRequestingRequestModel)

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun sendCancellationForStudentStaff(
        cancelRequestRequestModel: CancelRequestRequestModel
    ) = dashboardRepository.sendCancellationForStudentStaff(cancelRequestRequestModel)
}