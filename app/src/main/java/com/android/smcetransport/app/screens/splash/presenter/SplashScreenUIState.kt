package com.android.smcetransport.app.screens.splash.presenter

data class SplashScreenUIState(
    val isApiLoading : Boolean = true,
    val errorMessage : String? = null,
    val userPhoneNumber : String? = null
)