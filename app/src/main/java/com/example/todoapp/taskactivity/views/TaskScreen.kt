package com.example.todoapp.taskactivity.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.R
import com.example.todoapp.ui.theme.violetColor
import com.example.todoapp.ui.theme.boxColor1
import com.example.todoapp.ui.theme.boxColor2


@Composable
fun TaskScreen(
    title : String ,
    description : String,
    onBackPressed : ()-> Unit ,
    onDelete : ()-> Unit
){
    val cardBackground = Brush.horizontalGradient(colors = listOf(boxColor1 , boxColor2))
    val cardBorder = Brush.horizontalGradient(colors = listOf(Color.White , violetColor))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp) ,
            verticalAlignment = Alignment.CenterVertically
        ){
            Icon(
                painter = painterResource(id = R.drawable.back_icon),
                contentDescription = "BackIcon" ,
                modifier = Modifier
                    .clickable {
                        onBackPressed()
                    }
                    .padding(horizontal = 10.dp)
                    .size(32.dp)
            )
            Text(
                text = title ,
                modifier = Modifier
                    .padding(horizontal = 10.dp)
                    .weight(1f)
                    .background(
                        brush = cardBackground,
                        shape = RoundedCornerShape(25.dp)
                    )
                    .border(2.dp , cardBorder,RoundedCornerShape(25.dp))
                    .padding(10.dp) ,
                textAlign = TextAlign.Center
            )
            Icon(
                painter = painterResource(id = R.drawable.delete_icon),
                contentDescription = "Delete Icon" ,
                modifier = Modifier
                    .clickable {
                        onDelete()
                    }
                    .padding(horizontal = 10.dp)
                    .size(32.dp)
            )
        }

        Text(
            text = description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .heightIn(min = 250.dp)
                .background(
                    brush = cardBackground,
                    shape = RoundedCornerShape(25.dp)
                )
                .border(2.dp , cardBorder,RoundedCornerShape(25.dp))
                .padding(10.dp)
        )
    }
}


@Composable
@Preview(showBackground = true)
fun TaskScreen_Preview(){
    ToDoAppTheme {
        TaskScreen(
            "Title" ,
            "Description",
            onBackPressed = {

            },
            onDelete = {

            }
        )
    }
}

