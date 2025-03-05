package com.android.smcetransport.app.screens.signup.presentation

import android.net.Uri

sealed class SignUpScreenActionEvent {

    data object OnBackPressEvent : SignUpScreenActionEvent()

    data class OnUserNameChangeEvent(
        val userName : String
    ) : SignUpScreenActionEvent()

    data class OnUserEmailChangeEvent(
        val userEmail : String
    ) : SignUpScreenActionEvent()

    data class OnUserCollegeIdChangeEvent(
        val userCollegeId : String
    ) : SignUpScreenActionEvent()

    data class OnUserDepartmentChangeEvent(
        val departmentText : String?,
        val departmentId : String?,
        val isExpanded : Boolean
    ) : SignUpScreenActionEvent()

    data class OnUserCollegeYearChangeEvent(
        val yearText : String?,
        val yearId : String?,
        val isExpanded : Boolean
    ) : SignUpScreenActionEvent()

    data object OnRegisterBtnActionEvent : SignUpScreenActionEvent()

    data class OnDialogShowDismissEvent(
        val showHideDialog : Boolean
    ) : SignUpScreenActionEvent()

    data class OnProfileImageUri(
        val imageUri : Uri?
    ) : SignUpScreenActionEvent()

    data object OnDepartmentApiEvent : SignUpScreenActionEvent()

    data object OnMoveToDashBoardEvent : SignUpScreenActionEvent()

}