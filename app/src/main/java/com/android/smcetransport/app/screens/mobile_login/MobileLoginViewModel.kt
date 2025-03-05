package com.android.smcetransport.app.screens.mobile_login

import androidx.lifecycle.ViewModel
import com.android.smcetransport.app.core.utils.MobileValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MobileLoginViewModel : ViewModel() {

    var mobileLoginUIState = MutableStateFlow(MobileLoginUIState())
        private set

    fun onPhoneNumberChangeEvent(changedText : String) {
        mobileLoginUIState.update {
            it.copy(
                phoneNumber = changedText,
                isNotValidPhoneNumber = false
            )
        }
    }


    fun validatePhoneNumber(onSuccess : (String) -> Unit) {
        val phoneNumber = mobileLoginUIState.value.phoneNumber
        val isValidPhoneNumber = MobileValidator.isValidPhoneNumber(phoneNumber)
        mobileLoginUIState.update {
            it.copy(isNotValidPhoneNumber = !isValidPhoneNumber)
        }
        if (isValidPhoneNumber) {
            onSuccess("${MobileValidator.INDIAN_DIAL_CODE}$phoneNumber")
        }
    }

}