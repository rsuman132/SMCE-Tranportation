package com.android.smcetransport.app.screens.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.core.enum.RequestStatusEnum
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.android.smcetransport.app.core.utils.CommonLogout
import com.android.smcetransport.app.screens.dashboard.data.CancelRequestRequestModel
import com.android.smcetransport.app.screens.dashboard.data.SendRequestingRequestModel
import com.android.smcetransport.app.screens.dashboard.domain.DashboardUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

class DashboardViewModel(
    private val sharedPrefs: SharedPrefs,
    private val dashboardUseCase: DashboardUseCase,
    private val commonLogout: CommonLogout
) : ViewModel() {

    var dashboardUIState: MutableStateFlow<DashboardUIState> = MutableStateFlow(DashboardUIState())
        private set

    private val _errorMessage = Channel<String?>()
    val errorMessage = _errorMessage.receiveAsFlow()

    private val _logoutSuccessEvent = Channel<Unit>()
    val logoutSuccessEvent = _logoutSuccessEvent.receiveAsFlow()

    private val _requestingSuccessEvent = Channel<Unit>()
    val requestingSuccessEvent = _requestingSuccessEvent.receiveAsFlow()

    private val _cancellationSuccessEvent = Channel<Unit>()
    val cancellationSuccessEvent = _cancellationSuccessEvent.receiveAsFlow()


    init {
        updateUserValue()
    }


    private fun updateUserValue() {
        val userModel = sharedPrefs.getUserModel()
        dashboardUIState.update {
            it.copy(
                userName = userModel?.name ?: "",
                userCollegeId = userModel?.collegeOrStaffId ?: sharedPrefs.getLoginType()?.name
                ?: "",
                userAvatar = userModel?.imageUrl ?: ""
            )
        }
    }

    val getLoginTypeEnum get() = sharedPrefs.getLoginType()

    fun updateDialogShowStatus(show : Boolean) {
        dashboardUIState.update {
            it.copy(isShowLogoutDialog = show)
        }
    }


    fun logoutUser() {
        viewModelScope.launch(IO) {
            commonLogout.logout()
            _logoutSuccessEvent.send(Unit)
        }
    }


    fun updateStartingPointText(startingPoint : String) {
        dashboardUIState.update {
            it.copy(
                startingPointText = startingPoint,
                showStartingPointError = false
            )
        }
    }


    @OptIn(ExperimentalSerializationApi::class)
    fun sendRequestValidation() {
        val startingPoint = dashboardUIState.value.startingPointText
        val isValidStartingPoint = startingPoint.isNotBlank() && startingPoint.length > 3
        dashboardUIState.update {
            it.copy(showStartingPointError = !isValidStartingPoint)
        }
        if (isValidStartingPoint) {
            showRequestingBtnLoading(true)
            val loginUserTypeEnum = sharedPrefs.getLoginType()
            val userModel = sharedPrefs.getUserModel()
            val sendRequestingRequestModel = SendRequestingRequestModel(
                staffId = if (loginUserTypeEnum == LoginUserTypeEnum.STAFF) userModel?.id else null,
                studentId = if (loginUserTypeEnum == LoginUserTypeEnum.STUDENT) userModel?.id else null,
                pickupPoint = startingPoint,
                status = RequestStatusEnum.REQUESTED
            )
            viewModelScope.launch(IO) {
                dashboardUseCase.sendNewRequestForStudentStaff(sendRequestingRequestModel).collectLatest { networkResult ->
                    when(networkResult) {
                        is NetworkResult.Error -> {
                            showRequestingBtnLoading(false)
                            _errorMessage.send(networkResult.message)
                        }
                        is NetworkResult.Loading -> {
                            showRequestingBtnLoading(true)
                        }
                        is NetworkResult.Success -> {
                            val requestingRequestModel = networkResult.data?.data
                            if (requestingRequestModel != null) {
                                sharedPrefs.setRequestPassModelList(listOf(requestingRequestModel))
                            }
                            dashboardUIState.update {
                                it.copy(
                                    isRequestingButtonLoading = false,
                                    showStartingPointError = false,
                                    isShowRequestPassDialog = false,
                                    startingPointText = ""
                                )
                            }
                            _requestingSuccessEvent.send(Unit)
                        }
                    }
                }
            }
        }
    }


    fun updateSendRequestDialog(show : Boolean) {
        dashboardUIState.update {
            it.copy(isShowRequestPassDialog = show)
        }
    }


    private fun showRequestingBtnLoading(show : Boolean) {
        dashboardUIState.update {
            it.copy(isRequestingButtonLoading = show)
        }
    }


    fun updateCancellationReason(
        cancellationReason : String
    ) {
        dashboardUIState.update {
            it.copy(
                reasonForCancellation = cancellationReason,
                showReasonForCancellationError = false
            )
        }
    }


    fun updateCancelRequestDialog(show : Boolean) {
        dashboardUIState.update {
            it.copy(isShowCancelPassDialog = show)
        }
    }


    @OptIn(ExperimentalSerializationApi::class)
    fun cancelRequestValidation() {
        val cancellationReason = dashboardUIState.value.reasonForCancellation
        val isValidCancellation = cancellationReason.isNotBlank() && cancellationReason.length > 3
        dashboardUIState.update {
            it.copy(showReasonForCancellationError = !isValidCancellation)
        }
        if (isValidCancellation) {
            showCancelRequestButtonLoading(true)
            val loginUserTypeEnum = sharedPrefs.getLoginType()
            val userModel = sharedPrefs.getUserModel()
            val cancelRequestRequestModel = CancelRequestRequestModel(
                reasonForCancellation = cancellationReason,
                status = RequestStatusEnum.CANCELLED,
                staffId = if (loginUserTypeEnum == LoginUserTypeEnum.STAFF) userModel?.id else null,
                studentId = if (loginUserTypeEnum == LoginUserTypeEnum.STUDENT) userModel?.id else null,
            )
            viewModelScope.launch (IO) {
                dashboardUseCase.sendCancellationForStudentStaff(
                    cancelRequestRequestModel = cancelRequestRequestModel
                ).collectLatest { networkResult ->
                    when(networkResult) {
                        is NetworkResult.Error -> {
                            _errorMessage.send(networkResult.message)
                            showCancelRequestButtonLoading(false)
                        }
                        is NetworkResult.Loading -> {
                            showCancelRequestButtonLoading(true)
                        }
                        is NetworkResult.Success -> {
                            sharedPrefs.setRequestPassModelList(null)
                            dashboardUIState.update {
                                it.copy(
                                    isCancelButtonLoading = false,
                                    showReasonForCancellationError = false,
                                    isShowCancelPassDialog = false,
                                    reasonForCancellation = ""
                                )
                            }
                            _cancellationSuccessEvent.send(Unit)
                        }
                    }
                }
            }
        }

    }


    private fun showCancelRequestButtonLoading(show: Boolean) {
        dashboardUIState.update {
            it.copy(isCancelButtonLoading = show)
        }
    }


    fun updateInfoDialogStatus(
        title : String?,
        show : Boolean
    ) {
        if (title != null) {
            dashboardUIState.update {
                it.copy(infoDialogTitleText = title, showInfoDialog = show)
            }
        } else {
            dashboardUIState.update {
                it.copy(showInfoDialog = show)
            }
        }
    }


    val requestingRequestModel get() =
        sharedPrefs.getRequestPassModelList()?.find {
            (it.status == RequestStatusEnum.REQUESTED.name || it.status == RequestStatusEnum.ACCEPTED.name)
        }


    fun setBusRequestStatus(
        requestStatusEnum: RequestStatusEnum
    ) {
        sharedPrefs.setRequestStateType(requestStatusEnum)
    }

}