package com.android.smcetransport.app.screens.signup.domain

import android.net.Uri
import com.android.smcetransport.app.screens.signup.data.SignUpRequestModel
import kotlinx.serialization.ExperimentalSerializationApi

class SignUpUseCase(
    private val signUpRepository: SignUpRepository
) {

    suspend fun uploadProfileImage(imageUri : Uri?) =
        signUpRepository.uploadProfileImage(imageUri)

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun registerUser(
        signUpRequestModel: SignUpRequestModel
    ) = signUpRepository.registerUser(signUpRequestModel)

    @OptIn(ExperimentalSerializationApi::class)
    suspend fun getAllDepartment() = signUpRepository.getAllDepartment()

}