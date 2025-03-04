package com.android.smcetransport.app.screens.splash.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashScreenViewModel : ViewModel() {

    var splashScreenUIState = MutableStateFlow(SplashScreenUIState())
        private set

    init {
        triggerApiCall()
    }

    private fun triggerApiCall() {
        viewModelScope.launch {
            delay(3000L)
            splashScreenUIState.update {
                it.copy(isApiLoading = false)
            }
        }
    }

}