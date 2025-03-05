package com.android.smcetransport.app.core.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TokenModel(
    @SerialName("access_token")
    val accessToken: String? = null,
    @SerialName("type")
    val type: String? = null
)