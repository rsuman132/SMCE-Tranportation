package com.android.smcetransport.app.core.network

object ApiUrls{

    const val BASE_URL = "http://192.168.1.46:8000"
    const val CREATE_DEPARTMENT = "$BASE_URL/api/department/create"
    const val CREATE_BUS = "$BASE_URL/api/bus/create"
    const val GET_ALL_BUS = "$BASE_URL/api/bus/getAll"
    const val GET_ALL_DEPARTMENT = "$BASE_URL/api/department/getAll"
    const val GET_BUS_REQUEST_BY_STATUS_STUDENT_ID =
        "$BASE_URL/api/studentbusrequest/getStudentBusRequestsByStatusAndStudentId"
    const val GET_BUS_REQUEST_BY_STATUS_STAFF_ID =
        "$BASE_URL/api/staffbusrequest/getStaffBusRequestsByStatusAndStaffId"
    const val GET_STUDENT_BUS_REQUEST_BY_STATUS =
        "$BASE_URL/api/studentbusrequest/getStudentBusRequestsByStatus"
    const val GET_STAFF_BUS_REQUEST_BY_STATUS =
        "$BASE_URL/api/staffbusrequest/getStaffBusRequestsByStatus"
    const val GET_ALL_STUDENT_BUS_REQUEST = "$BASE_URL/api/studentbusrequest/getAll"
    const val GET_ALL_STAFF_BUS_REQUEST = "$BASE_URL/api/staffbusrequest/getAll"

}