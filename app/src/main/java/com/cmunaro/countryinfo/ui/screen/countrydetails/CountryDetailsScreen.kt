package com.cmunaro.countryinfo.ui.screen.countrydetails

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.cmunaro.countryinfo.ui.screen.countrydetails.components.CountryInfo
import com.cmunaro.countryinfo.ui.shared.Loading
import org.koin.androidx.compose.getViewModel

@Composable
fun CountryDetailsScreen(
    countryCode: String,
    viewModel: CountryDetailsViewModel = getViewModel()
) {
    LaunchedEffect(countryCode) { viewModel.getInfoOf(countryCode) }
    val state by viewModel.state.collectAsState()
    state.country?.let { country ->
        CountryInfo(country = country)
    }
    Loading(isVisible = state.loading)
}