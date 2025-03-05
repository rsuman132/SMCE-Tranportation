package com.android.smcetransport.app.core.utils

import com.google.i18n.phonenumbers.PhoneNumberUtil

object MobileValidator {

    private const val INDIAN_REGION_CODE = "IN"
    const val INDIAN_DIAL_CODE = "+91"

    fun isValidPhoneNumber(
        phoneNumber : String
    ) : Boolean {
        val phoneNumberWithCC = "$INDIAN_DIAL_CODE$phoneNumber"
        return try {
            val phoneNumberUtil = PhoneNumberUtil.getInstance()
            val parsedNumber = phoneNumberUtil.parse(phoneNumberWithCC, INDIAN_REGION_CODE)
            phoneNumberUtil.isValidNumber(parsedNumber)
        } catch (e: Exception) {
            false
        }
    }

}