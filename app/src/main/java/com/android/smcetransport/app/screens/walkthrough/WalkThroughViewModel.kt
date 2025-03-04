package com.android.smcetransport.app.screens.walkthrough

import android.util.Log
import androidx.lifecycle.ViewModel
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.google.firebase.auth.FirebaseAuth

class WalkThroughViewModel(
    private val sharedPrefs: SharedPrefs
) : ViewModel() {


    fun logoutUser() {
        try {
            FirebaseAuth.getInstance().signOut()
        } catch (e: Exception) {
            Log.d("ERROR", e.toString())
        }
    }


    fun setLoginUserTypeEnum(loginUserTypeEnum: LoginUserTypeEnum) {
        sharedPrefs.setLoginType(loginUserTypeEnum)
    }

}