package com.cmunaro.countryinfo.ui.screen.countrylist

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.cmunaro.countryinfo.ui.screen.countrylist.components.ContinentFilter
import com.cmunaro.countryinfo.ui.screen.countrylist.components.CountryList
import com.cmunaro.countryinfo.ui.shared.Loading
import org.koin.androidx.compose.getStateViewModel

@Composable
fun CountryListScreen(
    navController: NavHostController,
    viewModel: CountryListViewModel = getStateViewModel()
) {
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
        CountryList(navController = navController, items = countryListScreenState.countries)
    }
    Loading(isVisible = countryListScreenState.isLoading)
}
