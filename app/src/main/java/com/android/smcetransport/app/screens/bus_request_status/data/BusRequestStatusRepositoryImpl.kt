package com.android.smcetransport.app.screens.bus_request_status.data

import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.dto.BusRequestModel
import com.android.smcetransport.app.core.network.ApiExecution
import com.android.smcetransport.app.core.network.ApiUrls
import com.android.smcetransport.app.core.network.KtorHttpClient
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.screens.bus_request_status.domain.BusRequestStatusRepository
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

class BusRequestStatusRepositoryImpl(
    private val ktorHttpClient: KtorHttpClient,
    private val apiExecution: ApiExecution
) : BusRequestStatusRepository {

    @ExperimentalSerializationApi
    override suspend fun getStudentBusRequestByStatus(
        busRequestStatusRequestModel: BusRequestStatusRequestModel
    ): Flow<NetworkResult<BaseApiClass<List<BusRequestModel>>>> {
        val httpStatement = ktorHttpClient.httpClientAndroid().preparePost {
            url(ApiUrls.GET_STUDENT_BUS_REQUEST_BY_STATUS)
            setBody(busRequestStatusRequestModel)
        }
        return apiExecution.executeApi<List<BusRequestModel>>(httpStatement)
    }

    @ExperimentalSerializationApi
    override suspend fun getStaffBusRequestByStatus(
        busRequestStatusRequestModel: BusRequestStatusRequestModel
    ): Flow<NetworkResult<BaseApiClass<List<BusRequestModel>>>> {
        val httpStatement = ktorHttpClient.httpClientAndroid().preparePost {
            url(ApiUrls.GET_STAFF_BUS_REQUEST_BY_STATUS)
            setBody(busRequestStatusRequestModel)
        }
        return apiExecution.executeApi<List<BusRequestModel>>(httpStatement)
    }

}