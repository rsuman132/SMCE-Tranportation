package com.android.smcetransport.app.screens.bus_managment.data

import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.dto.BusAndRouteModel
import com.android.smcetransport.app.core.network.ApiExecution
import com.android.smcetransport.app.core.network.ApiUrls
import com.android.smcetransport.app.core.network.KtorHttpClient
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.android.smcetransport.app.screens.bus_managment.domain.BusRepository
import io.ktor.client.request.prepareGet
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

class BusRepositoryImpl(
    private val ktorHttpClient: KtorHttpClient,
    private val apiExecution: ApiExecution
) : BusRepository {


    @ExperimentalSerializationApi
    override suspend fun getAllBusList(): Flow<NetworkResult<BaseApiClass<List<BusAndRouteModel>?>>> {
        val httpStatement = ktorHttpClient.httpClientAndroid().prepareGet {
            url(ApiUrls.GET_ALL_BUS)
        }
        return apiExecution.executeApi<List<BusAndRouteModel>?>(httpStatement)
    }

    @ExperimentalSerializationApi
    override suspend fun createBus(
        createBusRequestModel: CreateBusRequestModel
    ): Flow<NetworkResult<BaseApiClass<BusAndRouteModel?>>> {
        val httpStatement = ktorHttpClient.httpClientAndroid().preparePost {
            url(ApiUrls.CREATE_BUS)
            setBody(createBusRequestModel)
        }
        return apiExecution.executeApi<BusAndRouteModel?>(httpStatement)
    }
}