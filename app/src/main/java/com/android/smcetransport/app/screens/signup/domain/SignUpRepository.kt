package com.android.smcetransport.app.screens.signup.domain

import android.net.Uri
import com.android.smcetransport.app.core.dto.BaseApiClass
import com.android.smcetransport.app.core.dto.DepartmentModel
import com.android.smcetransport.app.core.dto.UserModel
import com.android.smcetransport.app.core.network.NetworkResult
import com.android.smcetransport.app.screens.signup.data.SignUpRequestModel
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.ExperimentalSerializationApi

interface SignUpRepository {

    suspend fun uploadProfileImage(imageUri : Uri?) : Flow<NetworkResult<String?>>

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun registerUser(
        signUpRequestModel: SignUpRequestModel
    ) : Flow<NetworkResult<BaseApiClass<UserModel>>>

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getAllDepartment() : Flow<NetworkResult<BaseApiClass<List<DepartmentModel>>>>

}
