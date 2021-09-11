package com.cmunaro.countryinfo.ui.screen.countrylist

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.exception.ApolloException
import com.cmunaro.countryinfo.GetCountriesQuery
import com.cmunaro.countryinfo.data.CountriesService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CountryListViewModel(
    private val countriesService: CountriesService
) : ViewModel() {
    private val _state = MutableStateFlow(CountryListScreenState())
    val state: StateFlow<CountryListScreenState> = _state
    private var countries: List<GetCountriesQuery.Country> = emptyList()

    init {
        fetchCountries()
    }

    fun fetchCountries() = viewModelScope.launch {
        _state.value = _state.value.copy(isLoading = true)
        val fetchedCountries = try {
            countries = countriesService.getCountries() ?: emptyList()
            countries.toListOfCountryListEntry()
        } catch (exception: ApolloException) {
            emptyList()
        }.sortedBy { it.name }
        val continentFilters = countries.map { it.continent.name }
            .distinct()
            .sorted()
            .map { ContinentFilterEntry(it) }
        _state.value = _state.value.copy(
            isLoading = false,
            countries = fetchedCountries,
            filterName = "",
            continentFilters = continentFilters
        )
    }

    @Stable
    fun changeNameFilter(filter: String) {
        _state.value = _state.value.copy(
            filterName = filter,
            countries = getCountries(filter, _state.value.continentFilters)
        )
    }

    @Stable
    fun clearNameFilter() {
        _state.value = _state.value.copy(
            filterName = "",
            countries = getCountries("", _state.value.continentFilters)
        )
    }

    @Stable
    fun toggleFilter(filterToBeToggled: ContinentFilterEntry) {
        val newFilters = _state.value.continentFilters
            .map {
                when (it.name) {
                    filterToBeToggled.name -> it.copy(enable = !it.enable)
                    else -> it
                }
            }
        _state.value = _state.value.copy(
            continentFilters = newFilters,
            countries = getCountries(_state.value.filterName, newFilters)
        )
    }

    private fun getCountries(
        nameFilter: String,
        continentFilter: List<ContinentFilterEntry>
    ): List<CountryListEntry> {
        val enabledContinents = continentFilter
            .filter { it.enable }
            .map { it.name }
        return countries
            .filter { country ->
                country.name.startsWith(nameFilter, ignoreCase = true)
            }
            .filter { country ->
                country.continent.name in enabledContinents
            }
            .toListOfCountryListEntry()
            .sortedBy { it.name }
    }
}

private fun List<GetCountriesQuery.Country>?.toListOfCountryListEntry(): List<CountryListEntry> =
    this
        ?.map { CountryListEntry(it.name, it.code) }
        ?: emptyList()

@Stable
data class CountryListEntry(
    val name: String,
    val countryCode: String
)

@Stable
data class ContinentFilterEntry(
    val name: String,
    val enable: Boolean = true
)

@Stable
data class CountryListScreenState(
    val isLoading: Boolean = false,
    val countries: List<CountryListEntry> = emptyList(),
    val filterName: String = "",
    val continentFilters: List<ContinentFilterEntry> = emptyList()
)