package com.android.smcetransport.app.ui.theme.theme.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import com.android.smcetransport.app.R


@Composable
private fun appColorScheme() = lightColorScheme(
    primary = colorResource(R.color.app_main_color),
    secondary = colorResource(R.color.app_main_color),
    tertiary = colorResource(R.color.app_main_color),
    background = colorResource(R.color.white),
    surface = colorResource(R.color.light_white)
)

@Composable
fun SMCETransportTheme(
    content: @Composable () -> Unit
) {


    MaterialTheme(
        colorScheme = appColorScheme(),
        typography = Typography,
        content = content
    )
}