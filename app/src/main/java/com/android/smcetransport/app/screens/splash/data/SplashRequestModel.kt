package com.android.smcetransport.app.screens.splash.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SplashRequestModel(
    @SerialName("phone")
    val phone : String?
)
