package com.example.todoapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.core.app.ActivityCompat

class AppDelegate : Application() {
    companion object {
        const val notificationChannel = "ToDoAppChannel"
    }
    override fun onCreate() {
        super.onCreate()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannel ,
                "TodoNotification",
                NotificationManager.IMPORTANCE_HIGH
            )
            applicationContext.getSystemService(NotificationManager::class.java)
                .createNotificationChannel(channel)
        }
    }
}