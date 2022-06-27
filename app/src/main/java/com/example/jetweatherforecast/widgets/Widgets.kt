package com.example.jetweatherforecast.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.jetweatherforecast.R
import com.example.jetweatherforecast.model.WeatherItem
import com.example.jetweatherforecast.utils.formatDate
import com.example.jetweatherforecast.utils.formatDateTime
import com.example.jetweatherforecast.utils.formatDecimals


@Composable
fun WeatherDetailRow(weather: WeatherItem) {
    val imageUrl = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png"
    Surface(
        modifier = Modifier
            .padding(2.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(topEnd = CornerSize(6.dp)),
        color = Color.White
    ) {
        Row(
            Modifier.padding(3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = formatDate(weather.dt)
                .split(",")[0],
                Modifier.padding(start = 5.dp))
            WeatherStateImage(imgUrl = imageUrl)
            Surface(
                Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFFFC400)
            ) {
                Text(text = weather.weather[0].description,
                    Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption)
            }
            Text(text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                    color = Color.Blue.copy(0.7f),
                    fontWeight = FontWeight.SemiBold
                )
                ){
                    append(formatDecimals(weather.temp.max) + "°")
                }
                withStyle(
                    SpanStyle(
                        color = Color.LightGray
                    )
                ){
                    append(formatDecimals(weather.temp.min) + "°")
                }
            })
        }
    }
}

@Composable
fun SunsetAndSunrise(weatherItem: WeatherItem) {
    Row(
        Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(horizontalArrangement = Arrangement.Start) {
            Image(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise icon",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = formatDateTime(weatherItem.sunrise),
                style = MaterialTheme.typography.caption
            )
        }
        Row(horizontalArrangement = Arrangement.End) {
            Image(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "sunset icon",
                Modifier.size(30.dp)
            )
            Text(
                text = formatDateTime(weatherItem.sunset),
                style = MaterialTheme.typography.caption
            )
        }

    }
}

@Composable
fun HumidityWindPressureRow(weatherItem: WeatherItem) {
    Row(
        Modifier
            .padding(12.dp)
            .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row() {
            Image(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon",
                Modifier
                    .size(20.dp)
                    .padding(5.dp),
                alignment = Alignment.TopStart
            )
            Text(text = "${weatherItem.humidity}%", style = MaterialTheme.typography.caption)
        }
        Row() {
            Image(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon",
                Modifier
                    .size(20.dp)
                    .padding(5.dp)
            )
            Text(text = "${weatherItem.pressure} psi", style = MaterialTheme.typography.caption)
        }
        Row() {
            Image(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon",
                Modifier
                    .size(20.dp)
                    .padding(5.dp)
            )
            Text(text = "${weatherItem.speed} mph%", style = MaterialTheme.typography.caption)
        }
    }
}

@Composable
fun WeatherStateImage(imgUrl: String) {
    Image(
        painter = rememberImagePainter(imgUrl), contentDescription = "icon image",
        Modifier.size(60.dp)
    )
}
