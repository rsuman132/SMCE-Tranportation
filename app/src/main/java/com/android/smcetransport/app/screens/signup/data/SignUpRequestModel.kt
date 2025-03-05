package com.android.smcetransport.app.screens.signup.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpRequestModel(
    @SerialName("name")
    val name : String?,
    @SerialName("college_id")
    val collegeId : String?,
    @SerialName("year")
    val year : String?,
    @SerialName("department_id")
    val departmentId : String?,
    @SerialName("phone")
    val phone : String?,
    @SerialName("address")
    val address : String?,
    @SerialName("image_url")
    val imageUrl : String?
)
