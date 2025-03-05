package com.android.smcetransport.app.screens.splash.presenter

sealed class SplashActionEvent {
    data object MoveToLoginScreen : SplashActionEvent()
    data object MoveToHomeScreen : SplashActionEvent()
    data object OnErrorMessageUpdate : SplashActionEvent()
}