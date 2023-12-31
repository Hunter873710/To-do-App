package com.example.todoapp.homescreen.presenter

import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.alarm.AlarmScheduler
import com.example.todoapp.database.Category
import com.example.todoapp.database.CategoryDao
import com.example.todoapp.database.TaskDAO
import com.example.todoapp.database.TaskDataModel
import com.example.todoapp.database.UserInfo
import com.example.todoapp.database.UserInfoDao
import com.example.todoapp.storage.storeFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenPresenter @Inject constructor(
    var taskDAO: TaskDAO ,
    var categoryDAO: CategoryDao ,
    var userInfoDao: UserInfoDao
) : ViewModel() {
    var categoryList by mutableStateOf(mutableListOf<Category>())
    var taskList by mutableStateOf(mutableListOf<TaskDataModel>())
    var profileName : String? by mutableStateOf(null)
    var profileUri : Uri? by mutableStateOf(null)
    var taskProgress by mutableStateOf(1 to 1)
    var todayTaskList : List<TaskDataModel> by mutableStateOf(listOf())
    var taskList_Product : Job? = null

    init {
        getUserInfo()
        getCategoryTaskList()
        getTodayTaskList()
    }

    fun getTodayTaskList() {
        viewModelScope.launch(Dispatchers.IO) {
            taskDAO.getTaskList().collect(){ taskList ->
                todayTaskList = taskList
                taskProgress = todayTaskList.count { task->
                    task.action
                } to taskList.count()
            }
        }
    }

    fun getCategoryTaskList() {
        viewModelScope.launch(Dispatchers.IO) {
            categoryDAO.getCategoryList().collect(){
                categoryList = it.toMutableList()
            }
        }
    }

    fun getUserInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            profileName = userInfoDao.getUserInfo()
        }
    }

    fun updateProfilePic(context: Context) {
        viewModelScope.launch {
            profileUri = storeFile.getProfileImage(context)
        }
    }

    fun taskUpdate(status : Boolean , id : Long) {
        viewModelScope.launch(Dispatchers.IO){
            taskDAO.updateTaskStatus(status , id)
        }
    }

    fun insertUserInfo(name : String , uri : Uri? , context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            userInfoDao.insertUserInfo(UserInfo(name = name))
            getUserInfo()
            uri?.let {  uri ->
                storeFile.storeFile(
                    uri = uri ,
                    context = context
                )
            }
        }
    }

    fun addCategoryList(category: Category) {
        viewModelScope.launch(Dispatchers.IO){
            categoryDAO.addCategory(category)
        }
    }

    fun deleteCategory(categoryName : String) {
        viewModelScope.launch(Dispatchers.IO){
            categoryDAO.deleteCategory(categoryName)
            taskDAO.deleteCategory(categoryName)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertTask(model: TaskDataModel, context: Context) {
        viewModelScope.launch(Dispatchers.IO){
            taskDAO.insertTask(model)
            AlarmScheduler.schedule(
                context ,
                model
            )
        }
    }

    fun getCategoryTaskList(category : String) {
        taskList_Product = viewModelScope.launch(Dispatchers.IO) {
            taskDAO.getTaskList(category).collect(){
                taskList = it.toMutableList()
            }
        }
        taskList_Product?.start()
    }

    fun cancelTaskListUpdate() {
        taskList_Product?.cancel()
    }
}