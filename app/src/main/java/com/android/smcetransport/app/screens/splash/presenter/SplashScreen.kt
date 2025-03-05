package com.android.smcetransport.app.screens.splash.presenter

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.smcetransport.app.R
import com.android.smcetransport.app.ui.theme.theme.theme.boldFont
import com.android.smcetransport.app.utils.ContextExtension.showToast

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    splashScreenUIState: SplashScreenUIState,
    onSplashScreenEvent : (SplashActionEvent) -> Unit
) {
    val context = LocalContext.current
    val appName = stringResource(R.string.app_name)
    val configuration = LocalConfiguration.current
    if (!splashScreenUIState.isApiLoading) {
        if (splashScreenUIState.userPhoneNumber != null) {
            onSplashScreenEvent(SplashActionEvent.MoveToHomeScreen)
        } else {
            onSplashScreenEvent(SplashActionEvent.MoveToLoginScreen)
        }
    }
    if (splashScreenUIState.errorMessage != null) {
        context.showToast(splashScreenUIState.errorMessage)
        onSplashScreenEvent(SplashActionEvent.OnErrorMessageUpdate)
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                color = colorResource(R.color.white)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Image(
            painter = painterResource(R.drawable.app_logo),
            contentDescription = appName,
            modifier = Modifier
                .padding(vertical = 8.dp)
                .width(configuration.screenHeightDp.dp / 4)
                .aspectRatio(1f)
        )
        Text(
            text = appName,
            fontSize = 30.sp,
            textAlign = TextAlign.Center,
            fontFamily = FontFamily(boldFont),
            modifier = Modifier.padding(8.dp),
            color = colorResource(R.color.black)
        )
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(
        modifier = Modifier.fillMaxSize(),
        splashScreenUIState = SplashScreenUIState(),
        onSplashScreenEvent = {

        }
    )
}