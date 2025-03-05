package com.android.smcetransport.app.core.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@ExperimentalSerializationApi
@Serializable
data class BaseApiClass<T : Any?>(
    @SerialName("code")
    val code: Int = 0,
    @SerialName("status")
    val status: StatusModel = StatusModel(),
    @JsonNames("student", "staff", "departments")
    val data: T? = null,
    @SerialName("message")
    val message: String? = null,
    @SerialName("token")
    val token: TokenModel? = null,
)
