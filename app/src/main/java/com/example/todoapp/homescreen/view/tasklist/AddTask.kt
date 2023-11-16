package com.example.todoapp.homescreen.view.tasklist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.R
import com.example.todoapp.homescreen.common.timeFormatter
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.ui.theme.background
import com.example.todoapp.ui.theme.violetColor
import com.example.todoapp.ui.theme.secondaryBackground
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTask(
    onComplete : (String , String , LocalDate , LocalTime ) -> Unit ,
    onBackPressed : ()-> Unit
){
    val cardBorder = Brush.horizontalGradient(colors = listOf(Color.White , violetColor))
    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = violetColor
    )
    val context = LocalContext.current
    var taskName by remember {
        mutableStateOf("")
    }
    var taskDescription by remember {
        mutableStateOf("")
    }

    var date by remember {
        mutableStateOf(LocalDate.now())
    }
    var time by remember {
        mutableStateOf(LocalTime.now())
    }

    var dateDialogState = rememberMaterialDialogState()
    var timeDialogState = rememberMaterialDialogState()
    Surface(modifier = Modifier
        .fillMaxSize(),
        color = background ,
        contentColor = Color.White
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable {
                onBackPressed()
            }
            .background(secondaryBackground)
            ) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(15.dp)
                    .border(1.dp, cardBorder, RoundedCornerShape(15.dp))
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(value = taskName, onValueChange = {
                    taskName = it
                }, placeholder = {
                    Text(text = context.getString(R.string.task_name))
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                    colors = textFieldColors
                )

                OutlinedTextField(value = taskDescription, onValueChange = {
                    taskDescription = it
                }, placeholder = {
                    Text(text = context.getString(R.string.task_description))
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 10.dp),
                    colors = textFieldColors
                )
    
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp) ,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = "${date.dayOfMonth} ${date.month} ${date.year}" ,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                    Button(onClick = { 
                        dateDialogState.show()
                    },colors = ButtonDefaults.buttonColors(containerColor = violetColor)) {
                        Text(text = "Pick")
                    }
                }

                TaskDate(
                    state = dateDialogState,
                    date = date,
                    onDateChange = {
                        date = it
                    }
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp) ,
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    Text(
                        text = "${time.format(timeFormatter)}" ,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                    Button(onClick = {
                        timeDialogState.show()
                    },colors = ButtonDefaults.buttonColors(containerColor = violetColor)) {
                        Text(text = "Pick")
                    }
                }

                TaskTime(
                    state = timeDialogState,
                    time = time,
                    onTimeChange = {
                        time = it
                    })

                Button(onClick = {
                    onComplete(
                        taskName ,
                        taskDescription ,
                        date,
                        time)
                },colors = ButtonDefaults.buttonColors(containerColor = violetColor)) {
                    Text(text = context.getString(R.string.add))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddTaskPreview() {
    ToDoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = background,
            contentColor = Color.White
        ){
            AddTask(onBackPressed = {

            } , onComplete = { _ , _ , _ , _->

            })
        }
    }
}