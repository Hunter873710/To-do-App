package com.example.todoapp.homescreen.view.tasklist

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todoapp.ui.theme.secondaryBackground
import com.example.todoapp.R
import com.example.todoapp.database.TaskDataModel
import com.example.todoapp.homescreen.common.navigateTaskInfo
import com.example.todoapp.homescreen.common.timeFormatter
import com.example.todoapp.ui.theme.violetColor
import java.util.Date

@Composable
fun TaskCard(
    model: TaskDataModel,
    onTaskStatus : (Boolean)-> Unit
){
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .heightIn(min = 100.dp)
            .background(
                secondaryBackground,
                RoundedCornerShape(topStart = 70.dp, bottomEnd = 70.dp)
            ).clickable {
                navigateTaskInfo(
                    context ,
                    model.id
                )
            },
        colors = CardDefaults.cardColors(
            containerColor = secondaryBackground ,
            contentColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (model.action) {
                Icon(
                    painter = painterResource(id = R.drawable.tick),
                    contentDescription = "Tick mark",
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .size(40.dp)
                        .drawBehind {
                            drawCircle(violetColor)
                        }
                        .padding(5.dp)
                        .clickable {
                            onTaskStatus(false)
                        }
                )
            } else {
                Canvas(
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .clickable {
                            onTaskStatus(true)
                        },
                    onDraw = {
                        drawCircle(
                            color = violetColor,
                            style = Stroke(4.dp.toPx())
                        )
                    })
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text( model.date.toString() ,
                        fontWeight = FontWeight.Bold)
                    Text(  model.time.format(timeFormatter) ,
                        fontWeight = FontWeight.Bold)
                }
                Text(
                    text = model.taskName, fontWeight = FontWeight.Bold,
                    fontSize = 17.sp
                )
                Text(text = model.description)
                android.icu.util.Calendar.getInstance().time.time
                Date()
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun TaskCard_Preview(){
}