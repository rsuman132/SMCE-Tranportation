package com.android.smcetransport.app.screens.otp_verification.presentation

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.smcetransport.app.R
import com.android.smcetransport.app.screens.otp_verification.utils.OTPProcessEnum
import com.android.smcetransport.app.screens.otp_verification.utils.VerifyPhoneNumber
import com.android.smcetransport.app.ui.components.AppButton
import com.android.smcetransport.app.ui.components.AppToolBar
import com.android.smcetransport.app.ui.components.OTPView
import com.android.smcetransport.app.ui.components.OTP_VIEW_TYPE_BORDER
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont
import com.android.smcetransport.app.ui.theme.theme.theme.normalFont
import com.android.smcetransport.app.ui.theme.theme.theme.semiBoldFont
import com.android.smcetransport.app.utils.ContextExtension.showToast
import java.util.Locale

@Composable
fun OTPVerificationScreen(
    modifier: Modifier = Modifier,
    otpVerificationUIState: OTPVerificationUIState,
    onOTPVerificationActionEvent : (OTPVerificationActionEvent) -> Unit
) {

    val activity = LocalActivity.current as Activity

    val shakeOffset by animateFloatAsState(
        targetValue = if (otpVerificationUIState.isShaking) 10f else 0f,
        animationSpec = tween(durationMillis = 50, easing = LinearEasing),
        finishedListener = {
            onOTPVerificationActionEvent(OTPVerificationActionEvent.OnShakeCompleteEvent)
        }
    )

    val verifyPhoneNumber = remember { VerifyPhoneNumber(activity) }

    LaunchedEffect(otpVerificationUIState.otpProcessEnum) {
        when (otpVerificationUIState.otpProcessEnum) {
            OTPProcessEnum.TRIGGERED -> {
                verifyPhoneNumber.initVerifyPhoneNumber(
                    phoneNumber = otpVerificationUIState.phoneNumber,
                    resendToken = otpVerificationUIState.token,
                    onPhoneAuthCredentialSuccess = {
                        if (otpVerificationUIState.verificationId == null) {
                            return@initVerifyPhoneNumber
                        }
                        onOTPVerificationActionEvent(OTPVerificationActionEvent.OnVerifyOTPEvent)
                    },
                    onCodeSendSuccess = { verificationId, resendToken ->
                        onOTPVerificationActionEvent(
                            OTPVerificationActionEvent.OnVerificationIdAndResendTokenEvent(
                                verificationId = verificationId,
                                token = resendToken
                            )
                        )
                    },
                    onError = {
                        activity.showToast(it.message)
                        onOTPVerificationActionEvent(
                            OTPVerificationActionEvent.OnOtpButtonLoadingEvent(
                                isLoading = false
                            )
                        )
                    }
                )
            }
            OTPProcessEnum.SUCCESS -> {
                onOTPVerificationActionEvent(OTPVerificationActionEvent.OnOtpSuccessEvent)
            }
            else -> {
                return@LaunchedEffect
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
    ) {
        AppToolBar(
            modifier = Modifier.fillMaxWidth(),
            toolBarBgColor = colorResource(R.color.white),
            onToolBarStartIconClick = {
                onOTPVerificationActionEvent(OTPVerificationActionEvent.OnBackPressEvent)
            }
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.verify_your_phone_number),
                fontSize = 34.sp,
                color = colorResource(R.color.black),
                fontFamily = FontFamily(semiBoldFont),
                lineHeight = 34.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Start,
                style = LocalTextStyle.current
            )
            val descText = stringResource(
                R.string.otp_login_desc,
                otpVerificationUIState.phoneNumber
            )
            Text(
                text = descText,
                fontSize = 16.sp,
                color = colorResource(R.color.gray_dark),
                fontFamily = FontFamily(normalFont),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Start
            )
            OTPView(
                modifier = Modifier
                    .wrapContentWidth()
                    .offset {
                        IntOffset(shakeOffset.toInt(), 0)
                    }
                    .padding(vertical = 16.dp),
                otpText = otpVerificationUIState.otpValue,
                onOtpTextChange = {
                    onOTPVerificationActionEvent(OTPVerificationActionEvent.OnOTPTextChangeEvent(it))
                },
                type = OTP_VIEW_TYPE_BORDER,
                password = false,
                containerSize = 48.dp,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                charColor = colorResource(R.color.black),
                otpCount = otpVerificationUIState.otpLength,
                containerColor = colorResource(R.color.light_white)
            )
            val resendText = if (otpVerificationUIState.timerCount > 0) {
                val twoDigitFormatedString = String.format(
                    Locale.ENGLISH,
                    "%02d",
                    otpVerificationUIState.timerCount
                )
                stringResource(R.string.resend_otp_in_placeholder_seconds, twoDigitFormatedString)
            } else {
                stringResource(R.string.resend_otp_text)
            }
            Text(
                text = resendText,
                fontSize = 16.sp,
                color = colorResource(R.color.black),
                fontFamily = FontFamily(mediumFont),
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable(enabled = otpVerificationUIState.timerCount <= 0) {
                        onOTPVerificationActionEvent(OTPVerificationActionEvent.OnResendOTPEvent)
                    }
                    .padding(vertical = 16.dp),
                textAlign = TextAlign.Center
            )
            AppButton(
                buttonText = stringResource(R.string.verify_text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                buttonClickEvent = {
                    if (otpVerificationUIState.verificationId == null) {
                        return@AppButton
                    }
                    onOTPVerificationActionEvent(OTPVerificationActionEvent.OnVerifyOTPEvent)
                },
                isButtonLoading = otpVerificationUIState.isButtonLoading
            )

        }
    }

}

@Preview
@Composable
fun PreviewOTPVerificationScreen() {
    OTPVerificationScreen(
        modifier = Modifier.fillMaxSize(),
        otpVerificationUIState = OTPVerificationUIState(
            phoneNumber = "+919487551995",
            otpValue = "1234",
            timerCount = 0
        ),
        onOTPVerificationActionEvent = {

        }
    )
}