package com.android.smcetransport.app.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
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
fun CommonTextField(
    modifier: Modifier = Modifier,
    etValue : String,
    onEtValueChangeListener : (String) -> Unit,
    etPlaceHolder : String,
    isShowError : Boolean = false,
    etErrorText : String,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Default
    )
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
                    horizontal = 8.dp,
                    vertical = 8.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BasicTextField(
                value = etValue,
                onValueChange = {
                    onEtValueChangeListener(it)
                },
                modifier = Modifier.fillMaxWidth().weight(1f),
                keyboardOptions = keyboardOptions,
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
                        if (etValue.isEmpty()) {
                            Text(
                                text = etPlaceHolder,
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
        if (isShowError) {
            Text(
                text = etErrorText,
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
fun PreviewCommonTextField() {
    CommonTextField(
        modifier = Modifier.fillMaxWidth(),
        etValue = "",
        onEtValueChangeListener = {

        },
        etPlaceHolder = "Enter your name",
        isShowError = false,
        etErrorText = "Enter a valid name"
    )
}