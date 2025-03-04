package com.android.smcetransport.app.screens.signup.utils

import android.content.Context
import java.io.File

object ContextExtension {

    fun Context.createImageFile() : File? {
        return try {
            val timeStamp = System.currentTimeMillis()
            val imageFileName = "JPEG_$timeStamp"
            val image = File.createTempFile(
                imageFileName,
                ".jpg",
                externalCacheDir
            )
            image
        } catch (e: Exception) {
            null
        }
    }

}