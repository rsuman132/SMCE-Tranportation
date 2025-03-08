package com.android.smcetransport.app.core.utils

import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.google.firebase.auth.FirebaseAuth

class CommonLogout(
    private val sharedPrefs: SharedPrefs
) {

    private val firebaseAuth = FirebaseAuth.getInstance()

    fun logout() {
        firebaseAuth.signOut()
        sharedPrefs.setUserModel(null)
        sharedPrefs.setLoginType(null)
        sharedPrefs.setRequestPassModelList(null)
    }
}