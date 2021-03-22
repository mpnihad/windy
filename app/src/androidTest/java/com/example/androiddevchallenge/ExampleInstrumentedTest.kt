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
package com.example.androiddevchallenge

import androidx.activity.viewModels
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.androiddevchallenge.screens.homescreen.HomeActivity
import com.example.androiddevchallenge.screens.homescreen.HomeViewModel
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SearchText {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<HomeActivity>()

    @Test
    fun initialTest() {

        composeTestRule.onNodeWithContentDescription("loading").assertExists()
    }

    @Test
    fun checkSearch() {

        val homeViewModel: HomeViewModel by composeTestRule.activity.viewModels()

        homeViewModel.getWeatherData("India")
        composeTestRule.waitUntil(timeoutMillis = 4000) { homeViewModel.isLoading.value == false }
        composeTestRule.onNodeWithContentDescription("Edit Location").performClick()
        composeTestRule.onNodeWithText("Search...").assertExists()

    }
}
