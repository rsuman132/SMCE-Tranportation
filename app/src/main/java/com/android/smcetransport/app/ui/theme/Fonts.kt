package com.android.smcetransport.app.ui.theme.theme.theme

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import com.android.smcetransport.app.R

val thinFont = Font(
    resId = R.font.font_thin,
    weight = FontWeight.Thin,
    style = FontStyle.Normal
)
val extraLightFont = Font(
    resId = R.font.font_extra_light,
    weight = FontWeight.ExtraLight,
    style = FontStyle.Normal
)
val lightFont = Font(
    resId = R.font.font_light,
    weight = FontWeight.Light,
    style = FontStyle.Normal
)
val normalFont = Font(
    resId = R.font.font_normal,
    weight = FontWeight.Normal,
    style = FontStyle.Normal
)
val mediumFont = Font(
    resId = R.font.font_medium,
    weight = FontWeight.Medium,
    style = FontStyle.Normal
)
val semiBoldFont = Font(
    resId = R.font.font_semi_bold,
    weight = FontWeight.SemiBold,
    style = FontStyle.Normal
)
val boldFont = Font(
    resId = R.font.font_bold,
    weight = FontWeight.Bold,
    style = FontStyle.Normal
)
val extraBoldFont = Font(
    resId = R.font.font_extra_bold,
    weight = FontWeight.ExtraBold,
    style = FontStyle.Normal
)
val blackFont = Font(
    resId = R.font.font_black,
    weight = FontWeight.Black,
    style = FontStyle.Normal
)
val CustomFonts = FontFamily(
    fonts = listOf(
        thinFont,
        extraLightFont,
        lightFont,
        normalFont,
        mediumFont,
        semiBoldFont,
        boldFont,
        extraBoldFont,
        blackFont
    )
)