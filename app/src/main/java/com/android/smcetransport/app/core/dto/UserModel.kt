package com.android.smcetransport.app.core.dto

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonNames

@OptIn(ExperimentalSerializationApi::class)
@Serializable
data class UserModel(
    @SerialName("id")
    val id: String? = null,
    @SerialName("name")
    val name: String? = null,
    @JsonNames("college_id", "staff_id")
    val collegeOrStaffId: String? = null,
    @SerialName("department_id")
    val departmentId: String? = null,
    @SerialName("department")
    val departmentModel: DepartmentModel? = null,
    @SerialName("year")
    val year: String?= null,
    @SerialName("phone")
    val phone: String? = null,
    @SerialName("address")
    val address: String? = null,
    @SerialName("image_url")
    val imageUrl: String? = null
)
