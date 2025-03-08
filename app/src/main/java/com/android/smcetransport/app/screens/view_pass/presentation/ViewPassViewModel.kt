package com.android.smcetransport.app.screens.view_pass.presentation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smcetransport.app.core.enum.RequestStatusEnum
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.android.smcetransport.app.screens.view_pass.domain.ViewPassRepositoryUseCase
import com.android.smcetransport.app.utils.ContextExtension.getPassList
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

class ViewPassViewModel(
    private val viewPassRepositoryUseCase: ViewPassRepositoryUseCase,
    private val sharedPrefs: SharedPrefs,
    private val application: Application
) : ViewModel() {

    var viewPassUiState : MutableStateFlow<ViewPassUiState> = MutableStateFlow(ViewPassUiState())
        private set

    init {
        getPassById()
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun getPassById() {
        viewModelScope.launch(IO) {
            val passId = sharedPrefs.getRequestPassModelList()?.find {
                (it.status == RequestStatusEnum.REQUESTED.name || it.status == RequestStatusEnum.ACCEPTED.name)
            }?.id
            viewPassRepositoryUseCase.getBusRequestById(passId).collectLatest { networkResult ->
                when(networkResult) {
                    is NetworkResult.Error -> {
                        viewPassUiState.update {
                            it.copy(isApiLoading = false, errorText = networkResult.message)
                        }
                    }
                    is NetworkResult.Loading -> {
                        viewPassUiState.update {
                            it.copy(isApiLoading = true)
                        }
                    }
                    is NetworkResult.Success -> {
                        val requestData = networkResult.data?.data?.firstOrNull()
                        val titleDescList =
                            application.baseContext.getPassList(data = requestData)
                        viewPassUiState.update {
                            it.copy(
                                isApiLoading = false,
                                titleDescList = titleDescList,
                                userImage = requestData?.requesterUserModel?.imageUrl ?: ""
                            )
                        }
                    }
                }
            }
        }
    }

}