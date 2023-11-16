package com.example.todoapp.homescreen.view

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.todoapp.R
import com.example.todoapp.database.Category
import com.example.todoapp.database.TaskDataModel
import com.example.todoapp.homescreen.common.navigateTaskInfo
import com.example.todoapp.homescreen.common.timeFormatter
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.ui.theme.background
import com.example.todoapp.ui.theme.violetColor

@Composable
fun HomeScreen(
    profileName : String,
    profilePic : Uri?,
    taskProgress : Pair<Int,Int>,
    todayTaskDataModel: List<TaskDataModel>,
    categoryList : List<Category>,
    onCategoryClicked : (Category) -> Unit,
    onAddClicked: (String , String) -> Unit,
    updateTaskStatus : (Boolean,Long)-> Unit){
    val localConfig = LocalConfiguration.current
    val context = LocalContext.current
    var addCallBack by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .background(background)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, start = 15.dp),
            horizontalArrangement = Arrangement.SpaceBetween ,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Hi $profileName!",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 22.sp
                )
                Text(
                    text = "Have A Great Day Chef!",
                    fontSize = 15.sp
                )
            }
            AsyncImage(
                model = profilePic,
                error = painterResource(id = R.drawable.profile),
                contentDescription = "ProfileIcon",
                modifier = Modifier
                    .padding(end = 15.dp)
                    .size(70.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.FillBounds)
        }
        LazyRow(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)) {
            items(categoryList.size) {
                Card(
                    modifier = Modifier
                        .height(100.dp)
                        .width((localConfig.screenWidthDp * 0.8).dp)
                        .padding(top = 10.dp, bottom = 10.dp, end = 20.dp)
                        .clickable {
                            onCategoryClicked(categoryList[it])
                        },
                    colors = CardDefaults.cardColors(containerColor = violetColor)
                ) {
                    Column {
                        Text(text = categoryList[it].categoryName ,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp , vertical = 5.dp)
                        )
                        Text(text = categoryList[it].categoryDescription ,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp , vertical = 5.dp)
                        )
                    }
                }
            }
            item {
                AddIcon() {
                    addCallBack = !addCallBack
                }
            }
        }
        val textModifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp, horizontal = 15.dp)
        LazyColumn(modifier = Modifier.padding(vertical = 15.dp)){
            item{
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = textModifier
                ) {
                    Text(
                        text = context.getString(R.string.today_prograss),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                    )
                    Text(
                        text = "${taskProgress.first} / ${taskProgress.second}",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                    )
                }
                val taskProgress = if(taskProgress.second == 0){
                    1f
                } else {
                    taskProgress.first.toFloat() / taskProgress.second.toFloat()
                }
                LinearProgressIndicator(
                    progress = taskProgress,
                    color = violetColor,
                    modifier = textModifier
                )
                if(todayTaskDataModel.isNotEmpty()){
                    Text(
                        text = context.getString(R.string.upcoming_tasks),
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp,
                        modifier = textModifier
                    )
                }
            }
            items(todayTaskDataModel.size) {
                UpcomingTask(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp, horizontal = 15.dp),
                    todayTaskDataModel[it].taskName ,
                    todayTaskDataModel[it].description,
                    todayTaskDataModel[it].time.format(timeFormatter),
                    onTaskClick = {
                        navigateTaskInfo(
                            context = context,
                            taskId = todayTaskDataModel[it].id
                        )
                    },
                    isCompleted = todayTaskDataModel[it].action,
                    onTaskCompleted = { status ->
                        updateTaskStatus(status,todayTaskDataModel[it].id)
                    }
                )
            }
        }
    }
    AnimatedVisibility(visible = addCallBack) {
        AddCategory(onCompleted = { name , description ->
            onAddClicked(name , description)
            addCallBack = false
        } , onBackCallback = {
            addCallBack = false
        })
    }
}

@Composable
fun AddIcon(onAddClicked : ()-> Unit) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .padding(top = 10.dp, bottom = 10.dp)
            .clickable {
                onAddClicked()
            }
    ) {
        Image(painter = painterResource(id = R.drawable.plus_icon),
            contentDescription = "Add Icon")
    }
}

@Preview(showBackground = true ,
    uiMode = UI_MODE_NIGHT_YES)
@Composable
fun HomeScreenPreview() {
    ToDoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize() ,
            color = MaterialTheme.colorScheme.background
        ) {
           HomeScreen(
               profileName = "",
               profilePic = null,
               1 to 1,
               todayTaskDataModel = listOf(),
               categoryList = listOf(),
               onCategoryClicked = {

               },
               onAddClicked = { _ , _ ->

               },
               updateTaskStatus = { _ ,_ ->

               }
           )
        }
    }
}