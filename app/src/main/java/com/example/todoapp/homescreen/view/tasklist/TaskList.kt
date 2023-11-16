package com.example.todoapp.homescreen.view.tasklist

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.R
import com.example.todoapp.database.TaskDataModel
import com.example.todoapp.ui.theme.background
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskList(
    title: String,
    model: List<TaskDataModel>,
    onBackPressed: () -> Unit,
    onDelete: (String) -> Unit,
    addTask: (String , String , LocalDate , LocalTime) -> Unit,
    updateTaskStatus : (Boolean , Long)-> Unit
) {
    var addTaskPopUp by remember {
        mutableStateOf(false)
    }
    Scaffold(
        topBar = {
            Row(modifier = Modifier.fillMaxWidth() ,
                verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.back_icon),
                    contentDescription = "Back Icon", modifier = Modifier
                        .padding(15.dp)
                        .size(30.dp)
                        .clickable {
                            onBackPressed()
                        }
                )
                Text(
                    text = title, modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis ,
                    textAlign = TextAlign.Center ,
                    fontSize = 20.sp,
                    maxLines = 1 ,
                    fontWeight = FontWeight.Bold)
                Icon(
                    painter = painterResource(id = R.drawable.delete_icon),
                    contentDescription = "More Button",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(30.dp)
                        .clickable {
                            onDelete(title)
                        }
                )
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = "More Button",
                    modifier = Modifier
                        .padding(15.dp)
                        .size(30.dp)
                        .clickable {
                            addTaskPopUp = true
                        }
                )
            }
        },
        containerColor = background ,
        contentColor = Color.White
    ) {
        Box(modifier = Modifier.padding(it)){
            LazyColumn(){
                items(model){taskModel ->
                    TaskCard(
                        taskModel,
                        onTaskStatus = { status ->
                            updateTaskStatus(status , taskModel.id)
                        }
                    )
                }
            }
        }
    }
    AnimatedVisibility(addTaskPopUp){
        AddTask(onComplete = { taskName, taskDes, localDate, localTime ->
            addTask(taskName, taskDes, localDate, localTime)
            addTaskPopUp = false
        }, onBackPressed = {
            addTaskPopUp = false
        })
    }
}


@Preview(showBackground = true)
@Composable
fun TaskList_Preview() {

}