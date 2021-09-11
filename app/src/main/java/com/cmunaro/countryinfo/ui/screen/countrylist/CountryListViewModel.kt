package com.cmunaro.countryinfo.ui.screen.countrylist

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
    private val _state = mutableStateOf(CountryListState())
    val state: State<CountryListState> = _state

    fun fetchCountries() = viewModelScope.launch {
        _state.value = _state.value.copy(isLoading = true)
        val fetchedCountries = try {
            countriesService.getCountries().toListOfCountryListEntry()
        } catch (exception: ApolloException) {
            emptyList()
        }
        _state.value = _state.value.copy(
            isLoading = false,
            countries = fetchedCountries
        )
    }
}

private fun List<GetCountriesQuery.Country>?.toListOfCountryListEntry(): List<CountryListEntry> =
    this
        ?.map { CountryListEntry(it.name) }
        ?: emptyList()

data class CountryListEntry(
    val name: String
)

data class CountryListState(
    val isLoading: Boolean = true,
    val countries: List<CountryListEntry> = emptyList()
)