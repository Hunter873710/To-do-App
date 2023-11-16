package com.example.todoapp.homescreen.view.tasklist


import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.ui.theme.background
import com.example.todoapp.ui.theme.violetColor
import com.example.todoapp.ui.theme.secondaryBackground
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.MaterialDialogState
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun TaskDate(
    state : MaterialDialogState ,
    date: LocalDate ,
    onDateChange : (LocalDate) -> Unit
) {
    MaterialDialog(
        dialogState = state ,
        buttons = {
            positiveButton("Ok")
        }) {
        datepicker(
            title = "",
            initialDate = date,
            onDateChange = {
                onDateChange(it)
            },
            colors = DatePickerDefaults.colors(
                headerBackgroundColor = secondaryBackground,
                dateActiveBackgroundColor = secondaryBackground
            )
        )
    }
}

@Composable
fun TaskTime(
    state : MaterialDialogState ,
    time: LocalTime ,
    onTimeChange : (LocalTime) -> Unit
) {
    MaterialDialog(
        dialogState = state ,
        backgroundColor = background,
        buttons = {
            positiveButton("Ok")
        }) {
        timepicker(
            title = "" ,
            initialTime = time,
            is24HourClock = false,
            onTimeChange = {
                onTimeChange(it)
            } ,
            colors = TimePickerDefaults.colors(
                    activeBackgroundColor = violetColor,
                    inactiveBackgroundColor = secondaryBackground,
                    inactiveTextColor = Color.White,
                    inactivePeriodBackground = secondaryBackground,
                    selectorColor = violetColor
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun TaskTimePreview() {
    ToDoAppTheme {
        TaskTime(state = rememberMaterialDialogState()
            , time = LocalTime.now()
            , onTimeChange ={

            }
        )
    }
}