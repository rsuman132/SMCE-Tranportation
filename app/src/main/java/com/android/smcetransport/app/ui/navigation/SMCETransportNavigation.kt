package com.android.smcetransport.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.android.smcetransport.app.screens.dashboard.DashboardScreen
import com.android.smcetransport.app.screens.dashboard.DashboardViewModel
import com.android.smcetransport.app.screens.mobile_login.MobileLoginActionEvent
import com.android.smcetransport.app.screens.mobile_login.MobileLoginScreen
import com.android.smcetransport.app.screens.mobile_login.MobileLoginViewModel
import com.android.smcetransport.app.screens.otp_verification.presentation.OTPVerificationActionEvent
import com.android.smcetransport.app.screens.otp_verification.presentation.OTPVerificationScreen
import com.android.smcetransport.app.screens.otp_verification.presentation.OTPVerificationViewModel
import com.android.smcetransport.app.screens.request_success.RequestSuccessScreen
import com.android.smcetransport.app.screens.signup.SignUpScreen
import com.android.smcetransport.app.screens.signup.SignUpScreenActionEvent
import com.android.smcetransport.app.screens.signup.SignUpViewModel
import com.android.smcetransport.app.screens.splash.presenter.SplashActionEvent
import com.android.smcetransport.app.screens.splash.presenter.SplashScreen
import com.android.smcetransport.app.screens.splash.presenter.SplashScreenViewModel
import com.android.smcetransport.app.screens.walkthrough.WalkThroughActionEvent
import com.android.smcetransport.app.screens.walkthrough.WalkThroughScreen
import com.android.smcetransport.app.screens.walkthrough.WalkThroughViewModel
import com.android.smcetransport.app.utils.ContextExtension.showToast
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@Composable
fun SMCETransportApp(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SplashScreenRoute
    ) {
        // Splash Screen
        composable<SplashScreenRoute> {
            val splashScreenViewModel : SplashScreenViewModel = koinViewModel()
            val splashScreenUIState by splashScreenViewModel.splashScreenUIState.collectAsState()
            SplashScreen(
                modifier = modifier,
                splashScreenUIState = splashScreenUIState,
                onSplashScreenEvent = {
                    if (it is SplashActionEvent.MoveToLoginScreen) {
                        navController.navigate(WalkThroughRoute) {
                            popUpTo(SplashScreenRoute) {
                                inclusive = true
                            }
                        }
                    }
                }
            )
        }

        // Walkthrough Screen
        composable<WalkThroughRoute> {
            val walkThroughViewModel : WalkThroughViewModel = koinViewModel()
            WalkThroughScreen(
                modifier = modifier,
                onWalkThroughActionEvent = {
                    when(it) {
                        is WalkThroughActionEvent.OnScreenInitEvent -> {
                            walkThroughViewModel.logoutUser()
                        }
                        is WalkThroughActionEvent.OnLoginButtonClickEvent -> {
                            walkThroughViewModel.setLoginUserTypeEnum(it.loginUserTypeEnum)
                            navController.navigate(MobileLoginRoute)
                        }
                    }
                }
            )
        }

        // Mobile Login Screen
        composable<MobileLoginRoute> {
            val mobileLoginViewModel : MobileLoginViewModel = koinViewModel()
            val mobileLoginUIState by mobileLoginViewModel.mobileLoginUIState.collectAsState()
            MobileLoginScreen(
                modifier = modifier,
                mobileLoginUIState = mobileLoginUIState,
                onMobileLoginActionEvent = {
                    when (it) {
                        is MobileLoginActionEvent.OnPhoneNumberChangeEvent -> {
                            mobileLoginViewModel.onPhoneNumberChangeEvent(it.phoneNumber)
                        }

                        is MobileLoginActionEvent.OnBackPressEvent -> {
                            navController.navigateUp()
                        }

                        is MobileLoginActionEvent.OnGetOTPPressEvent -> {
                            mobileLoginViewModel.validatePhoneNumber(onSuccess = { phoneNumberWithCC ->
                                navController.navigate(OTPVerificationRoute(
                                    phoneNumberWithCC = phoneNumberWithCC
                                ))
                            })
                        }
                    }
                }
            )
        }

        // OTP Verification Screen
        composable<OTPVerificationRoute> { backStackEntry ->
            val otpVerificationRoute = backStackEntry.toRoute<OTPVerificationRoute>()
            val otpVerificationViewModel : OTPVerificationViewModel = koinViewModel()
            val otpVerificationUIState by otpVerificationViewModel.otpVerificationUIState.collectAsState()
            val context = LocalContext.current

            LaunchedEffect(Unit) {
                otpVerificationViewModel.errorMessageShow.collectLatest {
                    context.showToast(it)
                }
            }

            OTPVerificationScreen(
                modifier = modifier,
                otpVerificationUIState = otpVerificationUIState.copy(
                    phoneNumber = otpVerificationRoute.phoneNumberWithCC
                ),
                onOTPVerificationActionEvent = {
                    when (it) {
                        is OTPVerificationActionEvent.OnBackPressEvent -> {
                            navController.navigateUp()
                        }

                        is OTPVerificationActionEvent.OnShakeCompleteEvent -> {
                            otpVerificationViewModel.enableDisableShakeAnim(false)
                        }

                        is OTPVerificationActionEvent.OnOTPTextChangeEvent -> {
                            otpVerificationViewModel.updateOTPValue(it.otpText)
                        }

                        is OTPVerificationActionEvent.OnVerifyOTPEvent -> {
                            if (otpVerificationUIState.otpValue.length == otpVerificationUIState.otpLength) {
                                val verificationId = otpVerificationUIState.verificationId
                                    ?: return@OTPVerificationScreen
                                val phoneAuthCredential = PhoneAuthProvider.getCredential(
                                    verificationId,
                                    otpVerificationUIState.otpValue
                                )
                                otpVerificationViewModel.startOTPVerification(phoneAuthCredential)
                            } else {
                                otpVerificationViewModel.enableDisableShakeAnim(true)
                            }
                        }

                        is OTPVerificationActionEvent.OnResendOTPEvent -> {
                            otpVerificationViewModel.startTimer()
                        }

                        is OTPVerificationActionEvent.OnVerificationIdAndResendTokenEvent -> {
                            otpVerificationViewModel.updateVerificationIdAndResendToken(
                                verificationId = it.verificationId,
                                token = it.token
                            )
                        }

                        is OTPVerificationActionEvent.OnOtpButtonLoadingEvent -> {
                            otpVerificationViewModel.updateButtonLoading(it.isLoading)
                        }

                        is OTPVerificationActionEvent.OnOtpSuccessEvent -> {
                            navController.navigate(SignUpRoute) {
                                popUpTo(MobileLoginRoute) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                }
            )
        }

        // Sign Up Screen
        composable<SignUpRoute> {
            val signUpViewModel : SignUpViewModel = koinViewModel()
            val signUpUIState by signUpViewModel.signUpUIState.collectAsState()
            SignUpScreen(
                modifier = modifier,
                signUpUIState = signUpUIState,
                onSignUpScreenActionEvent = {
                    when(it) {
                        is SignUpScreenActionEvent.OnBackPressEvent -> {
                            navController.navigateUp()
                        }
                        is SignUpScreenActionEvent.OnRegisterBtnActionEvent -> {
                            // validation
                        }
                        is SignUpScreenActionEvent.OnUserCollegeIdChangeEvent -> {
                            signUpViewModel.updateUserCollegeId(it.userCollegeId)
                        }
                        is SignUpScreenActionEvent.OnUserEmailChangeEvent -> {
                            signUpViewModel.updateUserEmailId(it.userEmail)
                        }
                        is SignUpScreenActionEvent.OnUserNameChangeEvent -> {
                            signUpViewModel.updateUserName(it.userName)
                        }

                        is SignUpScreenActionEvent.OnUserCollegeYearChangeEvent -> {
                            signUpViewModel.updateCollegeYear(
                                collegeYearId = it.yearId,
                                collegeYearText = it.yearText,
                                isExpanded = it.isExpanded
                            )
                        }
                        is SignUpScreenActionEvent.OnUserDepartmentChangeEvent -> {
                            signUpViewModel.updateDepartment(
                                departmentText = it.departmentText,
                                departmentId = it.departmentId,
                                isExpanded = it.isExpanded
                            )
                        }

                        is SignUpScreenActionEvent.OnDialogShowDismissEvent -> {
                            signUpViewModel.updateShowHideDialog(it.showHideDialog)
                        }

                        is SignUpScreenActionEvent.OnProfileImageUri -> {
                            signUpViewModel.updateProfileUri(it.imageUri)
                        }
                    }
                }
            )
        }

        // Dashboard
        composable<DashboardRoute> {
            val dashboardViewModel : DashboardViewModel = koinViewModel()
            DashboardScreen(
                modifier = modifier,
                loginUserTypeEnum = dashboardViewModel.getLoginTypeEnum
            )
        }

        // Success Message Screen
        composable<SuccessScreenRoute> {
            RequestSuccessScreen(
                modifier = modifier,
                onBackPress = {
                    navController.navigateUp()
                }
            )
        }


    }
}