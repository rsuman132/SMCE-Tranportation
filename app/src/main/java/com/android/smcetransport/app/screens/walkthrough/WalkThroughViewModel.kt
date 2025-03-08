package com.android.smcetransport.app.screens.walkthrough

import androidx.lifecycle.ViewModel
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.android.smcetransport.app.core.utils.CommonLogout

class WalkThroughViewModel(
    private val sharedPrefs: SharedPrefs,
    private val commonLogout: CommonLogout
) : ViewModel() {


    fun logoutUser() {
        commonLogout.logout()
    }


    fun setLoginUserTypeEnum(loginUserTypeEnum: LoginUserTypeEnum) {
        sharedPrefs.setLoginType(loginUserTypeEnum)
    }

}