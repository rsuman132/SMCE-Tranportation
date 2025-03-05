package com.android.smcetransport.app.screens.dashboard

import androidx.lifecycle.ViewModel
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class DashboardViewModel(
    private val sharedPrefs: SharedPrefs
) : ViewModel() {

    var dashboardUIState: MutableStateFlow<DashboardUIState> = MutableStateFlow(DashboardUIState())
        private set


    init {
        updateUserValue()
    }


    private fun updateUserValue() {
        val userModel = sharedPrefs.getUserModel()
        dashboardUIState.update {
            it.copy(
                userName = userModel?.name ?: "",
                userCollegeId = userModel?.collegeOrStaffId ?: "",
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

}