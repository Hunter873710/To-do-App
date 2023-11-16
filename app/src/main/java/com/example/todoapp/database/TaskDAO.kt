package com.example.todoapp.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface TaskDAO {
    @Query("SELECT DISTINCT category FROM TaskList")
    fun getCategoryList(): MutableList<String>

    @Insert
    fun insertTask(model : TaskDataModel)

    @Query("SELECT * FROM TaskList WHERE category = :category")
    fun getTaskList(category : String) : Flow<List<TaskDataModel>>

    @Query("SELECT * FROM TaskList WHERE date = :date")
    fun getTaskList(date : LocalDate) : Flow<List<TaskDataModel>>

    @Query("SELECT * FROM TaskList")
    fun getTaskList() : Flow<List<TaskDataModel>>

    @Query("DELETE FROM TaskList WHERE category = :categoryName")
    fun deleteCategory(categoryName: String)

    @Query("SELECT * FROM TaskList WHERE id = :taskId")
    fun getTask(taskId : Long) : TaskDataModel

    @Query("DELETE FROM TaskList WHERE id = :taskId")
    fun deleteTask(taskId: Long)

    @Query("SELECT id FROM TaskList WHERE taskName == :taskName AND description = :description")
    fun getId (taskName : String , description : String) : Long

    @Query("UPDATE TaskList SET `action` = :action WHERE id = :taskId")
    fun updateTaskStatus(action : Boolean , taskId : Long)
}

@Dao
interface CategoryDao {
    @Query("SELECT * FROM category")
    fun getCategoryList() : Flow<List<Category>>

    @Insert
    fun addCategory(category : Category)

    @Delete
    fun deleteCategory(categoryList: Category)

    @Query("DELETE FROM category WHERE categoryName = :categoryName")
    fun deleteCategory(categoryName: String)
}

@Dao
interface UserInfoDao {
    @Query("SELECT Name FROM UserInfo")
    fun getUserInfo() : String

    @Insert
    fun insertUserInfo(userInfo : UserInfo)
}
