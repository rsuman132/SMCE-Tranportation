package com.android.smcetransport.app.screens.signup

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.android.smcetransport.app.screens.signup.utils.EmailValidator.isValidEmail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel(
    private val sharedPrefs: SharedPrefs
) : ViewModel() {

    var signUpUIState: MutableStateFlow<SignUpUIState> = MutableStateFlow(
        SignUpUIState(
            loginUserTypeEnum = loginUserTypeEnum
        )
    )
        private set

    private val loginUserTypeEnum get() = sharedPrefs.getLoginType()

    fun updateUserName(userName : String) {
        signUpUIState.update {
            it.copy(
                userName = userName,
                isValidUserName = true
            )
        }
    }


    fun updateUserEmailId(userEmail : String) {
        signUpUIState.update {
            it.copy(
                userEmail = userEmail,
                isValidUserEmail = true
            )
        }
    }

    fun updateUserCollegeId(userCollegeId : String) {
        signUpUIState.update {
            it.copy(
                userCollegeId = userCollegeId,
                isValidUserCollegeId = true
            )
        }
    }

    fun updateDepartment(
        departmentText : String?,
        departmentId : String?,
        isExpanded : Boolean
    ) {
        if (departmentText != null && departmentId != null) {
            signUpUIState.update {
                it.copy(
                    isDepartmentDropDownExpanded = isExpanded,
                    isYearDropDownExpanded = false,
                    userDepartmentDropDownId = departmentId,
                    userDepartment = departmentText,
                    isValidUserDepartment = true
                )
            }
        } else {
            signUpUIState.update {
                it.copy(
                    isDepartmentDropDownExpanded = isExpanded,
                    isYearDropDownExpanded = false
                )
            }
        }
    }


    fun updateCollegeYear(
        collegeYearText : String?,
        collegeYearId : String?,
        isExpanded: Boolean
    ) {
        if (collegeYearText != null && collegeYearId != null) {
            signUpUIState.update {
                it.copy(
                    isDepartmentDropDownExpanded = false,
                    isYearDropDownExpanded = isExpanded,
                    userCollegeYearDropDownId = collegeYearId,
                    userCollegeYear = collegeYearText,
                    isValidUserCollegeYear = true
                )
            }
        } else {
            signUpUIState.update {
                it.copy(
                    isDepartmentDropDownExpanded = false,
                    isYearDropDownExpanded = isExpanded
                )
            }
        }
    }


    fun updateShowHideDialog(show : Boolean) {
        signUpUIState.update {
            it.copy(isShowDialog = show)
        }
    }


    fun updateProfileUri(uri : Uri?) {
        signUpUIState.update {
            it.copy(profileImageUri = uri)
        }
    }



    fun validation() {
        val newSignUpUIState = signUpUIState.value
        val isValidProfileImg = newSignUpUIState.profileImageUri != null
        val isValidUserName = newSignUpUIState.userName.isNotBlank()
                && newSignUpUIState.userName.length > 3
        val isValidCollegeId = newSignUpUIState.userCollegeId.isNotBlank()
        val isValidEmail = newSignUpUIState.userEmail.isValidEmail()
        val isValidDepartment = newSignUpUIState.userDepartment.isNotEmpty()
        val isValidYear = newSignUpUIState.userCollegeYear.isNotEmpty()
        signUpUIState.update {
            it.copy(
                isValidProfileImage = isValidProfileImg,
                isValidUserName = isValidUserName,
                isValidUserDepartment = isValidDepartment,
                isValidUserCollegeYear = isValidYear,
                isValidUserEmail = isValidEmail,
                isValidUserCollegeId = isValidCollegeId
            )
        }
        val isAllInputsAraValid = if (newSignUpUIState.loginUserTypeEnum == LoginUserTypeEnum.STUDENT) {
            isValidProfileImg && isValidUserName && isValidDepartment && isValidYear && isValidEmail && isValidCollegeId
        } else {
            isValidProfileImg && isValidUserName && isValidDepartment && isValidEmail && isValidCollegeId
        }
        if (!isAllInputsAraValid) {
            return
        }
        // Trigger api call
    }


    fun showButtonLoading(show : Boolean) {
        signUpUIState.update {
            it.copy(isButtonLoading = show)
        }
    }

}