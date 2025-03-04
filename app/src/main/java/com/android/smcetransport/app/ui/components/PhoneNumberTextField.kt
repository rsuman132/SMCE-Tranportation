package com.android.smcetransport.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.smcetransport.app.R
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont
import com.android.smcetransport.app.ui.theme.theme.theme.normalFont

@Composable
fun PhoneNumberTextField(
    modifier: Modifier = Modifier,
    flagText : String,
    countryCodeText : String,
    phoneNumberText : String,
    phoneNumberErrorText : String,
    phoneNumberPlaceHolder : String,
    isShowPhoneNumberError : Boolean,
    onPhoneNumberChangeListener : (String) -> Unit
) {

    val roundedShape = RoundedCornerShape(10)

    Column(modifier = modifier.fillMaxWidth()) {

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .border(
                    width = 2.dp,
                    color = colorResource(R.color.light_white),
                    shape = roundedShape
                )
                .padding(
                    horizontal = 16.dp,
                    vertical = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = flagText,
                fontSize = 20.sp,
                color = colorResource(R.color.black),
                fontFamily = FontFamily(mediumFont)
            )
            Text(
                text = countryCodeText,
                fontSize = 20.sp,
                color = colorResource(R.color.black),
                fontFamily = FontFamily(mediumFont),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
            VerticalDivider(
                modifier = Modifier
                    .fillMaxHeight()
            )
            BasicTextField(
                value = phoneNumberText,
                onValueChange = {
                    onPhoneNumberChangeListener(it)
                },
                modifier = Modifier.fillMaxWidth().weight(1f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Phone,
                    imeAction = ImeAction.Done
                ),
                textStyle = TextStyle(
                    color = colorResource(R.color.black),
                    fontSize = 20.sp,
                    fontFamily = FontFamily(mediumFont)
                ),
                cursorBrush = SolidColor(colorResource(R.color.app_main_color)),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        if (phoneNumberText.isEmpty()) {
                            Text(
                                text = phoneNumberPlaceHolder,
                                fontSize = 20.sp,
                                color = colorResource(R.color.gray_dark),
                                fontFamily = FontFamily(normalFont)
                            )
                        }
                        innerTextField()
                    }
                },
            )
        }
        if (isShowPhoneNumberError) {
            Text(
                text = phoneNumberErrorText,
                fontSize = 14.sp,
                color = colorResource(R.color.red_color),
                fontFamily = FontFamily(normalFont),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
    }

}


@Preview
@Composable
fun PreviewPhoneNumberTextField() {
    PhoneNumberTextField(
        modifier = Modifier.fillMaxWidth()
            .background(color = colorResource(R.color.white)),
        flagText = "🇮🇳",
        countryCodeText = "+91",
        phoneNumberText = "948755",
        phoneNumberErrorText = stringResource(R.string.phone_number_validation_error_text),
        isShowPhoneNumberError = true,
        phoneNumberPlaceHolder = "Phone number",
        onPhoneNumberChangeListener = {

        }
    )
}