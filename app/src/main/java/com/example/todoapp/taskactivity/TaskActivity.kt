package com.example.todoapp.taskactivity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.taskactivity.views.TaskScreen
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.ui.theme.background
import com.example.todoapp.R
import com.example.todoapp.taskactivity.presenter.TaskPresenter


class TaskActivity : ComponentActivity() {
    lateinit var presenter: TaskPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = this.intent?.extras?.getLong(this.getString(R.string.task_id) , 0 ) ?: 0
        presenter = ViewModelProvider(this , TaskPresenter.getViewModel(this,id))[TaskPresenter::class.java]
        setContent {
            ToDoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = background,
                    contentColor = Color.White
                ) {
                    TaskScreen(
                        title = presenter.taskModel?.taskName ?: "Title",
                        description = presenter.taskModel?.description ?: "Description",
                        onBackPressed = {
                            this.finish()
                        },
                        onDelete = {
                            presenter.deleteTask()
                            this.finish()
                        }
                    )
                }
            }
        }
    }
}