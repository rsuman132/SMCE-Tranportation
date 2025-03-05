package com.android.smcetransport.app.core.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StatusModel(
    @SerialName("message")
    val message: String? = null,
    @SerialName("code")
    val code: Int = 0
)
