package com.android.smcetransport.app.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
data object SplashScreenRoute

@Serializable
data object WalkThroughRoute

@Serializable
data object MobileLoginRoute

@Serializable
data class OTPVerificationRoute(
    val phoneNumberWithCC : String
)

@Serializable
data object SignUpRoute

@Serializable
data object DashboardRoute


@Serializable
data object SuccessScreenRoute