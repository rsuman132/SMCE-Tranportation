package com.android.smcetransport.app.screens.department.presentation

import com.android.smcetransport.app.core.dto.DepartmentModel

data class DepartmentListUIState(
    val isLoading : Boolean = false,
    val departmentList : List<DepartmentModel> = listOf(),
    val isShowAddDepartmentDialog : Boolean = false,
    val departmentNameEtValue : String = "",
    val departmentCodeEtValue : String = "",
    val isAddDepartmentButtonLoading : Boolean = false,
    val showDepartmentError : Boolean = false,
    val showDepartmentCodeError : Boolean = false,
    val isFromOverAllData : Boolean = false,
    val showYearAndStaffDialog : Boolean = false,
    val selectedDepartmentId : String? = null
)
