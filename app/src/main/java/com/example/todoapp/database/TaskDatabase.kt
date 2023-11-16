package com.example.todoapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


@Database(entities = [TaskDataModel::class , Category::class , UserInfo::class], version = 8 , exportSchema = false)
@TypeConverters(*[DateTypeConverter::class,TimeTypeConverter::class])
abstract class TaskDatabase : RoomDatabase() {
    companion object {
        private const val DB_NAME = "task_database"
        private var instance : TaskDatabase ?= null
        fun getInstance(context : Context) : TaskDatabase {
            if(instance == null){
                instance = databaseBuilder(context.applicationContext , TaskDatabase::class.java , DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }

    abstract fun getTaskDao() : TaskDAO

    abstract fun getCategoryDao() : CategoryDao

    abstract fun getUserInfo() : UserInfoDao
}