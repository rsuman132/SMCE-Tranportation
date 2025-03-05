package com.android.smcetransport.app.di

import com.android.smcetransport.app.core.network.ApiExecution
import com.android.smcetransport.app.core.network.ApiUrls
import com.android.smcetransport.app.core.network.KtorHttpClient
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.android.smcetransport.app.screens.dashboard.DashboardViewModel
import com.android.smcetransport.app.screens.mobile_login.MobileLoginViewModel
import com.android.smcetransport.app.screens.otp_verification.data.OTPVerificationRepositoryImpl
import com.android.smcetransport.app.screens.otp_verification.domain.OTPVerificationRepository
import com.android.smcetransport.app.screens.otp_verification.domain.OTPVerificationUseCase
import com.android.smcetransport.app.screens.otp_verification.presentation.OTPVerificationViewModel
import com.android.smcetransport.app.screens.signup.data.SignUpRepositoryImpl
import com.android.smcetransport.app.screens.signup.domain.SignUpRepository
import com.android.smcetransport.app.screens.signup.domain.SignUpUseCase
import com.android.smcetransport.app.screens.signup.presentation.SignUpViewModel
import com.android.smcetransport.app.screens.splash.data.SplashRepositoryImpl
import com.android.smcetransport.app.screens.splash.domain.SplashRepository
import com.android.smcetransport.app.screens.splash.domain.SplashUseCase
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
    singleOf(::SignUpRepositoryImpl) {
        bind<SignUpRepository>()
    }
    singleOf(::SplashRepositoryImpl) {
        bind<SplashRepository>()
    }

    factoryOf(::SharedPrefs)
    factoryOf(::OTPVerificationUseCase)
    factoryOf(::SignUpUseCase)
    factoryOf(::KtorHttpClient)
    factoryOf(::ApiUrls)
    factoryOf(::ApiExecution)
    factoryOf(::SplashUseCase)

    viewModelOf(::SplashScreenViewModel)
    viewModelOf(::WalkThroughViewModel)
    viewModelOf(::MobileLoginViewModel)
    viewModelOf(::OTPVerificationViewModel)
    viewModelOf(::SignUpViewModel)
    viewModelOf(::DashboardViewModel)
}