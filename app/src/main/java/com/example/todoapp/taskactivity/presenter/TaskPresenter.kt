package com.example.todoapp.taskactivity.presenter

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.todoapp.database.TaskDataModel
import com.example.todoapp.database.TaskDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskPresenter(val database : TaskDatabase ,val taskId : Long) : ViewModel() {
    var taskModel by mutableStateOf<TaskDataModel?>(null)

    companion object {
        fun getViewModel(context : Context, taskId: Long) : ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return TaskPresenter(TaskDatabase.getInstance(context) , taskId) as T
            }
        }
    }

    init {
        viewModelScope.launch(Dispatchers.IO) {
            taskModel = database.getTaskDao().getTask(taskId = taskId)
        }
    }

    fun deleteTask() {
        viewModelScope.launch(Dispatchers.IO) {
            database.getTaskDao().deleteTask(taskId = taskId)
        }
    }
}