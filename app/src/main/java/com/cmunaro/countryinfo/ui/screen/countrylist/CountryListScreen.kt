package com.cmunaro.countryinfo.ui.screen.countrylist

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.cmunaro.countryinfo.ui.screen.components.Loading

@Composable
fun CountryListScreen(viewModel: CountryListViewModel) {
    val countryListState: CountryListState by viewModel.state
    CountryList(items = countryListState.countries)
    Loading(isVisible = countryListState.isLoading)
}