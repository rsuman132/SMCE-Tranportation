package com.android.smcetransport.app.screens.view_pass.domain

import kotlinx.serialization.ExperimentalSerializationApi

class ViewPassRepositoryUseCase(
    private val viewPassRepository: ViewPassRepository
) {
    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getBusRequestById(
        passId : String?
    ) = viewPassRepository.getBusRequestById(passId)
}