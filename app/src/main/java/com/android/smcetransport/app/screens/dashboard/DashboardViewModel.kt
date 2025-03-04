package com.android.smcetransport.app.screens.dashboard

import androidx.lifecycle.ViewModel
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs

class DashboardViewModel(
    private val sharedPrefs: SharedPrefs
) : ViewModel() {


    val getLoginTypeEnum get() = sharedPrefs.getLoginType()

}