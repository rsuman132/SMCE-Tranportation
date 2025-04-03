package com.android.smcetransport.app.screens.view_pass.presentation.utils

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.Picture
import android.graphics.pdf.PdfDocument
import androidx.core.content.FileProvider
import androidx.core.graphics.createBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

object Extensions {

    fun Picture.createBitmapFromPicture(): Bitmap? {
        return try {
            val bitmap = createBitmap(this.width, this.height)
            val canvas = android.graphics.Canvas(bitmap)
            canvas.drawColor(android.graphics.Color.WHITE)
            canvas.drawPicture(this)
            bitmap
        } catch (e: Exception) {
            null
        }
    }


    suspend fun Context.convertBitmapToFile(bitmap : Bitmap) : File? {
        return withContext(Dispatchers.IO) {
            try {
                val pdfDocument = PdfDocument()
                val pageWidth = 595  // A4 width in points
                val pageHeight = 842 // A4 height in points

                val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
                val page = pdfDocument.startPage(pageInfo)
                val canvas = page.canvas

                // Scale and center the bitmap
                val scale = minOf(
                    pageWidth.toFloat() / bitmap.width,
                    pageHeight.toFloat() / bitmap.height
                )
                val scaledWidth = (bitmap.width * scale).toInt()
                val scaledHeight = (bitmap.height * scale).toInt()
                val left = (pageWidth - scaledWidth) / 2f
                val top = (pageHeight - scaledHeight) / 2f

                val matrix = Matrix().apply {
                    postScale(scale, scale)
                    postTranslate(left, top)
                }
                val paint = Paint(Paint.ANTI_ALIAS_FLAG)
                canvas.drawBitmap(bitmap, matrix, paint)
                pdfDocument.finishPage(page)
                val pdfFile = File(this@convertBitmapToFile.cacheDir, "BUS_PASS_${System.currentTimeMillis()}.pdf")
                FileOutputStream(pdfFile).use { outputStream ->
                    pdfDocument.writeTo(outputStream)
                }
                pdfDocument.close()
                pdfFile
            } catch (e: Exception) {
                null
            }
        }
    }


    fun Context.openPdfFile(pdfFile: File) {
        try {
            val uri = FileProvider.getUriForFile(this, "${packageName}.provider", pdfFile)
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            }
            this.startActivity(Intent.createChooser(intent, "Open PDF"))
        } catch (e: Exception) {
            println(e.toString())
        }
    }
}