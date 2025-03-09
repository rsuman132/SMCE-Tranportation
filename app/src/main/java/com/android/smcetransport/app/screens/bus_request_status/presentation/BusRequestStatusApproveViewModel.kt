package com.android.smcetransport.app.screens.bus_request_status.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.core.enum.RequestStatusEnum
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.android.smcetransport.app.screens.bus_managment.domain.BusUseCase
import com.android.smcetransport.app.screens.bus_request_status.data.BusRequestApproveRequestModel
import com.android.smcetransport.app.screens.bus_request_status.domain.BusRequestStatusUseCase
import com.android.smcetransport.app.ui.components.enum.DropDownTypeEnum
import com.android.smcetransport.app.ui.components.model.DropDownModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

class BusRequestStatusApproveViewModel(
    private val busRequestStatusUseCase: BusRequestStatusUseCase,
    private val busUseCase : BusUseCase,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {

    var busRequestStatusApproveUIState : MutableStateFlow<BusRequestStatusApproveUIState> =
        MutableStateFlow(BusRequestStatusApproveUIState())
        private set

    private val _errorMessage = Channel<String?>()
    val errorMessage get() = _errorMessage.receiveAsFlow()

    private val _approveSuccessEvent = Channel<LoginUserTypeEnum?>()
    val approveSuccessEvent get() = _approveSuccessEvent.receiveAsFlow()


    init {
        viewModelScope.launch {
            _approveSuccessEvent.send(null)
        }
    }


    fun updateIdAndLoginType(
        id : String,
        loginUserTypeEnum: LoginUserTypeEnum
    ) {
        busRequestStatusApproveUIState.update {
            it.copy(id = id, loginUserTypeEnum = loginUserTypeEnum)
        }
    }


    @OptIn(ExperimentalSerializationApi::class)
    fun getAllBusList() {
        viewModelScope.launch(IO) {
            busUseCase.getAllBusList().collectLatest { networkResult ->
                when(networkResult) {
                    is NetworkResult.Error -> {
                        _errorMessage.send(networkResult.message)
                        busRequestStatusApproveUIState.update {
                            it.copy(isBusListApiLoading = false)
                        }
                    }
                    is NetworkResult.Loading -> {
                        busRequestStatusApproveUIState.update {
                            it.copy(isBusListApiLoading = true)
                        }
                    }
                    is NetworkResult.Success -> {
                        val dropDownList = networkResult.data?.data?.map {
                            DropDownModel(
                                dropDownId = it.id ?: "",
                                dropDownText = "${it.busNumber} • ${it.busRoute}",
                                dropDownTypeEnum = DropDownTypeEnum.BUS
                            )
                        } ?: listOf()
                        busRequestStatusApproveUIState.update {
                            it.copy(
                                isBusListApiLoading = true,
                                busListDropDownList = dropDownList,
                                selectedBusDropDown = true,
                                isBusRequestListLoading = false
                            )
                        }
                    }
                }
            }
        }
    }


    @OptIn(ExperimentalSerializationApi::class)
    fun getBusRequestById() {
        viewModelScope.launch(IO) {
            busRequestStatusUseCase.getBusRequestById(
                id = busRequestStatusApproveUIState.value.id,
                loginUserTypeEnum = busRequestStatusApproveUIState.value.loginUserTypeEnum
            ).collectLatest { networkResult ->
                when(networkResult) {
                    is NetworkResult.Error -> {
                        _errorMessage.send(networkResult.message)
                    }
                    is NetworkResult.Loading -> {
                        busRequestStatusApproveUIState.update {
                            it.copy(isBusRequestListLoading = true)
                        }
                    }
                    is NetworkResult.Success -> {
                        val busRequestModel = networkResult.data?.data?.firstOrNull()
                        val amount = busRequestModel?.amount ?: 0.0
                        busRequestStatusApproveUIState.update {
                            it.copy(
                                isBusRequestListLoading = false,
                                pickupPointText = busRequestModel?.pickupPoint ?: "",
                                requesterId = busRequestModel?.requesterId,
                                amountText = if (amount > 0.0) {
                                    "$amount"
                                } else {
                                    ""
                                }
                            )
                        }
                    }
                }
            }
        }
    }


    fun updateDepartment(
        selectedBusText : String?,
        selectedBusId : String?,
        isExpanded : Boolean
    ) {
        if (selectedBusText != null && selectedBusId != null) {
            busRequestStatusApproveUIState.update {
                it.copy(
                    selectedBusDropDown = isExpanded,
                    selectedBusDropDownId = selectedBusId,
                    selectedBusText = selectedBusText,
                    selectedBusShowError = false
                )
            }
        } else {
            busRequestStatusApproveUIState.update {
                it.copy(
                    selectedBusDropDown = isExpanded
                )
            }
        }
    }



    fun updateAmountText(
        amountValue : String
    ) {
        busRequestStatusApproveUIState.update {
            it.copy(amountText = amountValue, isShowAmountError = false)
        }
    }


    fun updateStartPoint(
        startPoint : String
    ) {
        busRequestStatusApproveUIState.update {
            it.copy(pickupPointText = startPoint, isShowPickupPointError = false)
        }
    }


    @OptIn(ExperimentalSerializationApi::class)
    fun validateApproveStatus() {
        val amount = busRequestStatusApproveUIState.value.amountText.toDoubleOrNull() ?: 0.0
        val pickUpPoint = busRequestStatusApproveUIState.value.pickupPointText
        val selectedBusId = busRequestStatusApproveUIState.value.selectedBusDropDownId
        val isValidAmount = amount > 0.0
        val isValidPickupPoint = pickUpPoint.isNotBlank() && pickUpPoint.length > 3
        val isValidBusId = selectedBusId.isNotBlank()
        busRequestStatusApproveUIState.update {
            it.copy(
                isShowPickupPointError = !isValidPickupPoint,
                isShowAmountError = !isValidAmount,
                selectedBusShowError = !isValidBusId
            )
        }
        val everyThingValid = if (busRequestStatusApproveUIState.value.loginUserTypeEnum == LoginUserTypeEnum.STUDENT) {
            isValidAmount && isValidPickupPoint && isValidBusId
        } else {
            isValidPickupPoint && isValidBusId
        }
        if (everyThingValid) {
            viewModelScope.launch {
                updateButtonLoading(true)
                val busInChargeId = sharedPrefs.getUserModel()?.id
                val busRequestApproveRequestModel = BusRequestApproveRequestModel(
                    pickUpPoint = pickUpPoint,
                    amount = amount,
                    status = RequestStatusEnum.ACCEPTED,
                    busInChargeId = busInChargeId,
                    busId = selectedBusId,
                    staffId = busRequestStatusApproveUIState.value.requesterId,
                    studentId = busRequestStatusApproveUIState.value.requesterId
                )
                busRequestStatusUseCase.requestApproveStatusChange(
                    busRequestApproveRequestModel = busRequestApproveRequestModel,
                    loginUserTypeEnum = busRequestStatusApproveUIState.value.loginUserTypeEnum,
                    id = busRequestStatusApproveUIState.value.id
                ).collectLatest { networkResult ->
                    when(networkResult) {
                        is NetworkResult.Error -> {
                            _errorMessage.send(networkResult.message)
                            updateButtonLoading(false)
                        }
                        is NetworkResult.Loading -> {
                            updateButtonLoading(true)
                        }
                        is NetworkResult.Success -> {
                            updateButtonLoading(false)
                            _approveSuccessEvent.send(busRequestStatusApproveUIState.value.loginUserTypeEnum)
                        }
                    }
                }
            }
        }
    }


    private fun updateButtonLoading(show : Boolean) {
        busRequestStatusApproveUIState.update {
            it.copy(isShowButtonLoading = show)
        }
    }


}