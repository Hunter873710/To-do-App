package com.example.todoapp.homescreen.view

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todoapp.database.Category
import com.example.todoapp.database.TaskDataModel
import com.example.todoapp.homescreen.presenter.HomeScreenPresenter
import com.example.todoapp.homescreen.view.tasklist.TaskList

enum class ToDoApp {
    TASK_LIST , HOME_SCREEN , USER_INFO
}

@Composable
fun HomeScreenNavigation(presenter : HomeScreenPresenter) {
    val context = LocalContext.current
    val navController = rememberNavController()
    val initScreen = if(presenter.profileName == null)
        ToDoApp.USER_INFO
    else
        ToDoApp.HOME_SCREEN
    NavHost(navController = navController, startDestination = initScreen.name) {
        composable(ToDoApp.HOME_SCREEN.name) {
            HomeScreen(
                profileName = presenter.profileName ?: "Folks" ,
                profilePic = presenter.profileUri ,
                taskProgress = presenter.taskProgress,
                todayTaskDataModel = presenter.todayTaskList ,
                presenter.categoryList , onCategoryClicked = {
                navController.navigate("${ToDoApp.TASK_LIST.name}/${it.categoryName}")
            } ,  onAddClicked = { name , des ->
                    presenter.addCategoryList(
                        Category( name , des)
                    )
            } , updateTaskStatus = { status , id ->
                presenter.taskUpdate(status , id)
            })
        }

        composable("${ToDoApp.TASK_LIST.name}/{category}") {
            var title = ""
            it.arguments?.getString("category")?.let { category ->
                presenter.getCategoryTaskList(category)
                title = category
            }
            TaskList(title , presenter.taskList , onBackPressed = {
                navController.popBackStack()
                presenter.cancelTaskListUpdate()
            } , onDelete = {
                presenter.deleteCategory(it)
                navController.popBackStack()
            } , addTask = { taskName , taskDes , date , time->
                presenter.insertTask(TaskDataModel(
                    taskName = taskName ,
                    description = taskDes ,
                    date = date,
                    time = time ,
                    category = title,
                    action = false
                ) , context)
            } , updateTaskStatus = { status , id ->
                presenter.taskUpdate(status , id )
            })
        }

        composable(ToDoApp.USER_INFO.name){
            WelcomePage(onSave = { name , uri ->
                if(name.isEmpty()) {
                    Toast.makeText(context, "Please Enter Valid Name", Toast.LENGTH_LONG).show()
                } else {
                    presenter.insertUserInfo(name, uri, context)
                    navController.popBackStack()
                    navController.navigate(ToDoApp.HOME_SCREEN.name)
                }
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenNavigation_Preview() {
//    HomeScreenNavigation()
}