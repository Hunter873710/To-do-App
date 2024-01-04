package com.example.todoapp.alarm

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.RingtoneManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.todoapp.AppDelegate
import com.example.todoapp.R
import com.example.todoapp.taskactivity.TaskActivity

class AlarmReceiver : BroadcastReceiver() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context?, intent: Intent?) {
        val taskName = intent?.getStringExtra(context?.getString(R.string.notification_task_name))
        val taskDes = intent?.getStringExtra(context?.getString(R.string.notification_task_description))
        val id = intent?.getLongExtra(context!!.getString(R.string.task_id) , 0)
        if (context != null) {
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
            val notification = NotificationCompat.Builder(context, AppDelegate.notificationChannel)
                .setContentTitle(taskName)
                .setSmallIcon(R.drawable.plus_icon)
                .setContentText(taskDes)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSound(alarmSound)
                .build()
            val taskIntent = Intent(context, TaskActivity::class.java)
            taskIntent.putExtra(context.getString(R.string.task_id) , id)
            notification.contentIntent = PendingIntent.getActivity(
                context ,
                1 ,
                taskIntent ,
                PendingIntent.FLAG_MUTABLE
            )
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                NotificationManagerCompat.from(context).notify(1 , notification)
            }
        }
    }
}