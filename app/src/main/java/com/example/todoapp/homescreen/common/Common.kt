package com.example.todoapp.homescreen.common

import android.content.Context
import android.content.Intent
import com.example.todoapp.taskactivity.TaskActivity
import java.time.format.DateTimeFormatter
import com.example.todoapp.R

val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("hh : mm a")

fun navigateTaskInfo(context: Context , taskId : Long) {
    val intent = Intent(context, TaskActivity::class.java)
    intent.putExtra(context.getString(R.string.task_id) , taskId)
    context.startActivity(intent)
}

