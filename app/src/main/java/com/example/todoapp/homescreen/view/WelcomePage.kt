package com.example.todoapp.homescreen.view

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.todoapp.ui.theme.ToDoAppTheme
import com.example.todoapp.ui.theme.background
import com.example.todoapp.R
import com.example.todoapp.ui.theme.violetColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun WelcomePage(onSave : (String , Uri?) -> Unit) {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    var name by remember {
        mutableStateOf("")
    }
    var profilePic : Uri? by remember {
        mutableStateOf(null)
    }

    val profilePicLauncher =  rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult ={ uri ->
            if(uri != null) {
                profilePic = uri
            }
        }
    )
    val mediaRequest1 = PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)

    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            text = context.getString(R.string.welcome) ,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier
                .padding(40.dp)
                .align(Alignment.TopCenter)
        )
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = profilePic,
                contentDescription = "Profile Pic",
                error = painterResource(id = R.drawable.profile),
                modifier = Modifier
                    .border(2.dp, violetColor, CircleShape)
                    .padding(5.dp)
                    .size(250.dp)
                    .clip(CircleShape)
                    .clickable {
                        profilePicLauncher.launch(mediaRequest1)
                    },
                contentScale = ContentScale.FillBounds
            )
            TextField(value = name,
                onValueChange = {
                    name = it
                } ,
                placeholder = {
                    Text(text = context.getString(R.string.enter_name))
                } ,
                modifier = Modifier.padding(25.dp),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                }) ,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done)
            )
            Button(onClick = {
                onSave(name , profilePic)
            }) {
                Text(text = context.getString(R.string.save) )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePagePreview() {
    ToDoAppTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = background,
            contentColor = Color.White
        ) {
            WelcomePage(
                onSave = { _, _ ->
                }
            )
        }
    }
}