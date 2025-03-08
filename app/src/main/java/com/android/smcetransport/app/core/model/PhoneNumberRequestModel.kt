package com.android.smcetransport.app.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhoneNumberRequestModel(
    @SerialName("phone")
    val phone : String?
)
