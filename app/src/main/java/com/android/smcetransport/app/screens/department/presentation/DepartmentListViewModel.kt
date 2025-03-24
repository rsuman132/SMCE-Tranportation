package com.android.smcetransport.app.screens.department.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.screens.department.data.DepartmentRequestModel
import com.android.smcetransport.app.screens.department.domain.DepartmentUseCase
import com.android.smcetransport.app.screens.signup.domain.SignUpUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

class DepartmentListViewModel(
    private val signUpUseCase: SignUpUseCase,
    private val departmentUseCase: DepartmentUseCase
) : ViewModel() {

    var departmentListUIState : MutableStateFlow<DepartmentListUIState> =
        MutableStateFlow(DepartmentListUIState())
        private set

    private val _errorMessage = Channel<String?>()
    val errorMessage = _errorMessage.receiveAsFlow()

    init {
        getAllDepartmentList()
    }



    @OptIn(ExperimentalSerializationApi::class)
    private fun getAllDepartmentList() {
        viewModelScope.launch {
            signUpUseCase.getAllDepartment().collectLatest { networkResult ->
                when(networkResult) {
                    is NetworkResult.Loading -> {
                        updateLoadingState(true)
                    }
                    is NetworkResult.Error -> {
                        _errorMessage.send(networkResult.message)
                        updateLoadingState(false)
                    }
                    is NetworkResult.Success -> {
                        val departmentList = networkResult.data?.data?: listOf()
                        departmentListUIState.update {
                            it.copy(
                                isLoading = false,
                                departmentList = departmentList
                            )
                        }
                    }
                }
            }
        }
    }


    fun updateDepartmentDialog(show : Boolean) {
        departmentListUIState.update {
            it.copy(isShowAddDepartmentDialog = show)
        }
    }


    fun updateDepartmentName(departmentName : String) {
        departmentListUIState.update {
            it.copy(
                departmentNameEtValue = departmentName,
                showDepartmentError = false
            )
        }
    }

    fun updateDepartmentCode(departmentCode : String) {
        departmentListUIState.update {
            it.copy(
                departmentCodeEtValue = departmentCode,
                showDepartmentCodeError = false
            )
        }
    }


    fun addDepartmentValidation() {
        val departmentName = departmentListUIState.value.departmentNameEtValue
        val departmentCode = departmentListUIState.value.departmentCodeEtValue
        val isValidDepartmentName = departmentName.isNotBlank() && departmentName.length > 2
        val isValidDepartmentCode = departmentCode.isNotBlank()
        departmentListUIState.update {
            it.copy(
                showDepartmentError = !isValidDepartmentName,
                showDepartmentCodeError = !isValidDepartmentCode
            )
        }
        if (isValidDepartmentName && isValidDepartmentCode) {
            hitAddDepartmentApi()
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun hitAddDepartmentApi() {
        viewModelScope.launch (IO) {
            val departmentRequestModel = DepartmentRequestModel(
                departmentName = departmentListUIState.value.departmentNameEtValue,
                departmentCode = departmentListUIState.value.departmentCodeEtValue
            )
            departmentUseCase.createDepartment(departmentRequestModel).collectLatest { networkResult ->
                when(networkResult) {
                    is NetworkResult.Error -> {
                        _errorMessage.send(networkResult.message)
                        updateButtonLoading(false)
                    }
                    is NetworkResult.Loading -> {
                        updateButtonLoading(true)
                    }
                    is NetworkResult.Success -> {
                        updateDepartmentAddSuccess()
                        getAllDepartmentList()
                    }
                }
            }
        }
    }


    private fun updateDepartmentAddSuccess() {
        departmentListUIState.update {
            it.copy(
                departmentCodeEtValue = "",
                departmentNameEtValue = "",
                showDepartmentError = false,
                showDepartmentCodeError = false,
                isShowAddDepartmentDialog = false,
                isAddDepartmentButtonLoading = false
            )
        }
    }


    private fun updateButtonLoading(show : Boolean) {
        departmentListUIState.update {
            it.copy(isAddDepartmentButtonLoading = show)
        }
    }


    private fun updateLoadingState(show : Boolean) {
        departmentListUIState.update {
            it.copy(isLoading = show)
        }
    }


}