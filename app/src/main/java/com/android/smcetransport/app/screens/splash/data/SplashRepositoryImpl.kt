package com.android.smcetransport.app.screens.splash.data

import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.dto.UserModel
import com.android.smcetransport.app.core.network.ApiExecution
import com.android.smcetransport.app.core.network.ApiUrls
import com.android.smcetransport.app.core.network.KtorHttpClient
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.screens.splash.domain.SplashRepository
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

class SplashRepositoryImpl(
    private val apiUrls: ApiUrls,
    private val ktorHttpClient: KtorHttpClient,
    private val apiExecution: ApiExecution
) : SplashRepository {

    @ExperimentalSerializationApi
    override suspend fun getUserProfile(
        splashRequestModel: SplashRequestModel
    ): Flow<NetworkResult<BaseApiClass<UserModel>>> {
        val httpStatement = ktorHttpClient.httpClientAndroid().preparePost {
            url(apiUrls.userLoginUrl)
            setBody(splashRequestModel)
        }
        return apiExecution.executeApi<UserModel>(httpStatement)
    }


}