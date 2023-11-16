package com.example.todoapp.homescreen

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.todoapp.database.TaskDatabase
import com.example.todoapp.homescreen.presenter.HomeScreenPresenter
import com.example.todoapp.homescreen.view.HomeScreenNavigation
import com.example.todoapp.ui.theme.ToDoAppTheme

class HomeScreenActivity : ComponentActivity() {
    private lateinit var presenter: HomeScreenPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                1
            )
        }
        presenter = ViewModelProvider(
            owner = this,
            factory = HomeScreenPresenter.getFactory(TaskDatabase.getInstance(this))
        )[HomeScreenPresenter::class.java]
        presenter.updateProfilePic(this)
        setContent {
            ToDoAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                    contentColor = Color.White
                ) {
                    HomeScreenNavigation(presenter)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ToDoAppTheme {
    }
}