package com.android.smcetransport.app.screens.signup.presentation

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.android.smcetransport.app.core.utils.StringExtensions.removeCountryCodeFromPhone
import com.android.smcetransport.app.screens.signup.data.SignUpRequestModel
import com.android.smcetransport.app.screens.signup.domain.SignUpUseCase
import com.android.smcetransport.app.ui.components.enum.DropDownTypeEnum
import com.android.smcetransport.app.ui.components.model.DropDownModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

class SignUpViewModel(
    private val sharedPrefs: SharedPrefs,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    var signUpUIState: MutableStateFlow<SignUpUIState> = MutableStateFlow(
        SignUpUIState(
            loginUserTypeEnum = loginUserTypeEnum
        )
    )
        private set

    private val loginUserTypeEnum get() = sharedPrefs.getLoginType()

    private val _errorMessage = Channel<String?>()
    val errorMessage = _errorMessage.receiveAsFlow()

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
                userAddress = userEmail,
                isValidUserAddress = true
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
            it.copy(
                profileImageUri = uri,
                isValidProfileImage = true
            )
        }
    }



    fun validation() {
        val newSignUpUIState = signUpUIState.value
        val isValidProfileImg = newSignUpUIState.profileImageUri != null
        val isValidUserName = newSignUpUIState.userName.isNotBlank()
                && newSignUpUIState.userName.length > 3
        val isValidCollegeId = newSignUpUIState.userCollegeId.isNotBlank()
        val isUserAddress = newSignUpUIState.userAddress.isNotBlank()
        val isValidDepartment = newSignUpUIState.userDepartment.isNotEmpty()
        val isValidYear = newSignUpUIState.userCollegeYear.isNotEmpty()
        signUpUIState.update {
            it.copy(
                isValidProfileImage = isValidProfileImg,
                isValidUserName = isValidUserName,
                isValidUserDepartment = isValidDepartment,
                isValidUserCollegeYear = isValidYear,
                isValidUserAddress = isUserAddress,
                isValidUserCollegeId = isValidCollegeId
            )
        }
        val isAllInputsAreValid = if (newSignUpUIState.loginUserTypeEnum == LoginUserTypeEnum.STUDENT) {
            isValidProfileImg && isValidUserName && isValidDepartment && isValidYear && isUserAddress && isValidCollegeId
        } else {
            isValidProfileImg && isValidUserName && isValidDepartment && isUserAddress && isValidCollegeId
        }
        if (!isAllInputsAreValid) {
            return
        }
        val signUpRequestModel = SignUpRequestModel(
            name = newSignUpUIState.userName,
            collegeId = newSignUpUIState.userCollegeId,
            year = newSignUpUIState.userCollegeYear,
            departmentId = newSignUpUIState.userDepartmentDropDownId,
            phone = userPhoneNumber,
            address = newSignUpUIState.userAddress,
            imageUrl = null
        )
        uploadProfileImage(signUpRequestModel, newSignUpUIState.profileImageUri)
    }


    private fun uploadProfileImage(
        signUpRequestModel: SignUpRequestModel,
        profileImageUri: Uri?
    ) {
        viewModelScope.launch (IO) {
            signUpUseCase.uploadProfileImage(profileImageUri).collectLatest { networkResult ->
                when (networkResult) {
                    is NetworkResult.Loading -> {
                        showButtonLoading(true)
                    }

                    is NetworkResult.Error -> {
                        showButtonLoading(false)
                        _errorMessage.send(networkResult.message)
                    }

                    is NetworkResult.Success -> {
                        registerUser(signUpRequestModel = signUpRequestModel.copy(
                            imageUrl = networkResult.data
                        ))
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalSerializationApi::class)
    private suspend fun registerUser(
        signUpRequestModel: SignUpRequestModel
    ) {
        signUpUseCase.registerUser(signUpRequestModel = signUpRequestModel).collectLatest { networkResult ->
            when (networkResult) {
                is NetworkResult.Loading -> {
                    showButtonLoading(true)
                }

                is NetworkResult.Error -> {
                    showButtonLoading(false)
                    _errorMessage.send(networkResult.message)
                }

                is NetworkResult.Success -> {
                    val userModel = networkResult.data?.data
                    if (userModel != null) {
                        signUpUIState.update {
                            it.copy(
                                isButtonLoading = false,
                                isApiSuccess = true
                            )
                        }
                    } else {
                        showButtonLoading(false)
                        _errorMessage.send("User Already registered")

                    }
                }
            }
        }
    }


    private fun showButtonLoading(show : Boolean) {
        signUpUIState.update {
            it.copy(isButtonLoading = show)
        }
    }

    private val userPhoneNumber get() =
        FirebaseAuth.getInstance().currentUser?.phoneNumber?.removeCountryCodeFromPhone()


    @OptIn(ExperimentalSerializationApi::class)
    fun getAllDepartment() {
        viewModelScope.launch {
            signUpUseCase.getAllDepartment().collectLatest { networkResult ->
                when (networkResult) {
                    is NetworkResult.Loading -> {
                        enableDisableDepartmentList(false)
                    }

                    is NetworkResult.Error -> {
                        enableDisableDepartmentList(true)
                        _errorMessage.send(networkResult.message)
                    }

                    is NetworkResult.Success -> {
                        val departmentDropDownList = networkResult.data?.data?.map {
                            DropDownModel(
                                dropDownId = it.id ?: "",
                                dropDownText = it.departmentName ?: "",
                                dropDownTypeEnum = DropDownTypeEnum.DEPARTMENT
                            )
                        } ?: listOf()
                        signUpUIState.update {
                            it.copy(
                                isEnableDepartmentDropDown = true,
                                departmentList = departmentDropDownList,
                                isDepartmentDropDownExpanded = true,
                                isYearDropDownExpanded = false
                            )
                        }
                    }
                }
            }
        }
    }


    private fun enableDisableDepartmentList(enable: Boolean) {
        signUpUIState.update {
            it.copy(isEnableDepartmentDropDown = enable)
        }
    }

}