package com.android.smcetransport.app.screens.bus_request_status.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smcetransport.app.core.dto.BusRequestModel
import com.android.smcetransport.app.core.dto.DepartmentModel
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.core.enum.RequestStatusEnum
import com.android.smcetransport.app.core.enum.StudentYearEnum
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.android.smcetransport.app.screens.bus_request_status.data.BusRequestStatusRequestModel
import com.android.smcetransport.app.screens.bus_request_status.domain.BusRequestStatusUseCase
import com.android.smcetransport.app.screens.dashboard.data.CancelRequestRequestModel
import com.android.smcetransport.app.screens.signup.domain.SignUpUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

class BusRequestStatusViewModel(
    private val busRequestStatusUseCase: BusRequestStatusUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val sharedPrefs: SharedPrefs
) : ViewModel() {

    var busRequestStatusUIState : MutableStateFlow<BusRequestStatusUIState> =
        MutableStateFlow(BusRequestStatusUIState())
        private set

    private val _errorMessage = Channel<String?>()
    val errorMessage get() = _errorMessage.receiveAsFlow()

    private var studentBusRequestList : List<BusRequestModel> = listOf()
    private var staffBusRequestList : List<BusRequestModel> = listOf()
    private var departmentList : List<DepartmentModel> = listOf()


    init {
        getStudentBusRequest()
        getStaffBusRequest()
        getAllDepartment()
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
                        studentBusRequestList = networkResult.data?.data ?: listOf()
                        busRequestStatusUIState.update {
                            it.copy(
                                isBusRequestStatusStudentLoading = false,
                                busRequestStatusStudentList = studentBusRequestList
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
                        staffBusRequestList = networkResult.data?.data?: listOf()
                        busRequestStatusUIState.update {
                            it.copy(
                                isBusRequestStatusStaffLoading = false,
                                busRequestStatusStaffList = staffBusRequestList
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


    fun applyFilterForStudent() {
        val studentDepartmentText = busRequestStatusUIState.value.studentSelectedDepartment
        val studentYearText = busRequestStatusUIState.value.studentSelectedYear
        val filterList = if (studentDepartmentText.isNotEmpty() && studentYearText.isNotEmpty()) {
            studentBusRequestList.filter {
                it.requesterUserModel?.departmentModel?.departmentName == studentDepartmentText
                        && it.requesterUserModel.year == studentYearText
            }
        } else if (studentDepartmentText.isNotEmpty()) {
            studentBusRequestList.filter {
                it.requesterUserModel?.departmentModel?.departmentName == studentDepartmentText
            }
        } else if (studentYearText.isNotEmpty()) {
            studentBusRequestList.filter { it.requesterUserModel?.year == studentYearText }
        } else {
            studentBusRequestList
        }
        busRequestStatusUIState.update {
            it.copy(
                busRequestStatusStudentList =  filterList
            )
        }
    }


    fun applyFilterForStaff() {
        val staffDepartmentText = busRequestStatusUIState.value.staffSelectedDepartment
        val filterList = if (staffDepartmentText.isNotEmpty()) {
            staffBusRequestList.filter {
                it.requesterUserModel?.departmentModel?.departmentName == staffDepartmentText
            }
        } else {
            staffBusRequestList
        }
        busRequestStatusUIState.update {
            it.copy(
                busRequestStatusStaffList = filterList
            )
        }
    }

    fun updateCancelOpenDialog() {
        busRequestStatusUIState.update {
            it.copy(isShowCancelDialog = false)
        }
    }


    fun updateDepartmentListDialogShow(show : Boolean) {
        busRequestStatusUIState.update {
            it.copy(isShowDepartmentListDialog = show)
        }
    }

    fun updateYearListDialogShow(show : Boolean) {
        busRequestStatusUIState.update {
            it.copy(isShowYearListDialog = show)
        }
    }


    fun updateStudentSelectedYear(yearText : String) {
        busRequestStatusUIState.update {
            it.copy(studentSelectedYear = yearText)
        }
    }


    fun updateStudentStaffDeptSelectedYear(deptText : String) {
        val selectedPos = busRequestStatusUIState.value.selectedPage
        if (selectedPos == 0) {
            busRequestStatusUIState.update {
                it.copy(studentSelectedDepartment = deptText)
            }
        } else {
            busRequestStatusUIState.update {
                it.copy(staffSelectedDepartment = deptText)
            }
        }
    }


    @OptIn(ExperimentalSerializationApi::class)
    private fun getAllDepartment() {
        viewModelScope.launch(IO) {
            signUpUseCase.getAllDepartment().collectLatest { networkResult ->
                when(networkResult) {
                    is NetworkResult.Error -> {
                        updateYearAndDept()
                    }
                    is NetworkResult.Loading -> {

                    }
                    is NetworkResult.Success -> {
                        departmentList = networkResult.data?.data?: listOf()
                        updateYearAndDept()
                    }
                }
            }
        }
    }


    private fun updateYearAndDept() {
        busRequestStatusUIState.update {
            it.copy(
                departmentList = departmentList.map { departmentModel -> departmentModel.departmentName },
                yearList = StudentYearEnum.entries.map { studentYearEnum -> studentYearEnum.name }
            )
        }
    }

}