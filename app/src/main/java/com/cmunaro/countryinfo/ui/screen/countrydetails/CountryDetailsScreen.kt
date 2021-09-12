package com.cmunaro.countryinfo.ui.screen.countrydetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.cmunaro.countryinfo.ui.screen.countrydetails.components.CountryInfo
import com.cmunaro.countryinfo.ui.shared.Loading

@Composable
fun CountryDetailsScreen(
    viewModel: CountryDetailsViewModel
) {
    val state by viewModel.state.collectAsState()
    state.country?.let { country ->
        CountryInfo(country = country)
    }
    Loading(isVisible = state.loading)
}