package com.android.smcetransport.app.screens.splash.data

import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.dto.UserModel
import com.android.smcetransport.app.core.model.PhoneNumberRequestModel
import com.android.smcetransport.app.core.network.ApiExecution
import com.android.smcetransport.app.core.network.ApiUrls
import com.android.smcetransport.app.core.network.KtorHttpClient
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.android.smcetransport.app.screens.splash.domain.SplashRepository
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

class SplashRepositoryImpl(
    private val sharedPrefs: SharedPrefs,
    private val ktorHttpClient: KtorHttpClient,
    private val apiExecution: ApiExecution
) : SplashRepository {


    @ExperimentalSerializationApi
    override suspend fun getUserProfile(
        phoneNumberRequestModel: PhoneNumberRequestModel
    ): Flow<NetworkResult<BaseApiClass<UserModel>>> {
        val loginTypeEnum = sharedPrefs.getLoginType()?.name?.lowercase()
        val apiUrl = "${ApiUrls.BASE_URL}/api/$loginTypeEnum/login"
        val httpStatement = ktorHttpClient.httpClientAndroid().preparePost {
            url(apiUrl)
            setBody(phoneNumberRequestModel)
        }
        return apiExecution.executeApi<UserModel>(httpStatement)
    }


}