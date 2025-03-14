package com.android.smcetransport.app.utils

import android.content.Context
import android.widget.Toast
import com.android.smcetransport.app.R
import com.android.smcetransport.app.core.dto.BusRequestModel
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
        data: BusRequestModel?
    ): List<TitleDescModel> {
        return if (data != null) {
            val notSetText = resources.getString(R.string.not_set_text)
            val declareAmount = data.amount ?: 0.0
            val amountText = if (declareAmount > 0.0) {
                "$declareAmount"
            } else {
                notSetText
            }
            val requestStatusEnum =
                RequestStatusEnum.entries.find { it.name == data.status }
            val requestedStatusEnumToString =
                this.requestStatusEnumToString(requestStatusEnum)
            listOf(
                TitleDescModel(
                    id = "1",
                    title = resources.getString(R.string.name_text),
                    descText = "${data.requesterUserModel?.name}"
                ),
                TitleDescModel(
                    id = "2",
                    title = resources.getString(R.string.college_id),
                    descText = "${data.requesterUserModel?.collegeOrStaffId}"
                ),
                TitleDescModel(
                    id = "3",
                    title = resources.getString(R.string.phone_number_text),
                    descText = "${data.requesterUserModel?.phone}"
                ),
                TitleDescModel(
                    id = "4",
                    title = resources.getString(R.string.departments_text),
                    descText = "${data.requesterUserModel?.departmentModel?.departmentName}"
                ),
                TitleDescModel(
                    id = "5",
                    title = resources.getString(R.string.year_text),
                    descText = "${data.requesterUserModel?.year}"
                ),
                TitleDescModel(
                    id = "6",
                    title = resources.getString(R.string.bus_no_text),
                    descText = data.busAndRouteModel?.busNumber ?: notSetText
                ),
                TitleDescModel(
                    id = "7",
                    title = resources.getString(R.string.start_point_text),
                    descText = data.pickupPoint ?: notSetText
                ),
                TitleDescModel(
                    id = "8",
                    title = resources.getString(R.string.via_route_text),
                    descText = data.busAndRouteModel?.busRoute ?: notSetText
                ),
                TitleDescModel(
                    id = "9",
                    title = resources.getString(R.string.amount_text),
                    descText = amountText
                ),
                TitleDescModel(
                    id = "10",
                    title = resources.getString(R.string.status_text),
                    descText = requestedStatusEnumToString ?: notSetText
                )
            )
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