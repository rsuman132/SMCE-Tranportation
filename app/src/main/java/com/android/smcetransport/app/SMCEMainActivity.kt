package com.android.smcetransport.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.android.smcetransport.app.ui.navigation.SMCETransportApp
import com.android.smcetransport.app.ui.theme.theme.theme.SMCETransportTheme

class SMCEMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            SMCETransportTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SMCETransportApp(
                        modifier = Modifier
                            .fillMaxSize()
                            .imePadding()
                            .padding(innerPadding)
                    )
                }
            }
        }
    }
}