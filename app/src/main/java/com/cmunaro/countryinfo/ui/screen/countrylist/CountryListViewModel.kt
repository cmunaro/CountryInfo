package com.cmunaro.countryinfo.ui.screen.countrylist

import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.exception.ApolloException
import com.cmunaro.countryinfo.GetCountriesQuery
import com.cmunaro.countryinfo.data.CountriesService
import kotlinx.coroutines.launch

class CountryListViewModel(
    private val countriesService: CountriesService
) : ViewModel() {
    private val _state = mutableStateOf(CountryListScreenState())
    val state: State<CountryListScreenState> = _state
    private var countries: List<GetCountriesQuery.Country> = emptyList()

    fun fetchCountries() = viewModelScope.launch {
        _state.value = _state.value.copy(isLoading = true)
        val fetchedCountries = try {
            countries = countriesService.getCountries() ?: emptyList()
            countries.toListOfCountryListEntry()
        } catch (exception: ApolloException) {
            emptyList()
        }
        _state.value = _state.value.copy(
            isLoading = false,
            countries = fetchedCountries.sortedBy { it.name }
        )
    }

    @Stable
    fun changeFilter(filter: String) {
        _state.value = _state.value.copy(
            filter = filter,
            countries = countries
                .toListOfCountryListEntry()
                .filter { it.name.startsWith(filter, ignoreCase = true) }
        )
    }

    @Stable
    fun clearFilter() {
        _state.value = _state.value.copy(
            filter = "",
            countries = countries
                .toListOfCountryListEntry()
        )
    }
}

private fun List<GetCountriesQuery.Country>?.toListOfCountryListEntry(): List<CountryListEntry> =
    this
        ?.map { CountryListEntry(it.name, it.code) }
        ?: emptyList()

data class CountryListEntry(
    val name: String,
    val countryCode: String
)

@Stable
data class CountryListScreenState(
    val isLoading: Boolean = true,
    val countries: List<CountryListEntry> = emptyList(),
    val filter: String = ""
)