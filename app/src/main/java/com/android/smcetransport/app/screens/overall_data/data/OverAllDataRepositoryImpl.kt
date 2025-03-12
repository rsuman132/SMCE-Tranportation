package com.android.smcetransport.app.screens.overall_data.data

import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.dto.BusRequestModel
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.core.network.ApiExecution
import com.android.smcetransport.app.core.network.ApiUrls
import com.android.smcetransport.app.core.network.KtorHttpClient
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.screens.overall_data.domain.OverAllDataRepository
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

class OverAllDataRepositoryImpl(
    private val ktorHttpClient: KtorHttpClient,
    private val apiExecution: ApiExecution
) : OverAllDataRepository {

    @ExperimentalSerializationApi
    override suspend fun getAllBusRequestByStatusAndDepartment(
        overAllDataRequestModel: OverAllDataRequestModel,
        loginUserTypeEnum: LoginUserTypeEnum?
    ): Flow<NetworkResult<BaseApiClass<List<BusRequestModel>>>> {
        val apiUrl = if(loginUserTypeEnum == LoginUserTypeEnum.STUDENT) {
            ApiUrls.GET_STUDENT_BUS_REQUEST_BY_STATUS_DEPARTMENT
        } else {
            ApiUrls.GET_STAFF_BUS_REQUEST_BY_STATUS_DEPARTMENT
        }
        val httpStatement = ktorHttpClient.httpClientAndroid().preparePost {
            url(apiUrl)
            setBody(overAllDataRequestModel)
        }
        return apiExecution.executeApi<List<BusRequestModel>>(httpStatement)
    }

}