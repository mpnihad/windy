/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge.screens.homescreen

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditLocation
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.swipeable
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentManager
import com.example.androiddevchallenge.ApplicationClass
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.weather_data.NearByCity
import com.example.androiddevchallenge.data.weather_data.TempByTime
import com.example.androiddevchallenge.data.weather_data.WeatherDataItem
import com.example.androiddevchallenge.dialog.SearchDialog
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.utils.DIALOG_TAG
import com.example.androiddevchallenge.utils.GetWorldBackground
import com.example.androiddevchallenge.utils.getCloudResource
import com.example.androiddevchallenge.utils.getTodayDate
import com.example.androiddevchallenge.utils.tempInDegree
import java.util.Locale
import kotlin.math.roundToInt

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: HomeViewModel by viewModels()
        setContent {
            MyTheme {
                MyInitial(viewModel)
            }
        }
    }

    @Composable
    private fun MyInitial(viewModel: HomeViewModel) {

        val supportFragmentManagers: FragmentManager = remember { supportFragmentManager }
        MyApp(viewModel, supportFragmentManagers)
    }
}

// Start building your app here!
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MyApp(
    viewModel: HomeViewModel,
    supportFragmentManager: FragmentManager
) {
    val isLoading: State<Boolean> = viewModel.isLoading.observeAsState(true)

    Surface(color = MaterialTheme.colors.background) {

        AnimatedVisibility(
            visible = isLoading.value,
            enter = slideInVertically()
        ) {
            ShowLoading()
        }
        AnimatedVisibility(
            visible = !isLoading.value,
            enter = fadeIn() + slideInHorizontally(),
            exit = fadeOut() + slideOutHorizontally()
        ) {
            ShowContent(viewModel, supportFragmentManager)
        }
    }
}

