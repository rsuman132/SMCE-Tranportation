package com.android.smcetransport.app.screens.department.data

import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.dto.DepartmentModel
import com.android.smcetransport.app.core.network.ApiExecution
import com.android.smcetransport.app.core.network.ApiUrls
import com.android.smcetransport.app.core.network.KtorHttpClient
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.screens.department.domain.DepartmentRepository
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

class DepartmentRepositoryImpl(
    private val ktorHttpClient: KtorHttpClient,
    private val apiExecution: ApiExecution
) : DepartmentRepository {


    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun createDepartment(
        departmentRequestModel: DepartmentRequestModel
    ): Flow<NetworkResult<BaseApiClass<DepartmentModel?>>> {
        val httpStatement = ktorHttpClient.httpClientAndroid().preparePost {
            url(ApiUrls.CREATE_DEPARTMENT)
            setBody(departmentRequestModel)
        }
        return apiExecution.executeApi<DepartmentModel?>(httpStatement)
    }


}