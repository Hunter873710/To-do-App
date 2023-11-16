package com.example.todoapp.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.todoapp.R
import java.time.LocalDateTime
import java.time.ZoneId

class AlarmScheduler() {
    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("ScheduleExactAlarm")
        fun schedule(context: Context , time: LocalDateTime, taskName : String, taskDescription : String , taskId : Long) {
            val alarmManager : AlarmManager = context.getSystemService(AlarmManager::class.java)
            val intent = Intent(context , AlarmReceiver::class.java ).apply {
                this.putExtra(context.getString(R.string.notification_task_name) , taskName)
                this.putExtra(context.getString(R.string.notification_task_description) , taskDescription)
                this.putExtra(context.getString(R.string.task_id) , taskId)
            }
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP ,
                time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000 ,
                PendingIntent.getBroadcast(
                    context ,
                    111 ,
                    intent ,
                    PendingIntent.FLAG_MUTABLE
                )
            )
        }
    }
}