package com.example.todoapp.storage

import android.content.Context
import android.net.Uri
import androidx.core.net.toFile
import androidx.core.net.toUri
import java.io.File
import java.io.FileOutputStream

class storeFile {
    companion object{
        fun storeFile(uri : Uri , context : Context) {
            val file = File(
                context.filesDir ,
                "userProfile"
            )
            val inputStream = context.contentResolver.openInputStream(uri)
            inputStream.use { inputStream ->
                val outputStream = FileOutputStream(file)
                outputStream.write(inputStream?.readBytes())
                outputStream.close()
            }
            inputStream?.close()
        }

        fun getProfileImage(context: Context) : Uri {
            val file = File(
                context.filesDir,
                "userProfile"
            )
            return file.toUri()
        }
    }
}