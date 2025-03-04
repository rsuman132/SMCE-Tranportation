package com.android.smcetransport.app

import android.app.Application
import com.android.smcetransport.app.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class SMCEApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@SMCEApplication)
            androidLogger()
            modules(appModule)
        }
    }

}