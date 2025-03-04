package com.android.smcetransport.app.utils

import android.content.Context
import android.widget.Toast

object ContextExtension {

    fun Context?.showToast(message : String?) {
        if (this == null && message.isNullOrBlank()) {
            return
        }
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}