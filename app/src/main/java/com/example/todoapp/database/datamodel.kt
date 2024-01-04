package com.example.todoapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(tableName = "TaskList")
data class TaskDataModel(
    @PrimaryKey
    var id : Long = 0 ,
    var taskName : String ,
    var description : String ,
    var category : String,
    var date : LocalDate ,
    var time : LocalTime ,
    var action : Boolean
) {
    val localTime: LocalDateTime get() = run { LocalDateTime.of(date , time) }
}

@Entity(tableName = "category")
data class Category(
    @PrimaryKey
    val categoryName : String ,
    val categoryDescription : String
)

@Entity
data class UserInfo(
    @PrimaryKey
    @ColumnInfo(name = "Name")
    val name : String
)