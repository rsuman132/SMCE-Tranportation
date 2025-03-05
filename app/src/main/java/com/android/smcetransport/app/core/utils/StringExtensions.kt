package com.android.smcetransport.app.core.utils

object StringExtensions {


    fun String?.removeCountryCodeFromPhone(): String? {
        return if (this?.startsWith(MobileValidator.INDIAN_DIAL_CODE) == true) {
            this.replace(MobileValidator.INDIAN_DIAL_CODE, "")
        } else {
            this
        }
    }

}