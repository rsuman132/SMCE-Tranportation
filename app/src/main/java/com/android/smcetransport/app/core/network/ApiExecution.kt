package com.android.smcetransport.app.core.network

import com.android.smcetransport.app.core.dto.BaseApiClass
import io.ktor.client.call.body
import io.ktor.client.statement.HttpStatement
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.ExperimentalSerializationApi

class ApiExecution {

    @OptIn(ExperimentalSerializationApi::class)
    inline fun <reified T : Any?> executeApi(
        httpStatement: HttpStatement
    ): Flow<NetworkResult<BaseApiClass<T>>> = flow {
        emit(NetworkResult.Loading())
        try {
            val httpResponse = httpStatement.execute()
            val responseData = httpResponse.body<BaseApiClass<T>>()
            val statusValue = httpResponse.status.value
            if (statusValue in 200..299) {
                emit(NetworkResult.Success(data = responseData))
            } else {
                val errorMessageException = if (!responseData.message.isNullOrEmpty()) {
                    Exception(responseData.message)
                } else {
                    getErrorFromStatusCode(statusValue)
                }
                emit(NetworkResult.Error(
                    message = errorMessageException.message,
                    data = null,
                    code = statusValue
                ))
            }
        } catch (e: Exception) {
            emit(NetworkResult.Error(message = e.message, null))
        }
    }

    fun getErrorFromStatusCode(statusCode : Int) : Exception {
        return when(statusCode) {
            401 -> Exception("Un Authorized")
            404 -> Exception("Not Found")
            in 500..599 -> Exception("Internal Server Error")
            else -> Exception("Something went wrong")
        }
    }

}