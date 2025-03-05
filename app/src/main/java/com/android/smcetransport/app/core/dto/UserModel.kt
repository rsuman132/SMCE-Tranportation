package com.android.smcetransport.app.core.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class UserModel(
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?,
    @JsonNames("college_id", "staff_id")
    val collegeOrStaffId: String?,
    @SerialName("department_id")
    val departmentId: String?,
    @SerialName("department")
    val departmentModel: DepartmentModel?,
    @SerialName("year")
    val year: String?,
    @SerialName("phone")
    val phone: String?,
    @SerialName("address")
    val address: String?,
    @SerialName("image_url")
    val imageUrl: String?
)
