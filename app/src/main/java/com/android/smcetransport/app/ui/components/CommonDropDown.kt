package com.android.smcetransport.app.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.smcetransport.app.R
import com.android.smcetransport.app.ui.components.enum.DropDownTypeEnum
import com.android.smcetransport.app.ui.components.model.DropDownModel
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont
import com.android.smcetransport.app.ui.theme.theme.theme.normalFont

@Composable
fun CommonDropDown(
    modifier: Modifier = Modifier,
    etValue : String,
    onEtValueChangeListener : (String) -> Unit,
    etPlaceHolder : String,
    isShowError : Boolean = false,
    etErrorText : String,
    dropDownList : List<DropDownModel> = listOf(),
    isDropDownExpanded : Boolean = false,
    onDropDownClickAndExpandedState : (DropDownModel?, Boolean) -> Unit,
    isEnable : Boolean = true
) {

    val roundedShape = RoundedCornerShape(10)

    Box(modifier = modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .fillMaxWidth()
                    .border(
                        width = 2.dp,
                        color = colorResource(R.color.light_white),
                        shape = roundedShape
                    )
                    .clickable(enabled = isEnable) {
                        onDropDownClickAndExpandedState(null, true)
                    }
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
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    readOnly = true,
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
                Image(
                    painter = painterResource(R.drawable.ic_arrow_drop_down_circle),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(color = colorResource(R.color.gray_dark)),
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .fillMaxHeight(0.65f)
                        .aspectRatio(1f)
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
        DropdownMenu(
            expanded = isDropDownExpanded,
            onDismissRequest = {
                onDropDownClickAndExpandedState(null, false)
            },
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clip(roundedShape)
                .background(color = colorResource(R.color.white)),
            shape = roundedShape,
            containerColor = colorResource(android.R.color.transparent),
            shadowElevation = 0.dp
        ) {
            dropDownList.forEach {
                DropdownMenuItem(
                    onClick = {
                        onDropDownClickAndExpandedState(it, false)
                    },
                    text = {
                        Text(
                            text = it.dropDownText,
                            fontSize = 20.sp,
                            color = colorResource(R.color.black),
                            fontFamily = FontFamily(normalFont),
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(roundedShape)
                        .background(color = colorResource(R.color.white))
                )
            }
        }
    }

}

@Preview
@Composable
fun PreviewCommonDropDown() {
    CommonDropDown(
        modifier = Modifier.fillMaxSize(),
        etValue = "",
        onEtValueChangeListener = {

        },
        etPlaceHolder = "Select your department",
        isShowError = false,
        etErrorText = "Please select your department",
        dropDownList = listOf(
            DropDownModel(
                dropDownId = "1",
                dropDownText = "Text 1",
                dropDownTypeEnum = DropDownTypeEnum.DEPARTMENT
            ),
            DropDownModel(
                dropDownId = "2",
                dropDownText = "Text 2",
                dropDownTypeEnum = DropDownTypeEnum.DEPARTMENT
            ),
            DropDownModel(
                dropDownId = "3",
                dropDownText = "Text 3",
                dropDownTypeEnum = DropDownTypeEnum.DEPARTMENT
            )
        ),
        isDropDownExpanded = true,
        onDropDownClickAndExpandedState = { _, _ ->

        }
    )
}