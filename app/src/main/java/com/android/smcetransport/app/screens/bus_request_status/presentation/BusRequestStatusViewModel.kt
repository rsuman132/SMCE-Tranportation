package com.android.smcetransport.app.screens.bus_request_status.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.android.smcetransport.app.screens.bus_request_status.data.BusRequestStatusRequestModel
import com.android.smcetransport.app.screens.bus_request_status.domain.BusRequestStatusUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

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

}