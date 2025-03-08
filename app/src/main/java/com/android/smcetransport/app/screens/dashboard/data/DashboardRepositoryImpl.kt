package com.android.smcetransport.app.screens.dashboard.data

import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.model.PhoneNumberRequestModel
import com.android.smcetransport.app.core.network.ApiExecution
import com.android.smcetransport.app.core.network.ApiUrls
import com.android.smcetransport.app.core.network.ApiUrls.BASE_URL
import com.android.smcetransport.app.core.network.KtorHttpClient
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.android.smcetransport.app.screens.dashboard.domain.DashboardRepository
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.JsonObject

class DashboardRepositoryImpl(
    private val ktorHttpClient: KtorHttpClient,
    private val apiExecution: ApiExecution,
    private val sharedPrefs: SharedPrefs
) : DashboardRepository {


    @ExperimentalSerializationApi
    override suspend fun logoutUser(
        phoneNumberRequestModel: PhoneNumberRequestModel
    ): Flow<NetworkResult<BaseApiClass<JsonObject>>> {
        val loginUserTypeEnum = sharedPrefs.getLoginType()?.name?.length
        val apiUrl = "$BASE_URL/api/$loginUserTypeEnum/logout"
        val httpStatement = ktorHttpClient.httpClientAndroid().preparePost {
            url(apiUrl)
            setBody(phoneNumberRequestModel)
        }
        return apiExecution.executeApi<JsonObject>(httpStatement)
    }
}