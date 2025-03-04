package com.android.smcetransport.app.screens.signup

import android.net.Uri
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum

data class SignUpUIState(
    val loginUserTypeEnum: LoginUserTypeEnum? = null,
    val profileImageUri: Uri? = null,
    val isValidProfileImage: Boolean = true,
    val userName: String = "",
    val isValidUserName: Boolean = true,
    val userEmail: String = "",
    val isValidUserEmail: Boolean = true,
    val userCollegeId: String = "",
    val isValidUserCollegeId: Boolean = true,
    val userDepartment : String = "",
    val userDepartmentDropDownId : String = "",
    val isValidUserDepartment : Boolean = true,
    val userCollegeYear : String = "",
    val userCollegeYearDropDownId : String = "",
    val isValidUserCollegeYear : Boolean = true,
    val isDepartmentDropDownExpanded : Boolean = false,
    val isYearDropDownExpanded : Boolean = false,
    val isShowDialog : Boolean = false,
    val isButtonLoading : Boolean = false
)
