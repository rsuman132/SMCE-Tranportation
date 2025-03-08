package com.android.smcetransport.app.screens.view_pass.data

import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.dto.RequestPassModel
import com.android.smcetransport.app.core.network.ApiExecution
import com.android.smcetransport.app.core.network.ApiUrls.BASE_URL
import com.android.smcetransport.app.core.network.KtorHttpClient
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.android.smcetransport.app.screens.view_pass.domain.ViewPassRepository
import io.ktor.client.request.prepareGet
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

class ViewPassRepositoryImpl(
    private val ktorHttpClient: KtorHttpClient,
    private val apiExecution: ApiExecution,
    private val sharedPrefs: SharedPrefs
) : ViewPassRepository {

    @ExperimentalSerializationApi
    override suspend fun getBusRequestById(
        passId : String?
    ): Flow<NetworkResult<BaseApiClass<List<RequestPassModel>>>> {
        val loginUserTypeEnum = sharedPrefs.getLoginType()?.name?.lowercase()
        val apiUrl = "$BASE_URL/api/${loginUserTypeEnum}busrequest/getById/$passId"
        val httpStatement = ktorHttpClient.httpClientAndroid().prepareGet {
            url(apiUrl)
        }
        return apiExecution.executeApi<List<RequestPassModel>>(httpStatement)
    }
}