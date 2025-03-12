package com.android.smcetransport.app.screens.overall_data.presentation

import com.android.smcetransport.app.core.dto.BusRequestModel
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum

data class OverAllDataUIModel(
    val toolbarTitle : String = "",
    val busRequestModelList : List<BusRequestModel> = listOf(),
    val loginUserTypeEnum: LoginUserTypeEnum? = null,
    val departmentText : String = "",
    val yearText : String = "",
    val isApiLoading : Boolean = false
)
