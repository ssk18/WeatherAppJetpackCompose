package com.example.jetweatherforecast.screens.about

import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.jetweatherforecast.widgets.WeatherAppBar

@Composable
fun AboutScreen(navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            "About",
            navController = navController,
            icon = Icons.Default.ArrowBack, isMainScreen = false
        ) {
            navController.popBackStack()
        }
    }) {
        Surface() {

        }
    }
}