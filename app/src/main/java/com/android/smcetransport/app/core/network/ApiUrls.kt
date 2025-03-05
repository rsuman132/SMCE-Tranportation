package com.android.smcetransport.app.core.network

import com.android.smcetransport.app.core.shared_prefs.SharedPrefs

class ApiUrls(
    private val sharedPrefs: SharedPrefs
) {

    companion object {
        private const val BASE_URL = "http://192.168.43.245:8000"
    }

    private val loginUserTypeEnum get() =
        sharedPrefs.getLoginType()?.name?.lowercase()


    val userLoginUrl = "$BASE_URL/api/$loginUserTypeEnum/login"
    val userRegisterUrl = "$BASE_URL/api/$loginUserTypeEnum/register"
    val getAllDepartments = "$BASE_URL/api/department/getAll"
    val userLogoutUrl = "$BASE_URL/api/$loginUserTypeEnum/logout"





}