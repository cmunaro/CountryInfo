package com.cmunaro.countryinfo.ui.screen.countrylist

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.cmunaro.countryinfo.ui.screen.countrylist.components.ContinentFilter
import com.cmunaro.countryinfo.ui.screen.countrylist.components.CountryList
import com.cmunaro.countryinfo.ui.shared.Loading

@Composable
fun CountryListScreen(viewModel: CountryListViewModel) {
    val countryListScreenState: CountryListScreenState by viewModel.state.collectAsState()
    Column {
        NameInputFilter(
            filter = countryListScreenState.filterName,
            onChange = viewModel::changeNameFilter,
            onClear = viewModel::clearNameFilter
        )
        ContinentFilter(
            filters = countryListScreenState.continentFilters,
            onToggle = viewModel::toggleFilter
        )
        CountryList(items = countryListScreenState.countries)
    }
    Loading(isVisible = countryListScreenState.isLoading)
}
