package com.example.todoapp.alarm

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.todoapp.R
import com.example.todoapp.database.TaskDataModel
import java.time.LocalDateTime
import java.time.ZoneId

class AlarmScheduler() {
    companion object {
        @SuppressLint("ScheduleExactAlarm")
        fun schedule(context: Context , taskModel: TaskDataModel) {
            val alarmManager : AlarmManager = context.getSystemService(AlarmManager::class.java)
            val intent = Intent(context , AlarmReceiver::class.java ).apply {
                this.putExtra(context.getString(R.string.notification_task_name) , taskModel.taskName)
                this.putExtra(context.getString(R.string.notification_task_description) , taskModel.description)
                this.putExtra(context.getString(R.string.task_id) , taskModel.id)
            }
            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP ,
                taskModel.localTime.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000 ,
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