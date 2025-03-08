package com.android.smcetransport.app.screens.bus_managment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.screens.bus_managment.data.CreateBusRequestModel
import com.android.smcetransport.app.screens.bus_managment.domain.BusUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

class AddBusViewModel(
    private val busUseCase: BusUseCase
) : ViewModel() {

    var addBusUIState: MutableStateFlow<AddBusUIState> = MutableStateFlow(AddBusUIState())
        private set

    private val _errorMessage = Channel<String?>()
    val errorMessage = _errorMessage.receiveAsFlow()

    private val _refreshState = Channel<Boolean?>()
    val refreshState = _refreshState.receiveAsFlow()

    init {
       viewModelScope.launch {
           _refreshState.send(false)
       }
    }

    fun updateBusNo(busNo: String) {
        addBusUIState.update {
            it.copy(busNumber = busNo, isShowBusNumberError = false)
        }
    }


    fun updateBusRegisterNo(busRegisterNo: String) {
        addBusUIState.update {
            it.copy(busRegisterNumber = busRegisterNo, isShowBusRegisterNumberError = false)
        }
    }


    fun updateBusStartingPoint(startingPoint: String) {
        addBusUIState.update {
            it.copy(busStartingPoint = startingPoint, isShowBusStaringPointError = false)
        }
    }

    fun updateBusRoute(busRoute: String) {
        addBusUIState.update {
            it.copy(busRoute = busRoute, isShowBusRouteError = false)
        }
    }


    fun updateDriverName(driverName: String) {
        addBusUIState.update {
            it.copy(busDriverName = driverName, isShowBusDriverNameError = false)
        }
    }


    fun addBusValidation() {
        val busNo = addBusUIState.value.busNumber
        val busRegisterNo = addBusUIState.value.busRegisterNumber
        val busStartingPoint = addBusUIState.value.busStartingPoint
        val busRoute = addBusUIState.value.busRoute
        val driverName = addBusUIState.value.busDriverName
        val isValidBusNo = busNo.isNotBlank()
        val isValidBusRegisterNo = busRegisterNo.isNotBlank() && busRegisterNo.length > 3
        val isValidStartingPoint = busStartingPoint.isNotBlank() && busRegisterNo.length > 3
        val isValidRoute = busRoute.isNotBlank() && busRegisterNo.length > 3
        val isValidDriverName = driverName.isNotBlank()
        addBusUIState.update {
            it.copy(
                isShowBusNumberError = !isValidBusNo,
                isShowBusRegisterNumberError = !isValidBusRegisterNo,
                isShowBusStaringPointError = !isValidStartingPoint,
                isShowBusRouteError = !isValidRoute,
                isShowBusDriverNameError = !isValidDriverName
            )
        }
        val allAreValid = isValidBusNo && isValidBusRegisterNo
                && isValidStartingPoint && isValidRoute
                && isValidDriverName
        if (allAreValid) {
            val busRequestModel = CreateBusRequestModel(
                busNumber = busNo,
                registrationNumber = busRegisterNo,
                startingPoint = busStartingPoint,
                busRoute = busRoute,
                driverName = driverName
            )
            createBusApiHit(busRequestModel)
        }
    }


    @OptIn(ExperimentalSerializationApi::class)
    private fun createBusApiHit(createBusRequestModel: CreateBusRequestModel) {
        viewModelScope.launch(IO) {
            busUseCase.createBus(createBusRequestModel).collectLatest { networkResult ->
                when(networkResult) {
                    is NetworkResult.Error -> {
                        showLoading(false)
                        _errorMessage.send(networkResult.message)
                    }
                    is NetworkResult.Loading -> {
                        showLoading(true)
                    }
                    is NetworkResult.Success -> {
                        addBusUIState.update {
                            it.copy(
                                busNumber = "",
                                busStartingPoint = "",
                                busRoute = "",
                                busRegisterNumber = "",
                                busDriverName = "",
                                isAddBusLoading = false,
                                isShowBusRegisterNumberError = false,
                                isShowBusNumberError = false,
                                isShowBusRouteError = false,
                                isShowBusDriverNameError = false,
                                isShowBusStaringPointError = false
                            )
                        }
                        _refreshState.send(true)
                    }
                }
            }
        }
    }


    private fun showLoading(showLoading : Boolean) {
        addBusUIState.update {
            it.copy(isAddBusLoading = showLoading)
        }
    }

}