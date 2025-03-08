package com.android.smcetransport.app.screens.dashboard.domain

import com.android.smcetransport.app.core.model.PhoneNumberRequestModel
import kotlinx.serialization.ExperimentalSerializationApi

class DashboardUseCase(
    private val dashboardRepository: DashboardRepository
) {

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun logoutUser(
        phoneNumberRequestModel : PhoneNumberRequestModel
    ) = dashboardRepository.logoutUser(phoneNumberRequestModel)
}