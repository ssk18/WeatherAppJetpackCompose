package com.example.jetweatherforecast.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.jetweatherforecast.data.DataOrException
import com.example.jetweatherforecast.model.WeatherItem
import com.example.jetweatherforecast.model.WeatherObject
import com.example.jetweatherforecast.navigation.WeatherScreens
import com.example.jetweatherforecast.screens.main.MainViewModel
import com.example.jetweatherforecast.screens.settings.SettingsViewModel
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDecimals
import com.example.jetweatherforecast.widgets.*

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    city: String?
) {
    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }
    if(!unitFromDb.isNullOrEmpty()){
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit =="imperial"
        val weatherData = produceState<DataOrException<WeatherObject, Boolean, Exception>>(
            initialValue = DataOrException(loading = true)
        ) {
            value = mainViewModel.getWeatherData(city = city.toString(), units = unit)
        }.value

        if (weatherData.loading == true) {
            CircularProgressIndicator()
        } else if (weatherData != null) {
            MainScaffold(weatherData.data!!, navController)
        }
    }
}

@Composable
fun MainScaffold(weather: WeatherObject, navController: NavController) {

    Scaffold(topBar = {
        WeatherAppBar(
            title = weather.city.name + " ,${weather.city.country}",
            navController = navController,
            onAddActionClicked = {
                                 navController.navigate(WeatherScreens.SearchScreen.name)
            },
            elevation = 5.dp
        ) {
            Log.d("TAG", "MainScaffold: Button Clicked")
        }
    }) {
        MainContent(data = weather)
    }

}

@Composable
fun MainContent(data: WeatherObject) {
    val weatherItem = data.list[0]
    val imageUrl = "https://openweathermap.org/img/wn/${weatherItem.weather[0].icon}.png"
    Column(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatDate(weatherItem.dt),
            style = MaterialTheme.typography.caption,
            color = MaterialTheme.colors.onSecondary,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )
        Surface(
            Modifier
                .padding(6.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                WeatherStateImage(imgUrl = imageUrl)
                Text(
                    text = formatDecimals(weatherItem.temp.day) + "º",
                    style = MaterialTheme.typography.h4,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = weatherItem.weather[0].main,
                    fontStyle = FontStyle.Italic
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        HumidityWindPressureRow(weatherItem = weatherItem)
        Divider()
        SunsetAndSunrise(weatherItem)
        Text(
            text = "This Week",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold
        )
        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(size = 14.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
                items(items = data.list) { item: WeatherItem ->
                    WeatherDetailRow(weather = item)
                }
            }
        }
    }
}
