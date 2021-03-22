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
package com.example.androiddevchallenge.utils

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import com.example.androiddevchallenge.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun getTodayDate(): String {
    val sdf = SimpleDateFormat("dd, MMM yyyy", Locale.getDefault())
    return sdf.format(Date())
}
fun getCloudResource(weatherName: String): Int {
    when (weatherName) {
        "cloudy" -> {
            return R.drawable.b_2_cloudy
        }
        "sunny" -> {
            return R.drawable.a_1_sunny
        }

        "thunder" -> {
            return R.drawable.c_3_thunderstorm
        }
        "clear" -> {
            return R.drawable.d_3_sleet
        }

        "rainy" -> {

            return R.drawable.c_1_rainy
        }
        "partial_cloudy" -> {

            return R.drawable.b_1_partly_cloudy
        }
        "heavy_rain" -> {

            return R.drawable.c_2_heavy_rain
        }
        "heavy_snow" -> {

            return R.drawable.d_2_heavy_snow
        }
        "night" -> {

            return R.drawable.a_4_night
        }
        "tornado" -> {

            return R.drawable.e_4_tornado
        }
        "sunrise" -> {

            return R.drawable.f_1_sunrise
        }
        "thunderstorm" -> {

            return R.drawable.c_3_thunderstorm
        }
        "stormy" -> {

            return R.drawable.g_1_stormy
        }
        "partly_cloudy" -> {

            return R.drawable.b_1_partly_cloudy
        }

        "windy" -> {

            return R.drawable.f_3_windy
        }
        "little_sunny" -> {

            return R.drawable.a_2_little_sunny
        }
        "very_windy" -> {

            return R.drawable.f_4_very_windy
        }

        "lightning" -> {

            return R.drawable.c_4_lightning
        }
        "sunset" -> {

            return R.drawable.f_2_sunset
        }
        "snow" -> {

            return R.drawable.d_1_snow
        }
        "snowflake" -> {

            return R.drawable.g_3_snowflake
        }

        "cloudy_night" -> {

            return R.drawable.b_4_cloudy_night
        }

        else -> {
            return R.drawable.a_1_sunny
        }
    }
}
@Composable
fun GetWorldBackground() {

    return Icon(
        imageVector = ImageVector.vectorResource(id = R.drawable.world),
        contentDescription = "world",
        tint = Color.LightGray.copy(alpha = 0.2f),
        modifier = Modifier.fillMaxWidth()

    )
}

@Composable
fun tempInDegree(weatherData: String): AnnotatedString.Builder {

    return AnnotatedString.Builder("$weatherData°")
        .apply {
            addStyle(
                SpanStyle(
                    color = MaterialTheme.colors.primaryVariant
                ),
                length - 1, length
            )
        }
}
