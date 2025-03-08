package com.android.smcetransport.app.screens.dashboard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val sharedPrefs: SharedPrefs
) : ViewModel() {

    var dashboardUIState: MutableStateFlow<DashboardUIState> = MutableStateFlow(DashboardUIState())
        private set

    private val firebaseAuth = FirebaseAuth.getInstance()

    private val _errorMessage = Channel<String?>()
    val errorMessage = _errorMessage.receiveAsFlow()

    private val _logoutSuccessEvent = Channel<Unit>()
    val logoutSuccessEvent = _logoutSuccessEvent.receiveAsFlow()


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
            firebaseAuth.signOut()
            sharedPrefs.setUserModel(null)
            sharedPrefs.setLoginType(null)
            _logoutSuccessEvent.send(Unit)
        }
    }

}


/*  val phoneNumberRequestModel = PhoneNumberRequestModel(
                phone = firebaseAuth.currentUser?.phoneNumber?.removeCountryCodeFromPhone()
            )
            dashboardUseCase.logoutUser(phoneNumberRequestModel).collectLatest { networkResult ->
                when(networkResult) {
                    is NetworkResult.Loading -> {
                        updateDialogShowStatus(false)
                    }
                    is NetworkResult.Error -> {
                        _errorMessage.send(networkResult.message)
                    }
                    is NetworkResult.Success -> {
                        firebaseAuth.signOut()
                        sharedPrefs.setUserModel(null)
                        sharedPrefs.setLoginType(null)
                        _logoutSuccessEvent.send(Unit)
                    }
                }
            }  */