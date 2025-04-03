package com.android.smcetransport.app.screens.view_pass.presentation

import com.android.smcetransport.app.ui.components.model.TitleDescModel

data class ViewPassUiState(
    val isApiLoading: Boolean = false,
    val titleDescList : List<TitleDescModel> = listOf(),
    val errorText : String? = null,
    val userImage : String = "",
    val isPrintButtonLoading : Boolean = false
)
