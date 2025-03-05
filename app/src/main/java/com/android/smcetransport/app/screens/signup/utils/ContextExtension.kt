package com.android.smcetransport.app.screens.signup.utils

import android.content.Context
import android.net.Uri
import android.webkit.MimeTypeMap
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


    fun Context.getMimeType(imageUri : Uri?) : String? {
        return try {
            if (imageUri == null) {
                throw Exception()
            }
            contentResolver.getType(imageUri)?.let { mimeType ->
                MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)
            } ?: throw Exception()
        } catch (e: Exception) {
            "png"
        }
    }

}