package com.android.smcetransport.app.screens.signup.utils

import android.util.Patterns

object EmailValidator {

    fun String?.isValidEmail() : Boolean {
        return !this.isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }

}