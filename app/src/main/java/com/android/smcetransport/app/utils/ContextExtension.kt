package com.android.smcetransport.app.utils

import android.content.Context
import android.widget.Toast
import com.android.smcetransport.app.R
import com.android.smcetransport.app.core.dto.BusRequestModel
import com.android.smcetransport.app.core.enum.LoginUserTypeEnum
import com.android.smcetransport.app.core.enum.RequestStatusEnum
import com.android.smcetransport.app.ui.components.model.TitleDescModel

object ContextExtension {

    fun Context?.showToast(message : String?) {
        if (this == null && message.isNullOrBlank()) {
            return
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }



    fun Context.getPassList(
        data: BusRequestModel?,
        loginUserTypeEnum: LoginUserTypeEnum?
    ): List<TitleDescModel> {
        return if (data != null) {
            val notSetText = resources.getString(R.string.not_set_text)
            val declareAmount = data.amount ?: 0.0
            val amountText = if (declareAmount > 0.0) {
                "$declareAmount"
            } else {
                notSetText
            }
            val nameModel = TitleDescModel(
                id = "1",
                title = resources.getString(R.string.name_text),
                descText = "${data.requesterUserModel?.name}"
            )
            val collegeIdModel = TitleDescModel(
                id = "2",
                title = resources.getString(R.string.college_id),
                descText = "${data.requesterUserModel?.collegeOrStaffId}"
            )
            val departmentModel = TitleDescModel(
                id = "4",
                title = resources.getString(R.string.departments_text),
                descText = "${data.requesterUserModel?.departmentModel?.departmentName}"
            )
            val yearModel = TitleDescModel(
                id = "5",
                title = resources.getString(R.string.year_text),
                descText = "${data.requesterUserModel?.year}"
            )
            val busNoModel = TitleDescModel(
                id = "6",
                title = resources.getString(R.string.bus_no_text),
                descText = data.busAndRouteModel?.busNumber ?: notSetText
            )
            val startPointModel = TitleDescModel(
                id = "7",
                title = resources.getString(R.string.start_point_text),
                descText = data.pickupPoint ?: notSetText
            )
            val viaRouteModel = TitleDescModel(
                id = "8",
                title = resources.getString(R.string.via_route_text),
                descText = data.busAndRouteModel?.busRoute ?: notSetText
            )
            val amountModel = TitleDescModel(
                id = "9",
                title = resources.getString(R.string.amount_text),
                descText = amountText
            )
            if (loginUserTypeEnum == LoginUserTypeEnum.STUDENT) {
                listOf(
                    busNoModel,
                    nameModel,
                    departmentModel,
                    collegeIdModel,
                    yearModel,
                    startPointModel,
                    viaRouteModel,
                    amountModel
                )
            } else {
                listOf(
                    busNoModel,
                    nameModel,
                    departmentModel,
                    yearModel,
                    startPointModel,
                    viaRouteModel,
                    amountModel
                )
            }
        } else {
            listOf()
        }
    }


    fun Context.requestStatusEnumToString(
        requestStatusEnum: RequestStatusEnum?
    ) : String? {
        if (requestStatusEnum == null) {
            return null
        }
        return when(requestStatusEnum) {
            RequestStatusEnum.REQUESTED -> resources.getString(R.string.requested_text)
            RequestStatusEnum.ACCEPTED -> resources.getString(R.string.accepted_text)
            RequestStatusEnum.CANCELLED -> resources.getString(R.string.cancelled_text)
        }
    }

}