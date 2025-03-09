package com.android.smcetransport.app.screens.bus_request_status.data

import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.dto.BusRequestModel
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.core.network.ApiExecution
import com.android.smcetransport.app.core.network.ApiUrls
import com.android.smcetransport.app.core.network.ApiUrls.BASE_URL
import com.android.smcetransport.app.core.network.KtorHttpClient
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.screens.bus_request_status.domain.BusRequestStatusRepository
import com.android.smcetransport.app.screens.dashboard.data.CancelRequestRequestModel
import io.ktor.client.request.prepareGet
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonObject

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

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun requestCancelStatusChange(
        cancelRequestRequestModel: CancelRequestRequestModel,
        loginUserTypeEnum: LoginUserTypeEnum,
        id : String?
    ): Flow<NetworkResult<BaseApiClass<JsonObject>>> {
        val apiUrl = "$BASE_URL/api/${loginUserTypeEnum.name.lowercase()}busrequest/update/$id"
        val httpStatement = ktorHttpClient.httpClientAndroid().preparePost {
            url(apiUrl)
            setBody(cancelRequestRequestModel)
        }
        return apiExecution.executeApi<JsonObject>(httpStatement)
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getBusRequestById(
        id: String,
        loginUserTypeEnum: LoginUserTypeEnum
    ): Flow<NetworkResult<BaseApiClass<List<BusRequestModel>>>> {
        val httpStatement = ktorHttpClient.httpClientAndroid().prepareGet {
            val apiUrl = "${BASE_URL}/api/${loginUserTypeEnum.name.lowercase()}busrequest/getById/$id"
            url(apiUrl)
        }
        return apiExecution.executeApi<List<BusRequestModel>>(httpStatement)
    }

    @ExperimentalSerializationApi
    override suspend fun requestApproveStatusChange(
        busRequestApproveRequestModel: BusRequestApproveRequestModel,
        loginUserTypeEnum: LoginUserTypeEnum,
        id: String?
    ): Flow<NetworkResult<BaseApiClass<JsonObject>>> {
        val apiUrl = "$BASE_URL/api/${loginUserTypeEnum.name.lowercase()}busrequest/update/$id"
        val httpStatement = ktorHttpClient.httpClientAndroid().preparePost {
            url(apiUrl)
            setBody(busRequestApproveRequestModel)
        }
        return apiExecution.executeApi<JsonObject>(httpStatement)
    }

}