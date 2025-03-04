package com.android.smcetransport.app.screens.walkthrough

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.smcetransport.app.R
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.ui.components.AppButton
import com.android.smcetransport.app.ui.theme.theme.theme.boldFont
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont

@Composable
fun WalkThroughScreen(
    modifier: Modifier = Modifier,
    onWalkThroughActionEvent: (WalkThroughActionEvent) -> Unit
) {
    val configuration = LocalConfiguration.current

    LaunchedEffect(Unit) {
        onWalkThroughActionEvent(WalkThroughActionEvent.OnScreenInitEvent)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = colorResource(R.color.white))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.welcome_text),
            color = colorResource(R.color.app_main_color),
            fontSize = 34.sp,
            fontFamily = FontFamily(boldFont),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Left
        )
        Text(
            text = stringResource(R.string.login_to_continue),
            color = colorResource(R.color.black),
            fontSize = 18.sp,
            fontFamily = FontFamily(mediumFont),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Start
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(R.drawable.app_logo),
                contentDescription = stringResource(R.string.app_name),
                modifier = Modifier.size(configuration.screenHeightDp.dp / 3)
            )
        }
        Text(
            text = stringResource(R.string.app_name),
            color = colorResource(R.color.app_main_color),
            fontSize = 34.sp,
            fontFamily = FontFamily(boldFont),
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.walk_through_desc),
            color = colorResource(R.color.black),
            fontSize = 18.sp,
            fontFamily = FontFamily(mediumFont),
            modifier = Modifier.padding(bottom = 8.dp),
            textAlign = TextAlign.Center
        )
        AppButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp, bottom = 8.dp
                ),
            buttonStokeBorderColor = colorResource(R.color.app_main_color),
            buttonBgColor = colorResource(R.color.app_main_color),
            buttonText = stringResource(R.string.student_login_text),
            buttonClickEvent = {
                onWalkThroughActionEvent(
                    WalkThroughActionEvent.OnLoginButtonClickEvent(
                        LoginUserTypeEnum.STUDENT
                    )
                )
            }
        )
        AppButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp, bottom = 8.dp
                ),
            buttonStokeBorderColor = colorResource(R.color.app_main_color),
            buttonBgColor = colorResource(R.color.app_main_color),
            buttonText = stringResource(R.string.staff_login_text),
            buttonClickEvent = {
                onWalkThroughActionEvent(
                    WalkThroughActionEvent.OnLoginButtonClickEvent(
                        LoginUserTypeEnum.STAFF
                    )
                )
            }
        )
        AppButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            buttonStokeBorderColor = colorResource(R.color.app_main_color),
            buttonBgColor = colorResource(android.R.color.transparent),
            buttonText = stringResource(R.string.in_charge_login_text),
            buttonTextAndSpinnerColor = colorResource(R.color.app_main_color),
            buttonClickEvent = {
                onWalkThroughActionEvent(
                    WalkThroughActionEvent.OnLoginButtonClickEvent(
                        LoginUserTypeEnum.INCHARGE
                    )
                )
            }
        )
    }
}


@Preview
@Composable
fun PreviewWalkThroughScreen() {
    WalkThroughScreen(
        modifier = Modifier.fillMaxSize(),
        onWalkThroughActionEvent = {

        }
    )
}