package com.example.todoapp

import android.content.Context
import com.example.todoapp.database.CategoryDao
import com.example.todoapp.database.TaskDAO
import com.example.todoapp.database.TaskDatabase
import com.example.todoapp.database.UserInfoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltModule {

    @Singleton
    @Provides
    fun bindsTaskDatabase(@ApplicationContext context : Context) : TaskDatabase {
        return TaskDatabase.getInstance(context = context)
    }

    @Singleton
    @Provides
    fun bindTaskDAO(taskDatabase: TaskDatabase) : TaskDAO = taskDatabase.getTaskDao()

    @Singleton
    @Provides
    fun bindCategoryDAO(taskDatabase: TaskDatabase) : CategoryDao = taskDatabase.getCategoryDao()

    @Singleton
    @Provides
    fun bindUserInfoDAO(taskDatabase: TaskDatabase) : UserInfoDao = taskDatabase.getUserInfoDao()

}