package com.android.smcetransport.app.core.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import kotlinx.serialization.json.Json

class KtorHttpClient {

    private val s : String = KtorHttpClient::class.java.simpleName

    private val json = Json {
        prettyPrint = true
        isLenient = true
        useAlternativeNames = true
        ignoreUnknownKeys = true
        encodeDefaults = false
    }

    companion object {
        private const val NETWORK_TIME_OUT = 15000L
    }

    fun httpClientAndroid() = HttpClient(Android) {
        install(ContentNegotiation) {
            json(json)
        }
        install(HttpTimeout) {
            requestTimeoutMillis = NETWORK_TIME_OUT
            connectTimeoutMillis = NETWORK_TIME_OUT
            socketTimeoutMillis = NETWORK_TIME_OUT
        }
        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.d(s, message)
                }
            }
            level = LogLevel.ALL
        }
        install(DefaultRequest) {
            header(key = HttpHeaders.ContentType, value = ContentType.Application.Json)
            header(key = HttpHeaders.Accept, value = ContentType.Application.Json)
        }
    }

}