package com.android.smcetransport.app.core.shared_prefs

import android.content.Context
import android.content.SharedPreferences
import com.android.smcetransport.app.core.dto.BusRequestModel
import com.android.smcetransport.app.core.dto.UserModel
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.core.enum.RequestStatusEnum
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

class SharedPrefs(context : Context) {

    companion object {
        private const val SHARED_PREFS_NAME = "smce_preferences"
        private const val LOGIN_USER_TYPE_KEY = "login_user_type"
        private const val USER_MODEL_KEY = "user_model_type"
        private const val REQUESTING_PASS_LIST_KEY = "requesting_pass_list"
        private const val REQUESTING_STATUS_KEY = "requesting_status_key"
    }

    private val json = Json {
        prettyPrint = true
        isLenient = true
        useAlternativeNames = true
        ignoreUnknownKeys = true
        encodeDefaults = false
    }

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        SHARED_PREFS_NAME,
        Context.MODE_PRIVATE
    )
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    fun setLoginType(loginUserTypeEnum: LoginUserTypeEnum?) {
        editor.apply {
            putString(LOGIN_USER_TYPE_KEY, loginUserTypeEnum?.name)
            apply()
        }
    }

    fun getLoginType() : LoginUserTypeEnum? {
        val loginTypeString = sharedPreferences.getString(LOGIN_USER_TYPE_KEY, null)
        return LoginUserTypeEnum.entries.find { it.name == loginTypeString }
    }


    fun setUserModel(userModel: UserModel?) {
        val userModelString = if(userModel != null) {
            json.encodeToString(
                value = userModel,
                serializer = serializer()
            )
        } else {
            null
        }
        editor.apply {
            putString(USER_MODEL_KEY, userModelString)
            apply()
        }
    }


    fun getUserModel() : UserModel? {
        val userModelString = sharedPreferences.getString(USER_MODEL_KEY, null)
        return if (userModelString != null) {
            json.decodeFromString<UserModel?>(userModelString)
        } else {
            null
        }
    }


    fun setRequestPassModelList(
        requestedPassList : List<BusRequestModel>?
    ) {
        val requestedPassListString = if(!requestedPassList.isNullOrEmpty()) {
            json.encodeToString(
                value = requestedPassList,
                serializer = serializer()
            )
        } else {
            null
        }
        editor.apply {
            putString(REQUESTING_PASS_LIST_KEY, requestedPassListString)
            apply()
        }
    }

    fun getRequestPassModelList() : List<BusRequestModel>? {
        val requestedPassListString = sharedPreferences.getString(REQUESTING_PASS_LIST_KEY, null)
        return if (requestedPassListString != null) {
            json.decodeFromString<List<BusRequestModel>?>(requestedPassListString)
        } else {
            null
        }
    }


    fun setRequestStateType(requestStatusEnum: RequestStatusEnum?) {
        editor.apply {
            putString(REQUESTING_STATUS_KEY, requestStatusEnum?.name)
            apply()
        }
    }

    fun getRequestStateType() : RequestStatusEnum? {
        val requestStatusEnum = sharedPreferences.getString(REQUESTING_STATUS_KEY, null)
        return RequestStatusEnum.entries.find { it.name == requestStatusEnum }
    }

}