package com.android.smcetransport.app.screens.mobile_login

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.smcetransport.app.R
import com.android.smcetransport.app.core.utils.MobileValidator
import com.android.smcetransport.app.ui.components.AppButton
import com.android.smcetransport.app.ui.components.AppToolBar
import com.android.smcetransport.app.ui.components.PhoneNumberTextField
import com.android.smcetransport.app.ui.theme.theme.theme.normalFont
import com.android.smcetransport.app.ui.theme.theme.theme.semiBoldFont

@Composable
fun MobileLoginScreen(
    modifier: Modifier = Modifier,
    mobileLoginUIState: MobileLoginUIState,
    onMobileLoginActionEvent : (MobileLoginActionEvent) -> Unit
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
    ) {
        AppToolBar(
            modifier = Modifier.fillMaxWidth(),
            toolBarBgColor = colorResource(R.color.white),
            onToolBarStartIconClick = {
                onMobileLoginActionEvent(MobileLoginActionEvent.OnBackPressEvent)
            }
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Text(
                text = stringResource(R.string.whats_your_phone_number),
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

            Text(
                text = stringResource(R.string.mobile_login_desc),
                fontSize = 16.sp,
                color = colorResource(R.color.gray_dark),
                fontFamily = FontFamily(normalFont),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                textAlign = TextAlign.Start
            )
            PhoneNumberTextField(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 16.dp),
                flagText = stringResource(R.string.indian_flag_emoji),
                countryCodeText = MobileValidator.INDIAN_DIAL_CODE,
                phoneNumberText = mobileLoginUIState.phoneNumber,
                phoneNumberErrorText = stringResource(R.string.phone_number_validation_error_text),
                isShowPhoneNumberError = mobileLoginUIState.isNotValidPhoneNumber,
                onPhoneNumberChangeListener = {
                    onMobileLoginActionEvent(
                        MobileLoginActionEvent.OnPhoneNumberChangeEvent(phoneNumber = it)
                    )
                },
                phoneNumberPlaceHolder = stringResource(R.string.phone_number_text)
            )
            AppButton(
                buttonText = stringResource(R.string.get_otp_text),
                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                buttonClickEvent = {
                    onMobileLoginActionEvent(MobileLoginActionEvent.OnGetOTPPressEvent)
                }
            )

        }
    }

}


@Preview
@Composable
fun PreviewMobileLoginScreen() {
    MobileLoginScreen(
        modifier = Modifier.fillMaxSize(),
        mobileLoginUIState = MobileLoginUIState(),
        onMobileLoginActionEvent = {

        }
    )
}