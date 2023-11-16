package com.example.todoapp.homescreen.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.example.todoapp.ui.theme.background
import com.example.todoapp.ui.theme.violetColor
import com.example.todoapp.ui.theme.secondaryBackground

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun  AddCategory(onCompleted : (String , String)-> Unit , onBackCallback : ()->Unit){
    val cardBorder = Brush.horizontalGradient(colors = listOf(Color.White , violetColor))
    val context = LocalContext.current
    var categoryName by remember {
        mutableStateOf("")
    }
    var categoryDescription by remember {
        mutableStateOf("")
    }
    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        focusedBorderColor = violetColor
    )
    Surface(modifier = Modifier.fillMaxSize() ,
        color = background ,
        contentColor = Color.White
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(secondaryBackground)
            .clickable {
                onBackCallback()
            }) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .border(1.dp, cardBorder, RoundedCornerShape(15.dp))
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(value = categoryName, onValueChange = {
                    categoryName = it
                }, placeholder = {
                    Text(text = context.getString(R.string.category_name))
                }, modifier = Modifier.padding(vertical = 10.dp),
                    colors = textFieldColors)

                OutlinedTextField(value = categoryDescription, onValueChange = {
                    categoryDescription = it
                }, placeholder = {
                    Text(text = context.getString(R.string.category_description))
                }, modifier = Modifier.padding(vertical = 10.dp),
                    colors = textFieldColors)

                Button(onClick = {
                    onCompleted(categoryName , categoryDescription)
                } , colors = ButtonDefaults.buttonColors(containerColor = violetColor)) {
                    Text(text = context.getString(R.string.add))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AddCategoryPreview() {
    AddCategory(onBackCallback = {

    } , onCompleted = { _ , _ ->

    })
}
