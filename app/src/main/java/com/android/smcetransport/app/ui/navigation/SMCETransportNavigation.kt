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
import com.android.smcetransport.app.screens.bus_managment.presentation.AddBusActionEvent
import com.android.smcetransport.app.screens.bus_managment.presentation.AddBusScreen
import com.android.smcetransport.app.screens.bus_managment.presentation.AddBusUIState
import com.android.smcetransport.app.screens.bus_managment.presentation.AddBusViewModel
import com.android.smcetransport.app.screens.bus_managment.presentation.BusListActionEvent
import com.android.smcetransport.app.screens.bus_managment.presentation.BusListScreen
import com.android.smcetransport.app.screens.bus_managment.presentation.BusListUIState
import com.android.smcetransport.app.screens.bus_managment.presentation.BusListViewModel
import com.android.smcetransport.app.screens.dashboard.presentation.DashboardActionEvents
import com.android.smcetransport.app.screens.dashboard.presentation.DashboardScreen
import com.android.smcetransport.app.screens.dashboard.presentation.DashboardViewModel
import com.android.smcetransport.app.screens.department.presentation.DepartmentActionEvent
import com.android.smcetransport.app.screens.department.presentation.DepartmentListScreen
import com.android.smcetransport.app.screens.department.presentation.DepartmentListViewModel
import com.android.smcetransport.app.screens.mobile_login.MobileLoginActionEvent
import com.android.smcetransport.app.screens.mobile_login.MobileLoginScreen
import com.android.smcetransport.app.screens.mobile_login.MobileLoginViewModel
import com.android.smcetransport.app.screens.otp_verification.presentation.OTPVerificationActionEvent
import com.android.smcetransport.app.screens.otp_verification.presentation.OTPVerificationScreen
import com.android.smcetransport.app.screens.otp_verification.presentation.OTPVerificationViewModel
import com.android.smcetransport.app.screens.request_success.RequestSuccessScreen
import com.android.smcetransport.app.screens.signup.presentation.SignUpScreen
import com.android.smcetransport.app.screens.signup.presentation.SignUpScreenActionEvent
import com.android.smcetransport.app.screens.signup.presentation.SignUpViewModel
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
    val context = LocalContext.current
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
                    when (it) {
                        is SplashActionEvent.MoveToLoginScreen -> {
                            navController.navigate(WalkThroughRoute) {
                                popUpTo(SplashScreenRoute) {
                                    inclusive = true
                                }
                            }
                        }

                        is SplashActionEvent.MoveToHomeScreen -> {
                            navController.navigate(DashboardRoute) {
                                popUpTo(SplashScreenRoute) {
                                    inclusive = true
                                }
                            }
                        }

                        is SplashActionEvent.OnErrorMessageUpdate -> {
                            splashScreenViewModel.updateErrorMessage()
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
                            if (otpVerificationUIState.userPhoneNumber != null) {
                                navController.navigate(DashboardRoute) {
                                    popUpTo(MobileLoginRoute) {
                                        inclusive = true
                                    }
                                }
                            } else {
                                navController.navigate(SignUpRoute) {
                                    popUpTo(MobileLoginRoute) {
                                        inclusive = true
                                    }
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

            LaunchedEffect(Unit) {
                signUpViewModel.errorMessage.collectLatest {
                    context.showToast(it)
                }
            }

            LaunchedEffect(Unit) {
                signUpViewModel.apiSuccessChannel.collectLatest {
                    navController.navigate(DashboardRoute) {
                        popUpTo(WalkThroughRoute) {
                            inclusive = true
                        }
                    }
                }
            }

            SignUpScreen(
                modifier = modifier,
                signUpUIState = signUpUIState,
                onSignUpScreenActionEvent = {
                    when(it) {
                        is SignUpScreenActionEvent.OnBackPressEvent -> {
                            navController.navigateUp()
                        }
                        is SignUpScreenActionEvent.OnRegisterBtnActionEvent -> {
                            signUpViewModel.validation()
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

                        is SignUpScreenActionEvent.OnDepartmentApiEvent -> {
                            signUpViewModel.getAllDepartment()
                        }
                    }
                }
            )
        }

        // Dashboard
        composable<DashboardRoute> {
            val dashboardViewModel : DashboardViewModel = koinViewModel()
            val dashboardUIState by dashboardViewModel.dashboardUIState.collectAsState()
            LaunchedEffect(Unit) {
                dashboardViewModel.errorMessage.collectLatest {
                    context.showToast(it)
                }
            }
            LaunchedEffect(Unit) {
                dashboardViewModel.logoutSuccessEvent.collectLatest {
                    navController.navigate(WalkThroughRoute) {
                        popUpTo(DashboardRoute) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }
            DashboardScreen(
                modifier = modifier,
                loginUserTypeEnum = dashboardViewModel.getLoginTypeEnum,
                dashboardUIState = dashboardUIState,
                onDashboardActionEvents = {
                    when(it) {
                        is DashboardActionEvents.OnLogoutDialogShowEvent -> {
                            dashboardViewModel.updateDialogShowStatus(it.show)
                        }

                        is DashboardActionEvents.OnLogoutTriggerEvent -> {
                            dashboardViewModel.updateDialogShowStatus(false)
                            dashboardViewModel.logoutUser()
                        }

                        is DashboardActionEvents.OnManageDepartmentClickEvent -> {
                            navController.navigate(DepartmentScreenRoute)
                        }

                        is DashboardActionEvents.OnManageBusClickEvent -> {
                            navController.navigate(BusListScreenRoute)
                        }
                    }
                }
            )
        }

        // Department
        composable<DepartmentScreenRoute> {
            val departmentListViewModel : DepartmentListViewModel = koinViewModel()
            val departmentListUIState by departmentListViewModel.departmentListUIState.collectAsState()

            LaunchedEffect(Unit) {
                departmentListViewModel.errorMessage.collectLatest {
                    context.showToast(it)
                }
            }

            DepartmentListScreen(
                modifier = modifier,
                departmentListUIState = departmentListUIState,
                onDepartmentActionEvent = {
                    when(it) {
                        is DepartmentActionEvent.OnBackPressEvent -> {
                            navController.navigateUp()
                        }
                        is DepartmentActionEvent.OnAddDepartmentEvent -> {
                            departmentListViewModel.updateDepartmentDialog(true)
                        }
                        is DepartmentActionEvent.OnAddDepartmentEventInDialog -> {
                            departmentListViewModel.addDepartmentValidation()
                        }
                        is DepartmentActionEvent.OnDepartmentCodeUpdate -> {
                            departmentListViewModel.updateDepartmentCode(it.code)
                        }
                        is DepartmentActionEvent.OnDepartmentNameUpdate -> {
                            departmentListViewModel.updateDepartmentName(it.name)
                        }
                        is DepartmentActionEvent.OnAddDepartmentDialogDismiss -> {
                            departmentListViewModel.updateDepartmentDialog(false)
                        }
                    }
                }
            )
        }

        // Bus List
        composable<BusListScreenRoute> {

            val busListViewModel : BusListViewModel = koinViewModel()
            val busListUIState by busListViewModel.busListUIState.collectAsState()

            LaunchedEffect(Unit) {
                val saveStateHandle = navController.currentBackStackEntry?.savedStateHandle
                saveStateHandle?.get<Boolean?>("refresh")?.let {
                    if (it) {
                        busListViewModel.getAllBusList()
                    }
                }
            }

            LaunchedEffect(Unit) {
                busListViewModel.errorMessage.collectLatest {
                    context.showToast(it)
                }
            }

            BusListScreen(
                modifier = modifier,
                busListUIState = busListUIState,
                onBusListActionEvent = {
                    when(it) {
                        is BusListActionEvent.OnAddBusEvent -> {
                            navController.navigate(BusAddScreenRoute)
                        }
                        is BusListActionEvent.OnBackPressEvent -> {
                            navController.navigateUp()
                        }
                    }
                }
            )
        }

        // Add Bus
        composable<BusAddScreenRoute> {

            val addBusViewModel : AddBusViewModel = koinViewModel()
            val addBusUIState by addBusViewModel.addBusUIState.collectAsState()

            LaunchedEffect(Unit) {
                addBusViewModel.errorMessage.collectLatest {
                    context.showToast(it)
                }
            }

            LaunchedEffect(Unit) {
                addBusViewModel.refreshState.collectLatest { isRefresh ->
                    if (isRefresh != null) {
                        navController.previousBackStackEntry?.savedStateHandle?.set(
                            "refresh",
                            isRefresh
                        )
                    }
                }
            }

            AddBusScreen(
                modifier = modifier,
                addBusUIState = addBusUIState,
                onAddBusActionEvent = {
                    when(it) {
                        is AddBusActionEvent.OnAddBusBtnEvent -> {
                            addBusViewModel.addBusValidation()
                        }
                        is AddBusActionEvent.OnBackPressEvent -> {
                            navController.navigateUp()
                        }

                        is AddBusActionEvent.OnBusTextFieldUpdateEvent -> {
                            if (it.busNo != null) {
                                addBusViewModel.updateBusNo(it.busNo)
                                return@AddBusScreen
                            }
                            if (it.busRegisterNo != null) {
                                addBusViewModel.updateBusRegisterNo(it.busRegisterNo)
                                return@AddBusScreen
                            }
                            if (it.busRoute != null) {
                                addBusViewModel.updateBusRoute(it.busRoute)
                                return@AddBusScreen
                            }
                            if (it.busStartingPoint != null) {
                                addBusViewModel.updateBusStartingPoint(it.busStartingPoint)
                                return@AddBusScreen
                            }
                            if (it.busDriverName != null) {
                                addBusViewModel.updateDriverName(it.busDriverName)
                                return@AddBusScreen
                            }
                        }
                    }
                }
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