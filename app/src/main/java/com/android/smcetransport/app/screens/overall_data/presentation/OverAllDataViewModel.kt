package com.android.smcetransport.app.screens.overall_data.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.core.enum.RequestStatusEnum
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.screens.overall_data.data.OverAllDataRequestModel
import com.android.smcetransport.app.screens.overall_data.domain.OverAllDataUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

class OverAllDataViewModel(
    private val overAllDataUseCase: OverAllDataUseCase
) : ViewModel() {


    var overAllDataUIModel : MutableStateFlow<OverAllDataUIModel> = MutableStateFlow(OverAllDataUIModel())
        private set

    private val _errorMessage = Channel<String?>()
    val errorMessage = _errorMessage.receiveAsFlow()


    fun updateInitialValues(
        departmentId : String,
        loginUserTypeEnum: LoginUserTypeEnum,
        departmentText : String,
        yearText : String
    ) {
        overAllDataUIModel.update {
            it.copy(
                loginUserTypeEnum = loginUserTypeEnum,
                departmentText = departmentText,
                departmentId = departmentId,
                yearText = yearText
            )
        }
        getOverAppDataList()
    }


    @OptIn(ExperimentalSerializationApi::class)
    private fun getOverAppDataList() {
        viewModelScope.launch (IO) {
            val overAllDataRequestModel =  OverAllDataRequestModel(
                status = RequestStatusEnum.ACCEPTED,
                departmentId = overAllDataUIModel.value.departmentId
            )
            val loginUserTypeEnum = overAllDataUIModel.value.loginUserTypeEnum
            overAllDataUseCase.getAllBusRequestByStatusAndDepartment(
                overAllDataRequestModel = overAllDataRequestModel,
                loginUserTypeEnum = loginUserTypeEnum
            ).collectLatest { networkResult ->
                when(networkResult) {
                    is NetworkResult.Error -> {
                        _errorMessage.send(networkResult.message)
                        overAllDataUIModel.update {
                            it.copy(isApiLoading = false)
                        }
                    }
                    is NetworkResult.Loading -> {
                        overAllDataUIModel.update {
                            it.copy(isApiLoading = true)
                        }
                    }
                    is NetworkResult.Success -> {
                        val yearFilterList = if (loginUserTypeEnum == LoginUserTypeEnum.STUDENT) {
                            networkResult.data?.data?.filter {
                                it.requesterUserModel?.year == overAllDataUIModel.value.yearText
                            }
                        } else {
                            networkResult.data?.data
                        }
                        overAllDataUIModel.update {
                            it.copy(
                                isApiLoading = false,
                                busRequestModelList = yearFilterList?: listOf()
                            )
                        }
                    }
                }
            }
        }
    }

}