@Composable
fun ShowLoading() {
    val infiniteTransition = rememberInfiniteTransition()
    val alpha by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.a_3_very_sunny),
            contentDescription = "loading",
            Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.Center)
                .alpha(alpha)
                .size(120.dp)
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun ShowContent(
    viewModel: HomeViewModel,
    supportFragmentManager: FragmentManager,

) {
    val isLoading: State<Boolean> = viewModel.isLoading.observeAsState(true)
    val weatherData: WeatherDataItem = viewModel.weatherData.observeAsState().value!!
    val visibilityMoreCity: MutableState<Boolean> = remember { mutableStateOf(false) }
    Column {
        Spacer(
            modifier = Modifier
                .paddingFromBaseline(top = 32.dp)
                .background(color = Color.Transparent)
        )
        Row(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .background(color = Color.Transparent)
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {

                TopBar(weatherData, viewModel) {
                    val dialog = SearchDialog()
                    dialog.show(
                        supportFragmentManager,
                        DIALOG_TAG
                    )
                }

                Spacer(modifier = Modifier.padding(top = 32.dp))

                MiddleContent(weatherData, isLoading)
                Spacer(modifier = Modifier.padding(top = 32.dp))
                WeatherByTime(weatherData)
                Spacer(modifier = Modifier.padding(top = 64.dp))
                DetailedWeather(weatherData, visibilityMoreCity)
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun DetailedWeather(weatherData: WeatherDataItem, visibilityMoreCity: MutableState<Boolean>) {

    Column() {
        Text(
            stringResource(R.string.near_by_cities),
            style = MaterialTheme.typography.h3.copy(
                color = MaterialTheme.colors.primary,
                fontSize = 24.sp
            ),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.padding(top = 32.dp))
        Box() {
            Box(
                modifier = Modifier.align(alignment = Alignment.Center)
            ) {
                GetWorldBackground()
            }
            Column() {

                CityDetails(weatherData.nearByCity.subList(0, 2))

                if (weatherData.nearByCity.size > 2) {
                    MoreCitiesButton(visibilityMoreCity.value) {

                        visibilityMoreCity.value = !visibilityMoreCity.value
                    }
                }
                AnimatedVisibility(visible = visibilityMoreCity.value) {
                    Spacer(modifier = Modifier.padding(bottom = 32.dp))
                }
                AnimatedVisibility(visible = visibilityMoreCity.value) {

                    CityDetails(weatherData.nearByCity.subList(2, weatherData.nearByCity.size))
                }

                Spacer(modifier = Modifier.padding(top = 16.dp))
                WeatherDetails(weatherData)
                Spacer(modifier = Modifier.padding(top = 64.dp))
            }
        }
    }
}

@Composable
fun WeatherDetails(weatherData: WeatherDataItem) {
    Box() {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 32.dp, end = 16.dp)
                .border(
                    BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colors.secondary.copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(top = 32.dp, bottom = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {

                WeatherItems(stringResource(R.string.precipitation), stringResource(id = R.string.percentage, weatherData.tempInDetail.precipitation.toString()))
                Spacer(modifier = Modifier.padding(top = 32.dp))
                WeatherItems(stringResource(R.string.humidity), stringResource(id = R.string.percentage, weatherData.tempInDetail.humidity.toString()))
            }

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrows),
                contentDescription = stringResource(R.string.divider),
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentHeight(align = Alignment.CenterVertically)
            )

            Column(modifier = Modifier.weight(1f)) {

                WeatherItems(stringResource(R.string.wind), stringResource(id = R.string.wind_data, weatherData.tempInDetail.wind.toString()))
                Spacer(modifier = Modifier.padding(top = 32.dp))
                WeatherItems(
                    stringResource(R.string.pressure),
                    stringResource(R.string.pressure_data, weatherData.tempInDetail.pressure.toString())
                )
            }
        }
        Image(
            imageVector = ImageVector.vectorResource(
                id = getCloudResource(
                    weatherData.currentWeather
                )
            ),
            contentDescription = weatherData.currentWeather,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun WeatherItems(title: String, data: String) {

    Column {
        Text(
            title,
            style = MaterialTheme.typography.h3.copy(
                color = MaterialTheme.colors.secondary,
                fontSize = 16.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.padding(top = 8.dp))
        Text(
            data,
            style = MaterialTheme.typography.body1.copy(
                color = MaterialTheme.colors.primary,
                fontSize = 22.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(align = Alignment.CenterHorizontally)
        )
    }
}

@Composable
private fun MoreCitiesButton(visibilityMoreCity: Boolean, onClick: () -> Unit) {
    Spacer(modifier = Modifier.padding(top = 32.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(align = Alignment.CenterHorizontally)
            .background(
                color = MaterialTheme.colors.secondary.copy(alpha = 0.1f),
                shape = RoundedCornerShape(20.dp)
            )
            .clickable {
                onClick()
            }
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)
    ) {

        Text(
            if (visibilityMoreCity) {
                "More cities"
            } else {
                "Less cities"
            },
            style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.primary),
            modifier = Modifier.align(alignment = Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.padding(start = 8.dp))

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_star),
            tint = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.size(25.dp),
            contentDescription = "More Cities"
        )
    }
}

@Composable
fun CityDetails(weatherData: List<NearByCity>) {

    Row() {
        repeat(weatherData.size) { position ->
            if (position < 2) {
                val nearByCity = weatherData[position]
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp, end = 8.dp)
                        .border(
                            BorderStroke(
                                width = 1.dp,
                                color = MaterialTheme.colors.secondary.copy(alpha = 0.1f)
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .background(
                            color = MaterialTheme.colors.surface,
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(16.dp)
                ) {

                    Text(
                        nearByCity.locality.toUpperCase(Locale.ROOT),
                        style = MaterialTheme.typography.h3.copy(color = MaterialTheme.colors.primary),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )

                    Image(
                        imageVector = ImageVector.vectorResource(
                            id = getCloudResource(
                                nearByCity.currentWeather
                            )
                        ),
                        contentDescription = nearByCity.currentWeather,
                        modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(align = Alignment.CenterHorizontally)
                    ) {
                        val annotatedString = tempInDegree(nearByCity.currentTemp.toString())
                        Text(
                            annotatedString.toAnnotatedString(),
                            style = MaterialTheme.typography.h5.copy(
                                color = MaterialTheme.colors.primary,
                                fontSize = 30.sp
                            ),

                        )
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                        Text(
                            nearByCity.currentWeather.replace("_", " ").capitalize(Locale.ROOT),
                            style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.secondary),
                            modifier = Modifier.align(alignment = Alignment.CenterVertically)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TopBar(weatherData: WeatherDataItem, viewModel: HomeViewModel, OnClick: () -> Unit) {
    Box() {
        Column() {
            Text(
                getTodayDate(),
                style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.secondary),
                modifier = Modifier.padding(start = 8.dp)
            )

            Row(
                Modifier

                    .clickable {
                        OnClick()
                    }
                    .semantics(mergeDescendants = true) {
                    }
            ) {

                Icon(Icons.Filled.EditLocation, contentDescription = stringResource(R.string.edit_location))
                Text(
                    "${weatherData.locality}, ".toUpperCase(Locale.ROOT),
                    style = MaterialTheme.typography.h3.copy(color = MaterialTheme.colors.primary),
                    modifier = Modifier.align(alignment = Alignment.Bottom)
                )
                Text(
                    weatherData.country,
                    style = MaterialTheme.typography.h3.copy(color = MaterialTheme.colors.secondary),
                    modifier = Modifier.align(alignment = Alignment.Bottom)
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(align = Alignment.CenterEnd)
        ) {
            SwipeableThemeChange(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeableThemeChange(viewModel: HomeViewModel) {
    val width = 50.dp
    val squareSize = 25.dp
    val isDark: Boolean by viewModel.getApplication<ApplicationClass>().isDark

    val swipeableState = rememberSwipeableState(isDark)
    if (swipeableState.currentValue) {
        viewModel.getApplication<ApplicationClass>().toggleTheme(true)
    } else {
        viewModel.getApplication<ApplicationClass>().toggleTheme(false)
    }
    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
    val anchors = mapOf(0f to true, sizePx to false) // Maps anchor points (in px) to states

    Box(
        modifier = Modifier
            .width(width)
            .swipeable(
                state = swipeableState,
                anchors = anchors,
                thresholds = { from, to ->
                    FractionalThreshold(0.3f)
                },

                orientation = Orientation.Horizontal
            )
            .background(Color.Transparent)
            .border(
                BorderStroke(width = 1.dp, color = MaterialTheme.colors.primary),
                shape = RoundedCornerShape(50)
            )
    ) {

        Box(
            Modifier
                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
                .size(squareSize)
                .background(MaterialTheme.colors.primaryVariant, shape = CircleShape)
        )
    }
    Row(modifier = Modifier.width(width)) {
        Icon(
            imageVector = ImageVector.vectorResource(
                id = R.drawable.a_3_very_sunny
            ),
            contentDescription = stringResource(R.string.weather_Icon),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            tint = MaterialTheme.colors.primary
        )
        Icon(
            imageVector = ImageVector.vectorResource(
                id = R.drawable.a_4_night
            ),
            contentDescription = stringResource(R.string.weather_Icon),
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            tint = MaterialTheme.colors.primary

        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun MiddleContent(weatherData: WeatherDataItem, isLoading: State<Boolean>) {
    Box() {
        GetWorldBackground()
        Column() {

            AnimatedVisibility(
                visible = !isLoading.value,
                enter = expandIn
                (
                    expandFrom = Alignment.Center,
                    animationSpec = tween(
                        durationMillis = 1000,
                        delayMillis = 100,
                    )
                ),
                exit = shrinkOut()
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(
                        id = getCloudResource(
                            weatherData.currentWeather
                        )
                    ),
                    contentDescription = stringResource(R.string.weather_Icon),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentWidth(align = Alignment.CenterHorizontally)
                        .size(150.dp)
                )
            }
            Text(
                weatherData.currentWeather.capitalize(Locale.ROOT),
                style = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.secondary),
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
            val annotatedString = tempInDegree(weatherData.currentTemp.toString())
            Text(
                annotatedString.toAnnotatedString(),
                style = MaterialTheme.typography.h1.copy(color = MaterialTheme.colors.primary),
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
private fun WeatherByTime(weatherData: WeatherDataItem) {
    LazyRow() {
        itemsIndexed(items = weatherData.tempByTime) { index: Int, item: TempByTime ->
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
                    .width(100.dp)
            ) {
                Image(
                    imageVector = ImageVector.vectorResource(
                        id = getCloudResource(
                            item.weather
                        )
                    ),
                    contentDescription = item.weather,
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                )

                Text(
                    item.time,
                    style = MaterialTheme.typography.h6.copy(color = MaterialTheme.colors.primary),
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                )

                val annotatedString = tempInDegree(item.temp)

                Text(
                    annotatedString.toAnnotatedString(),
                    style = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.primary),
                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Preview("Light Theme", widthDp = 360, heightDp = 640)
@Composable
fun LightPreview() {

    MyTheme {
    }
}

@Preview("Dark Theme", widthDp = 360, heightDp = 640)
@Composable
fun DarkPreview() {
    MyTheme(darkTheme = true) {
    }
}
