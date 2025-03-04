package com.android.smcetransport.app.ui.components.model

import com.android.smcetransport.app.ui.components.enum.DropDownTypeEnum

data class DropDownModel(
    val dropDownId: String,
    val dropDownText: String,
    val dropDownTypeEnum: DropDownTypeEnum
)