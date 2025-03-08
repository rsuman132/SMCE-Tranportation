package com.android.smcetransport.app.screens.bus_managment.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.screens.bus_managment.domain.BusUseCase
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.ExperimentalSerializationApi

class BusListViewModel(
    private val busUseCase: BusUseCase
) : ViewModel() {

    var busListUIState : MutableStateFlow<BusListUIState> = MutableStateFlow(BusListUIState())
        private set

    private val _errorMessage = Channel<String?>()
    val errorMessage = _errorMessage.receiveAsFlow()


    init {
        getAllBusList()
    }

    @OptIn(ExperimentalSerializationApi::class)
    fun getAllBusList() {
        viewModelScope.launch(IO) {
            busUseCase.getAllBusList().collectLatest { networkResult ->
                when(networkResult) {
                    is NetworkResult.Error -> {
                        showLoading(false)
                        _errorMessage.send(networkResult.message)
                    }
                    is NetworkResult.Loading -> {
                        showLoading(true)
                    }
                    is NetworkResult.Success -> {
                        busListUIState.update {
                            it.copy(
                                isBusListGetApiLoading = false,
                                busList = networkResult.data?.data ?: listOf()
                            )
                        }
                    }
                }
            }
        }
    }


    private fun showLoading(show : Boolean) {
        busListUIState.update {
            it.copy(isBusListGetApiLoading = show)
        }
    }

}