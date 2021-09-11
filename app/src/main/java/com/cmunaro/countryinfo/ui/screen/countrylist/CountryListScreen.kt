package com.cmunaro.countryinfo.ui.screen.countrylist

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.cmunaro.countryinfo.ui.screen.countrylist.components.CountryList
import com.cmunaro.countryinfo.ui.shared.Loading

@Composable
fun CountryListScreen(viewModel: CountryListViewModel) {
    val countryListScreenState: CountryListScreenState by viewModel.state
    Column {
        NameInputFilter(filter = countryListScreenState.filter, onChange = viewModel::changeFilter)
        CountryList(items = countryListScreenState.countries)
    }
    Loading(isVisible = countryListScreenState.isLoading)
}
