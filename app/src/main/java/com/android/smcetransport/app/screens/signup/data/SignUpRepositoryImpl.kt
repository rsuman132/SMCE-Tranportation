package com.android.smcetransport.app.screens.signup.data

import android.content.Context
import android.net.Uri
import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.dto.DepartmentModel
import com.android.smcetransport.app.core.dto.UserModel
import com.android.smcetransport.app.core.network.ApiExecution
import com.android.smcetransport.app.core.network.ApiUrls
import com.android.smcetransport.app.core.network.KtorHttpClient
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.core.shared_prefs.SharedPrefs
import com.android.smcetransport.app.screens.signup.domain.SignUpRepository
import com.android.smcetransport.app.screens.signup.utils.ContextExtension.getMimeType
import com.google.firebase.storage.FirebaseStorage
import io.ktor.client.request.prepareGet
import io.ktor.client.request.preparePost
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.ExperimentalSerializationApi

class SignUpRepositoryImpl(
    private val sharedPrefs: SharedPrefs,
    private val ktorHttpClient : KtorHttpClient,
    private val apiUrls: ApiUrls,
    private val apiExecution: ApiExecution,
    private val context : Context
) : SignUpRepository {

    private val firebaseStorage = FirebaseStorage.getInstance()

    override suspend fun uploadProfileImage(imageUri : Uri?): Flow<NetworkResult<String?>> = flow {
        emit(NetworkResult.Loading())
        try {
            if (imageUri != null) {
                val loginUserTypeEnum = sharedPrefs.getLoginType()?.name?.lowercase()
                val mimeType = context.getMimeType(imageUri)
                val path = "profileImg/$loginUserTypeEnum/${System.currentTimeMillis()}.$mimeType"
                val storageReference = firebaseStorage.reference
                    .child(path)
                val uploadTask = storageReference.putFile(imageUri).await().task
                if (uploadTask.isSuccessful) {
                    val downloadUrl = storageReference.downloadUrl.await()
                    emit(NetworkResult.Success(downloadUrl?.toString()))
                }
            } else {
                emit(NetworkResult.Error(message = "Invalid image. Please upload a valid image", data = null))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(message = e.message, data = null))
        }
    }

    @ExperimentalSerializationApi
    override suspend fun registerUser(
        signUpRequestModel: SignUpRequestModel
    ): Flow<NetworkResult<BaseApiClass<UserModel>>> {
        val httpStatement = ktorHttpClient.httpClientAndroid().preparePost {
            url(apiUrls.userRegisterUrl)
            setBody(signUpRequestModel)
        }
        return apiExecution.executeApi<UserModel>(httpStatement)
    }

    @ExperimentalSerializationApi
    override suspend fun getAllDepartment(): Flow<NetworkResult<BaseApiClass<List<DepartmentModel>>>> {
        val httpStatement = ktorHttpClient.httpClientAndroid().prepareGet {
            url(apiUrls.getAllDepartments)
        }
        return apiExecution.executeApi<List<DepartmentModel>>(httpStatement)
    }

}