package com.example.todoapp.homescreen.view

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.ui.theme.violetColor
import com.example.todoapp.ui.theme.boxColor1
import com.example.todoapp.ui.theme.boxColor2
import com.example.todoapp.R

@Composable
fun UpcomingTask(
    modifier: Modifier ,
    taskName: String,
    taskDesc : String ,
    time : String,
    onTaskClick : ()-> Unit,
    isCompleted : Boolean,
    onTaskCompleted : (Boolean) ->Unit
) {
    val cardBackground = Brush.horizontalGradient(colors = listOf(boxColor1 , boxColor2))
    val cardBorder = Brush.horizontalGradient(colors = listOf(Color.White , violetColor))
    Row(modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = time ,
            modifier = Modifier.padding(end = 10.dp),
            fontSize = 12.sp
        )
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(20.dp))
                .border(2.dp , cardBorder , RoundedCornerShape(20.dp))
                .clickable {
                    onTaskClick()
                }
                .background(cardBackground)
                .padding(10.dp)
        ) {
            Column(modifier = Modifier
                .weight(1f)
            ) {
                Text(
                    text = taskName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
                Text(
                    text = taskDesc,
                    fontSize = 12.sp
                )
            }
            if (isCompleted) {
                Icon(
                    painter = painterResource(id = R.drawable.tick),
                    contentDescription = "Tick mark",
                    modifier = Modifier
                        .padding(10.dp)
                        .size(40.dp)
                        .drawBehind {
                            drawCircle(violetColor)
                        }
                        .padding(5.dp)
                        .clickable {
                            onTaskCompleted(false)
                        }
                )
            } else {
                Canvas(
                    modifier = Modifier
                        .padding(10.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable {
                            onTaskCompleted(true)
                        },
                    onDraw = {
                        drawCircle(
                            color = violetColor,
                            style = Stroke(4.dp.toPx())
                        )
                    })
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun UpcomingTaskPreview() {
//    UpcomingTask(
//        Modifier
//            .fillMaxWidth()
//            .padding(vertical = 10.dp) ,
//        "Task Name" ,
//        "Task Des" ,
//        "10 AM" ,{
//
//        })
}