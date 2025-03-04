package com.android.smcetransport.app.di

import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.android.smcetransport.app.screens.dashboard.DashboardViewModel
import com.android.smcetransport.app.screens.mobile_login.MobileLoginViewModel
import com.android.smcetransport.app.screens.otp_verification.data.OTPVerificationRepositoryImpl
import com.android.smcetransport.app.screens.otp_verification.domain.OTPVerificationRepository
import com.android.smcetransport.app.screens.otp_verification.domain.OTPVerificationUseCase
import com.android.smcetransport.app.screens.otp_verification.presentation.OTPVerificationViewModel
import com.android.smcetransport.app.screens.signup.SignUpViewModel
import com.android.smcetransport.app.screens.splash.presenter.SplashScreenViewModel
import com.android.smcetransport.app.screens.walkthrough.WalkThroughViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    singleOf(::OTPVerificationRepositoryImpl) {
        bind<OTPVerificationRepository>()
    }

    factoryOf(::OTPVerificationUseCase)
    factoryOf(::SharedPrefs)

    viewModelOf(::SplashScreenViewModel)
    viewModelOf(::WalkThroughViewModel)
    viewModelOf(::MobileLoginViewModel)
    viewModelOf(::OTPVerificationViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::DashboardViewModel)
}