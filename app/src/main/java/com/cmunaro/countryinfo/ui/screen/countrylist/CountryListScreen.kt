package com.cmunaro.countryinfo.ui.screen.countrylist

import androidx.compose.foundation.layout.Column
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cmunaro.countryinfo.ui.screen.countrylist.components.ContinentFilter
import com.cmunaro.countryinfo.ui.screen.countrylist.components.CountryList
import com.cmunaro.countryinfo.ui.screen.countrylist.components.NameInputFilter
import com.cmunaro.countryinfo.ui.shared.GenericError
import com.cmunaro.countryinfo.ui.shared.Loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CountryListScreen(
    navController: NavHostController,
    handleAction: (CountryListAction) -> Unit,
    viewModelStateFlow: StateFlow<CountryListScreenState>
) {
    val state: CountryListScreenState by viewModelStateFlow.collectAsState()
    LaunchedEffect(Unit) {
        handleAction(CountryListAction.FetchCountries)
    }

    if (state.error) {
        GenericError { handleAction(CountryListAction.FetchCountries) }
    } else {
        Column {
            NameInputFilter(
                filter = state.filterName,
                onChange = { handleAction(CountryListAction.ChangeNameFilter(it)) },
                onClear = { handleAction(CountryListAction.ClearNameFilter) }
            )
            ContinentFilter(
                filters = state.continentFilters,
                onToggle = { handleAction(CountryListAction.ToggleContinentFilter(it)) }
            )
            CountryList(navController = navController, items = state.filteredCountries)
        }
    }
    if (state.isLoading) {
        Loading()
    }
}

@Preview(showBackground = true)
@Composable
fun CountryListScreenPreview() {
    MaterialTheme {
        CountryListScreen(
            navController = rememberNavController(),
            handleAction = {},
            viewModelStateFlow = MutableStateFlow(
                CountryListScreenState(
                    isLoading = false,
                    filteredCountries = listOf(
                        CountryListDefinition("Italia", "IT"),
                        CountryListDefinition("Norvegia", "NO"),
                        CountryListDefinition("Spagna", "ES"),
                        CountryListDefinition("France", "FR"),
                    ),
                    filterName = "Asd",
                    continentFilters = listOf(
                        ContinentFilterEntry("Europe", true),
                        ContinentFilterEntry("Asia", false)
                    )
                )
            )
        )
    }
}