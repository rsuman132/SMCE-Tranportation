package com.android.smcetransport.app.screens.department.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.android.smcetransport.app.R
import com.android.smcetransport.app.core.dto.DepartmentModel
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.core.enum.StudentYearEnum
import com.android.smcetransport.app.ui.components.AppButton
import com.android.smcetransport.app.ui.components.AppToolBar
import com.android.smcetransport.app.ui.components.CommonTextField
import com.android.smcetransport.app.ui.components.TitleDescCardItem
import com.android.smcetransport.app.ui.theme.theme.theme.mediumFont

@Composable
fun DepartmentListScreen(
    modifier: Modifier = Modifier,
    departmentListUIState : DepartmentListUIState,
    onDepartmentActionEvent : (DepartmentActionEvent) -> Unit
) {

    val fivePercentageRoundedCorner = RoundedCornerShape(5)

    val firstRightIcon = if (departmentListUIState.isFromOverAllData) {
        null
    } else {
        painterResource(R.drawable.ic_add)
    }

    Box(modifier = modifier.fillMaxSize()
        .background(color = colorResource(R.color.white)),
        contentAlignment = Alignment.Center
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            AppToolBar(
                modifier = Modifier.fillMaxWidth(),
                toolBarText = stringResource(R.string.departments_text),
                onToolBarStartIconClick = {
                    onDepartmentActionEvent(DepartmentActionEvent.OnBackPressEvent)
                },
                isShowLoading = departmentListUIState.isLoading,
                firstRightIcon = firstRightIcon,
                onFirstRightIconClick = {
                    onDepartmentActionEvent(DepartmentActionEvent.OnAddDepartmentEvent)
                }
            )
            if (!departmentListUIState.isLoading) {
                if (departmentListUIState.departmentList.isNotEmpty()) {
                    LazyColumn(
                        modifier = modifier
                            .fillMaxSize()
                            .weight(1f)
                            .background(color = colorResource(R.color.white))
                            .padding(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(
                            items = departmentListUIState.departmentList,
                            key = {
                                it.departmentCode ?: "${System.currentTimeMillis()}"
                            }
                        ) {

                            val onItemClick = if (departmentListUIState.isFromOverAllData) {
                                {
                                    onDepartmentActionEvent(DepartmentActionEvent.OnStudentYearListDialogShowHideEvent(
                                        show = true,
                                        selectedId = it.id,
                                        selectedDepartmentName = it.departmentName
                                    ))
                                }
                            } else {
                                null
                            }

                            TitleDescCardItem(
                                modifier = Modifier.fillMaxWidth(),
                                titleText = it.departmentName ?: "",
                                descText = it.departmentCode ?: "",
                                onItemClick = onItemClick
                            )
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.no_department_added),
                            modifier = Modifier.fillMaxWidth()
                                .align(Alignment.Center),
                            textAlign = TextAlign.Center,
                            fontFamily = FontFamily(mediumFont),
                            fontSize = 16.sp,
                            color = colorResource(R.color.black)
                        )
                    }
                }
            }
        }

        if (departmentListUIState.isShowAddDepartmentDialog) {
            Dialog(
                onDismissRequest = {
                    onDepartmentActionEvent(DepartmentActionEvent.OnAddDepartmentDialogDismiss)
                },
                properties = DialogProperties(
                    dismissOnClickOutside = !departmentListUIState.isAddDepartmentButtonLoading,
                    dismissOnBackPress = !departmentListUIState.isAddDepartmentButtonLoading,
                    usePlatformDefaultWidth = false
                )
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp)
                        .clip(shape = fivePercentageRoundedCorner)
                        .background(color = colorResource(R.color.white))
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp)
                ) {

                    CommonTextField(
                        modifier = Modifier.fillMaxWidth(),
                        etValue = departmentListUIState.departmentNameEtValue,
                        onEtValueChangeListener = {
                            onDepartmentActionEvent(DepartmentActionEvent.OnDepartmentNameUpdate(it))
                        },
                        etPlaceHolder = stringResource(R.string.enter_department_name),
                        isShowError = departmentListUIState.showDepartmentError,
                        etErrorText = stringResource(R.string.enter_department_name_error),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    )


                    CommonTextField(
                        modifier = Modifier.fillMaxWidth(),
                        etValue = departmentListUIState.departmentCodeEtValue,
                        onEtValueChangeListener = {
                            onDepartmentActionEvent(DepartmentActionEvent.OnDepartmentCodeUpdate(it))
                        },
                        etPlaceHolder = stringResource(R.string.enter_department_code),
                        isShowError = departmentListUIState.showDepartmentCodeError,
                        etErrorText = stringResource(R.string.enter_department_code_error),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Characters,
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Next
                        )
                    )

                    AppButton(
                        buttonText = stringResource(R.string.add_department),
                        modifier = Modifier.fillMaxWidth(),
                        isButtonLoading = departmentListUIState.isAddDepartmentButtonLoading,
                        buttonClickEvent = {
                            onDepartmentActionEvent(DepartmentActionEvent.OnAddDepartmentEventInDialog)
                        }
                    )

                }

            }
        }

        if (departmentListUIState.showYearAndStaffDialog) {
            Dialog(
                onDismissRequest = {
                    onDepartmentActionEvent(DepartmentActionEvent.OnStudentYearListDialogShowHideEvent(
                        show = false,
                        selectedId = null,
                        selectedDepartmentName = null
                    ))
                },
                properties = DialogProperties(
                    usePlatformDefaultWidth = false
                )
            ) {

                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(16.dp)
                        .clip(shape = fivePercentageRoundedCorner)
                        .background(color = colorResource(R.color.white))
                        .padding(16.dp)
                ) {
                    StudentYearEnum.entries.forEach {
                        Text(
                            it.name,
                            fontSize = 18.sp,
                            fontFamily = FontFamily(mediumFont),
                            color = colorResource(R.color.black),
                            modifier = Modifier.fillMaxWidth().clickable {
                                onDepartmentActionEvent(
                                    DepartmentActionEvent.OnSelectedYearOrStaffId(
                                        selectedYearOrStaff = it.name,
                                        loginUserTypeEnum = LoginUserTypeEnum.STUDENT
                                    )
                                )
                            }.padding(16.dp)
                        )
                        HorizontalDivider()
                    }
                    Text(
                        stringResource(R.string.staff_text),
                        fontSize = 18.sp,
                        fontFamily = FontFamily(mediumFont),
                        color = colorResource(R.color.black),
                        modifier = Modifier.fillMaxWidth().clickable {
                            DepartmentActionEvent.OnSelectedYearOrStaffId(
                                selectedYearOrStaff = null,
                                loginUserTypeEnum = LoginUserTypeEnum.STAFF
                            )
                        }.padding(16.dp)
                    )
                }

            }
        }

    }

}


@Preview
@Composable
fun PreviewDepartmentListScreen() {
    DepartmentListScreen(
        modifier = Modifier.fillMaxSize(),
        departmentListUIState = DepartmentListUIState(
            isLoading = false,
            departmentList = listOf(
                DepartmentModel(
                    id = "1",
                    departmentName = "Mechanic",
                    departmentCode = "MECH"
                ),
                DepartmentModel(
                    id = "1",
                    departmentName = "Civil",
                    departmentCode = "CIVIL"
                ),
                DepartmentModel(
                    id = "1",
                    departmentName = "Electrical and Electronic",
                    departmentCode = "EEE"
                ),
                DepartmentModel(
                    id = "1",
                    departmentName = "Computer Science",
                    departmentCode = "CSE"
                )
            ),
            isShowAddDepartmentDialog = false,
            showYearAndStaffDialog = true
        ),
        onDepartmentActionEvent = {

        }
    )
}