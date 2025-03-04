package com.android.smcetransport.app.screens.signup

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.FileProvider
import com.android.smcetransport.app.BuildConfig
import com.android.smcetransport.app.R
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.screens.signup.utils.ContextExtension.createImageFile
import com.android.smcetransport.app.ui.components.AppButton
import com.android.smcetransport.app.ui.components.AppToolBar
import com.android.smcetransport.app.ui.components.CommonDropDown
import com.android.smcetransport.app.ui.components.CommonTextField
import com.android.smcetransport.app.ui.components.ImageUploadView
import com.android.smcetransport.app.ui.components.InformationDialog
import com.android.smcetransport.app.ui.components.enum.DropDownTypeEnum
import com.android.smcetransport.app.ui.components.model.DropDownModel
import com.android.smcetransport.app.ui.theme.theme.theme.normalFont
import com.android.smcetransport.app.ui.theme.theme.theme.semiBoldFont
import java.util.Objects

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    signUpUIState : SignUpUIState,
    onSignUpScreenActionEvent : (SignUpScreenActionEvent) -> Unit
) {

    val context = LocalContext.current

    var currentPhotoUri by remember { mutableStateOf(value = Uri.EMPTY) }

    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) {
        onSignUpScreenActionEvent(SignUpScreenActionEvent.OnProfileImageUri(currentPhotoUri))
    }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
        onSignUpScreenActionEvent(SignUpScreenActionEvent.OnProfileImageUri(it))
    }

    Box(modifier = modifier
        .fillMaxSize()
        .background(color = colorResource(R.color.white)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AppToolBar(
                modifier = Modifier.fillMaxWidth(),
                toolBarBgColor = colorResource(R.color.white),
                onToolBarStartIconClick = {
                    onSignUpScreenActionEvent(SignUpScreenActionEvent.OnBackPressEvent)
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
                    text = stringResource(R.string.register_text),
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
                    text = stringResource(R.string.enter_your_personal_information),
                    fontSize = 16.sp,
                    color = colorResource(R.color.gray_dark),
                    fontFamily = FontFamily(normalFont),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Start
                )
                ImageUploadView(
                    modifier = Modifier
                        .padding(16.dp)
                        .size(100.dp),
                    profileImage = signUpUIState.profileImageUri,
                    onItemClick = {
                        onSignUpScreenActionEvent(
                            SignUpScreenActionEvent.OnDialogShowDismissEvent(
                                showHideDialog = true
                            )
                        )
                    }
                )
                if (!signUpUIState.isValidProfileImage) {
                    Text(
                        text = stringResource(R.string.image_upload_error),
                        fontSize = 14.sp,
                        color = colorResource(R.color.red_color),
                        fontFamily = FontFamily(normalFont),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                }
                CommonTextField(
                    etValue = signUpUIState.userName,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    onEtValueChangeListener = {
                        onSignUpScreenActionEvent(SignUpScreenActionEvent.OnUserNameChangeEvent(it))
                    },
                    etPlaceHolder = stringResource(R.string.enter_your_name),
                    isShowError = !signUpUIState.isValidUserName,
                    etErrorText = stringResource(R.string.enter_your_name_error),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    )
                )
                CommonDropDown(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    etValue = signUpUIState.userDepartment,
                    onEtValueChangeListener = {},
                    etPlaceHolder = stringResource(R.string.select_your_department),
                    isShowError = !signUpUIState.isValidUserDepartment,
                    etErrorText = stringResource(R.string.select_your_department_error),
                    dropDownList = listOf(
                        DropDownModel(
                            dropDownId = "1",
                            dropDownText = "Mechanic",
                            dropDownTypeEnum = DropDownTypeEnum.DEPARTMENT
                        ),
                        DropDownModel(
                            dropDownId = "2",
                            dropDownText = "EEE",
                            dropDownTypeEnum = DropDownTypeEnum.DEPARTMENT
                        )
                    ),
                    isDropDownExpanded = signUpUIState.isDepartmentDropDownExpanded,
                    onDropDownClickAndExpandedState = { dropDownModel, isExpanded ->
                        onSignUpScreenActionEvent(
                            SignUpScreenActionEvent.OnUserDepartmentChangeEvent(
                                departmentText = dropDownModel?.dropDownText,
                                departmentId = dropDownModel?.dropDownId,
                                isExpanded = isExpanded
                            )
                        )
                    }
                )
                if (signUpUIState.loginUserTypeEnum == LoginUserTypeEnum.STUDENT) {
                    CommonDropDown(
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp, bottom = 8.dp),
                        etValue = signUpUIState.userCollegeYear,
                        onEtValueChangeListener = {},
                        etPlaceHolder = stringResource(R.string.select_your_college_year),
                        isShowError = !signUpUIState.isValidUserCollegeYear,
                        etErrorText = stringResource(R.string.select_your_college_year_error),
                        dropDownList = listOf(
                            DropDownModel(
                                dropDownId = "1",
                                dropDownText = "1 Year",
                                dropDownTypeEnum = DropDownTypeEnum.YEAR
                            ),
                            DropDownModel(
                                dropDownId = "2",
                                dropDownText = "2 Year",
                                dropDownTypeEnum = DropDownTypeEnum.YEAR
                            )
                        ),
                        isDropDownExpanded = signUpUIState.isYearDropDownExpanded,
                        onDropDownClickAndExpandedState = { dropDownModel, isExpanded ->
                            onSignUpScreenActionEvent(
                                SignUpScreenActionEvent.OnUserCollegeYearChangeEvent(
                                    yearText = dropDownModel?.dropDownText,
                                    yearId = dropDownModel?.dropDownId,
                                    isExpanded = isExpanded
                                )
                            )
                        }
                    )
                }
                CommonTextField(
                    etValue = signUpUIState.userEmail,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    onEtValueChangeListener = {
                        onSignUpScreenActionEvent(SignUpScreenActionEvent.OnUserEmailChangeEvent(it))
                    },
                    etPlaceHolder = stringResource(R.string.enter_your_email),
                    isShowError = !signUpUIState.isValidUserEmail,
                    etErrorText = stringResource(R.string.enter_your_email_error),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )
                CommonTextField(
                    etValue = signUpUIState.userCollegeId,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    onEtValueChangeListener = {
                        onSignUpScreenActionEvent(SignUpScreenActionEvent.OnUserCollegeIdChangeEvent(it))
                    },
                    etPlaceHolder = stringResource(R.string.enter_your_college_id),
                    isShowError = !signUpUIState.isValidUserCollegeId,
                    etErrorText = stringResource(R.string.enter_your_college_id_error),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.None,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    )
                )
            }

            AppButton(
                buttonText = stringResource(R.string.register_text),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                buttonClickEvent = {
                    onSignUpScreenActionEvent(SignUpScreenActionEvent.OnRegisterBtnActionEvent)
                },
                isButtonLoading = signUpUIState.isButtonLoading
            )

        }

        if (signUpUIState.isShowDialog) {
            InformationDialog(
                modifier = Modifier.fillMaxWidth(),
                isDismissible = true,
                dialogIcon = painterResource(R.drawable.ic_upload),
                dialogTitle = stringResource(R.string.upload_profile_photo),
                dialogDesc = stringResource(R.string.upload_profile_photo_desc),
                buttonAndIconColor = colorResource(R.color.app_main_color),
                positiveBtnText = stringResource(R.string.camera_text),
                negativeBtnText = stringResource(R.string.gallery_text),
                positiveBtnIcon = painterResource(R.drawable.ic_fill_photo_camera),
                negativeBtnIcon = painterResource(R.drawable.ic_fill_photo_library),
                onDialogDismissEvent = {
                    onSignUpScreenActionEvent(
                        SignUpScreenActionEvent.OnDialogShowDismissEvent(
                            showHideDialog = false
                        )
                    )
                },
                onPositiveBtnEvent = {
                    onSignUpScreenActionEvent(
                        SignUpScreenActionEvent.OnDialogShowDismissEvent(
                            showHideDialog = false
                        )
                    )
                    val photoFile = context.createImageFile()
                    currentPhotoUri = photoFile?.let { FileProvider.getUriForFile(
                        Objects.requireNonNull(context),
                        "${BuildConfig.APPLICATION_ID}.provider",
                        photoFile
                    ) }
                    if (currentPhotoUri != null) {
                        cameraLauncher.launch(currentPhotoUri)
                    }
                },
                onNegativeBtnEvent = {
                    onSignUpScreenActionEvent(
                        SignUpScreenActionEvent.OnDialogShowDismissEvent(
                            showHideDialog = false
                        )
                    )
                    galleryLauncher.launch("image/*")
                }
            )
        }
    }

}

@Preview
@Composable
fun PreviewSignUpScreen() {
    SignUpScreen(
        modifier = Modifier.fillMaxSize(),
        signUpUIState = SignUpUIState(isShowDialog = true),
        onSignUpScreenActionEvent = {

        }
    )
}