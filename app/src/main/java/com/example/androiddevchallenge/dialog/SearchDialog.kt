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
package com.example.androiddevchallenge.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.androiddevchallenge.R
import com.example.androiddevchallenge.data.weather_data.DataWeather
import com.example.androiddevchallenge.screens.homescreen.HomeViewModel
import com.example.androiddevchallenge.ui.theme.MyTheme

class SearchDialog : DialogFragment() {

    private var title: String? = null
    private var content: String? = null
    private var button: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        dialog!!.window?.setBackgroundDrawableResource(android.R.color.transparent)
        val viewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)
        val countryName: MutableList<String> =
            DataWeather().getWeatherDataObject().map {
                it.country
            }.toMutableList()

        return ComposeView(requireContext()).apply {

            setContent {
                MyTheme {
                    Column(
                        modifier = Modifier.background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(20.dp)
                        )
                    ) {

                        Box(
                            Modifier
                                .fillMaxWidth()
                                .wrapContentWidth(align = Alignment.End)
                                .clickable {
                                    dismiss()
                                }
                                .background(
                                    color = MaterialTheme.colors.primary,
                                    shape = CircleShape
                                )
                        ) {
                            Icon(
                                imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                                context.getString(R.string.close_icon),
                                tint = MaterialTheme.colors.background,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .padding(top = 16.dp, bottom = 16.dp)
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(25.dp))
                                .background(MaterialTheme.colors.secondary.copy(alpha = 0.7f)),
                        ) {

                            val listCountryNames: MutableState<MutableList<String>> = remember {
                                mutableStateOf(countryName)
                            }

                            SearchBar { searchText ->

                                if (searchText.text.isNotEmpty()) {
                                    listCountryNames.value = (
                                        countryName.filter {
                                            it.contains(searchText.text, ignoreCase = true)
                                        }.toMutableList()
                                        )
                                } else {
                                    listCountryNames.value = (
                                        countryName.toMutableList()
                                        )
                                }
                            }
                            Column(
                                modifier = Modifier.padding(
                                    start = 16.dp,
                                    end = 16.dp,
                                    bottom = 16.dp
                                )
                            ) {
                                listCountryNames.value.forEach { item ->

                                    Column(
                                        modifier = Modifier.clickable {
                                            viewModel.getWeatherData(item)
                                            dismiss()
                                        }
                                    ) {

                                        Text(
                                            item,
                                            modifier = Modifier
                                                .padding(8.dp)
                                                .fillMaxWidth(),
                                            style = MaterialTheme.typography.body1.copy(
                                                color = MaterialTheme.colors.primary
                                            )
                                        )
                                        Divider()
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SearchBar(onValueChanged: (TextFieldValue) -> Unit) {

        var inputvalue by remember { mutableStateOf(TextFieldValue("")) }
        TextField(
            value = inputvalue,
            onValueChange = {
                inputvalue = it
                onValueChanged(it)
            },
            placeholder = {
                Text(stringResource(R.string.search_placeholder), color = MaterialTheme.colors.primary)
            },

            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(25.dp))
                .background(MaterialTheme.colors.secondary),

            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_search),
                    stringResource(
                        R.string.search_icon
                    )
                )
            },
            trailingIcon = {
                if (inputvalue.text.isNotEmpty()) {

                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_close),
                        stringResource(id = R.string.close_icon)
                    )
                }
            },

            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.secondary,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )

        )
    }

    override fun onStart() {
        val width = (resources.displayMetrics.widthPixels * 0.85).toInt()
        dialog!!.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
        this.isCancelable = true

        super.onStart()
    }
}
