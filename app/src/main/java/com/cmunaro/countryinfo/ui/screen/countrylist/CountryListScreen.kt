package com.cmunaro.countryinfo.ui.screen.countrylist

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cmunaro.countryinfo.ui.screen.countrylist.components.ContinentFilter
import com.cmunaro.countryinfo.ui.screen.countrylist.components.CountryList
import com.cmunaro.countryinfo.ui.shared.Loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CountryListScreen(
    navController: NavHostController,
    viewModelStateFlow: StateFlow<CountryListScreenState>,
    onChangeNameFilter: (String) -> Unit,
    onClearNameFilter: () -> Unit,
    onToggleContinentFilter: (ContinentFilterEntry) -> Unit
) {
    val countryListScreenState: CountryListScreenState by viewModelStateFlow.collectAsState()
    Column {
        NameInputFilter(
            filter = countryListScreenState.filterName,
            onChange = onChangeNameFilter,
            onClear = onClearNameFilter
        )
        ContinentFilter(
            filters = countryListScreenState.continentFilters,
            onToggle = onToggleContinentFilter
        )
        CountryList(navController = navController, items = countryListScreenState.countries)
    }
    Loading(isVisible = countryListScreenState.isLoading)
}

@Preview(showBackground = true)
@Composable
fun CountryListScreenPreview() {
    MaterialTheme {
        CountryListScreen(
            navController = rememberNavController(),
            viewModelStateFlow = MutableStateFlow(
                CountryListScreenState(
                    isLoading = false,
                    countries = listOf(
                        CountryListEntry("Italia", "IT"),
                        CountryListEntry("Norvegia", "NO"),
                        CountryListEntry("Spagna", "ES"),
                        CountryListEntry("France", "FR"),
                    ),
                    filterName = "Asd",
                    continentFilters = listOf(
                        ContinentFilterEntry("Europe", true),
                        ContinentFilterEntry("Asia", false)
                    )
                )
            ),
            onChangeNameFilter = {},
            onClearNameFilter = {},
            onToggleContinentFilter = {}
        )
    }
}