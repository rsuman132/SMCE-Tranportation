package com.android.smcetransport.app.screens.walkthrough

import com.android.smcetransport.app.core.enum.LoginUserTypeEnum

sealed class WalkThroughActionEvent {
    data object OnScreenInitEvent : WalkThroughActionEvent()
    data class OnLoginButtonClickEvent(
        val loginUserTypeEnum: LoginUserTypeEnum
    ) : WalkThroughActionEvent()
}