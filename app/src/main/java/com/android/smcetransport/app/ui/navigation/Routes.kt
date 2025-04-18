package com.android.smcetransport.app.ui.navigation

import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
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
data class SuccessScreenRoute(
    val messageText : String
)

@Serializable
data object DepartmentScreenRoute

@Serializable
data object BusListScreenRoute

@Serializable
data object BusAddScreenRoute

@Serializable
data object ViewPassRoute

@Serializable
data object BusRequestStatusRoute

@Serializable
data class BusRequestStatusApproveRoute(
    val id : String,
    val loginUserTypeEnum: LoginUserTypeEnum
)