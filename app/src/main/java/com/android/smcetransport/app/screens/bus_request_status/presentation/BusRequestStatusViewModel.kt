package com.android.smcetransport.app.screens.bus_request_status.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.core.enum.RequestStatusEnum
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.android.smcetransport.app.screens.bus_request_status.data.BusRequestStatusRequestModel
import com.android.smcetransport.app.screens.bus_request_status.domain.BusRequestStatusUseCase
import com.android.smcetransport.app.screens.dashboard.data.CancelRequestRequestModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.core.component.getScopeName

class BusRequestStatusViewModel(
    private val busRequestStatusUseCase: BusRequestStatusUseCase,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {

    var busRequestStatusUIState : MutableStateFlow<BusRequestStatusUIState> =
        MutableStateFlow(BusRequestStatusUIState())
        private set

    private val _errorMessage = Channel<String?>()
    val errorMessage get() = _errorMessage.receiveAsFlow()


    init {
        getStudentBusRequest()
        getStaffBusRequest()
    }


    @OptIn(ExperimentalSerializationApi::class)
    private fun getStudentBusRequest() {
        viewModelScope.launch(IO) {
            busRequestStatusUseCase.getStudentBusRequestByStatus(
                busRequestStatusRequestModel = BusRequestStatusRequestModel(
                    status = sharedPrefs.getRequestStateType()
                )
            ).collectLatest { networkResult ->
                when(networkResult) {
                    is NetworkResult.Error -> {
                        _errorMessage.send(networkResult.message)
                        busRequestStatusUIState.update {
                            it.copy(isBusRequestStatusStudentLoading = false)
                        }
                    }
                    is NetworkResult.Loading -> {
                        busRequestStatusUIState.update {
                            it.copy(isBusRequestStatusStudentLoading = true)
                        }
                    }
                    is NetworkResult.Success -> {
                        busRequestStatusUIState.update {
                            it.copy(
                                isBusRequestStatusStudentLoading = false,
                                busRequestStatusStudentList = networkResult.data?.data ?: listOf()
                            )
                        }
                    }
                }
            }
        }
    }



    @OptIn(ExperimentalSerializationApi::class)
    private fun getStaffBusRequest() {
        viewModelScope.launch(IO) {
            busRequestStatusUseCase.getStaffBusRequestByStatus(
                busRequestStatusRequestModel = BusRequestStatusRequestModel(
                    status = sharedPrefs.getRequestStateType()
                )
            ).collectLatest { networkResult ->
                when(networkResult) {
                    is NetworkResult.Error -> {
                        _errorMessage.send(networkResult.message)
                        busRequestStatusUIState.update {
                            it.copy(isBusRequestStatusStaffLoading = false)
                        }
                    }
                    is NetworkResult.Loading -> {
                        busRequestStatusUIState.update {
                            it.copy(isBusRequestStatusStaffLoading = true)
                        }
                    }
                    is NetworkResult.Success -> {
                        busRequestStatusUIState.update {
                            it.copy(
                                isBusRequestStatusStaffLoading = false,
                                busRequestStatusStaffList = networkResult.data?.data?: listOf()
                            )
                        }
                    }
                }
            }
        }
    }


    fun updateSelectedPos(selectedPos : Int) {
        busRequestStatusUIState.update {
            it.copy(selectedPage = selectedPos)
        }
    }


    fun updateCancelReasonText(
        reasonText : String
    ) {
        busRequestStatusUIState.update {
            it.copy(
                reasonForCancellation = reasonText,
                isShowReasonForCancellationError = false
            )
        }
    }


    @OptIn(ExperimentalSerializationApi::class)
    fun validateCancelRequest() {
        val cancellationReason = busRequestStatusUIState.value.reasonForCancellation
        val isValidCancellationReason = cancellationReason.isNotBlank() && cancellationReason.length > 3
        busRequestStatusUIState.update {
            it.copy(isShowReasonForCancellationError = !isValidCancellationReason)
        }
        if (isValidCancellationReason) {
            val cancelRequestRequestModel = CancelRequestRequestModel(
                staffId = busRequestStatusUIState.value.selectedRequesterId,
                studentId = busRequestStatusUIState.value.selectedRequesterId,
                reasonForCancellation = cancellationReason,
                status = RequestStatusEnum.CANCELLED,
                busInChargeId = sharedPrefs.getUserModel()?.id
            )
            viewModelScope.launch (IO){
                busRequestStatusUseCase.requestCancelStatusChange(
                    cancelRequestRequestModel = cancelRequestRequestModel,
                    loginUserTypeEnum = if (busRequestStatusUIState.value.selectedPage == 0)
                        LoginUserTypeEnum.STUDENT else LoginUserTypeEnum.STAFF,
                    id = busRequestStatusUIState.value.selectedRequestId
                ).collectLatest { networkResult ->
                    when(networkResult) {
                        is NetworkResult.Error -> {
                            _errorMessage.send(networkResult.message)
                            busRequestStatusUIState.update {
                                it.copy(isCancelRequestButtonLoading = false)
                            }
                        }
                        is NetworkResult.Loading -> {
                            busRequestStatusUIState.update {
                                it.copy(isCancelRequestButtonLoading = true)
                            }
                        }
                        is NetworkResult.Success -> {
                            busRequestStatusUIState.update {
                                it.copy(
                                    isCancelRequestButtonLoading = false,
                                    isShowCancelDialog = false,
                                    selectedRequestId = null,
                                    isShowReasonForCancellationError = false,
                                    reasonForCancellation = ""
                                )
                            }
                            hitApiProcess()
                        }
                    }
                }
            }
        }
    }


    fun hitApiProcess() {
        if (busRequestStatusUIState.value.selectedPage == 0) {
            getStudentBusRequest()
        } else {
            getStaffBusRequest()
        }
    }


    fun updateCancelIdAndOpenDialog(id : String?, requesterId : String?) {
        busRequestStatusUIState.update {
            it.copy(
                selectedRequestId = id,
                isShowCancelDialog = true,
                selectedRequesterId = requesterId
            )
        }
    }



    fun updateCancelOpenDialog() {
        busRequestStatusUIState.update {
            it.copy(isShowCancelDialog = false)
        }
    }

}