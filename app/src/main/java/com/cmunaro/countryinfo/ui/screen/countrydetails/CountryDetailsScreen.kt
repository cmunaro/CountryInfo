package com.cmunaro.countryinfo.ui.screen.countrydetails

import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun CountryDetailsScreen(countryCode: String) {
    Text(text = countryCode)
}
