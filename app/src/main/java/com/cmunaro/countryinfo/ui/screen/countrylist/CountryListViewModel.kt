package com.cmunaro.countryinfo.ui.screen.countrylist

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo.exception.ApolloException
import com.cmunaro.countryinfo.GetCountriesQuery
import com.cmunaro.countryinfo.data.CountriesService
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CountryListViewModel(
    private val countriesService: CountriesService
) : ViewModel() {
    private val _state = MutableStateFlow(CountryListScreenState())
    val state: StateFlow<CountryListScreenState> = _state

    init {
        loadData()
    }

    fun loadData() = viewModelScope.launch {
        _state.value = _state.value.copy(isLoading = true)
        val deferredCountries = async { getCountries() }
        val deferredContinents = async { getContinents() }
        val fetchedCountries = deferredCountries.await()
        val fetchedContinents = deferredContinents.await()
        val continentFilters = fetchedContinents.map { it.name }
            .sorted()
            .map { ContinentFilterEntry(it) }
        _state.value = _state.value.copy(
            isLoading = false,
            fetchedCountries = fetchedCountries,
            filteredCountries = fetchedCountries.toListOfCountryListDefinition(),
            filterName = "",
            continentFilters = continentFilters
        )
    }

    private suspend fun getCountries() = try {
        countriesService.getCountries() ?: emptyList()
    } catch (exception: ApolloException) {
        emptyList()
    }

    private suspend fun getContinents() = try {
        countriesService.getContinents() ?: emptyList()
    } catch (exception: ApolloException) {
        emptyList()
    }


    @Stable
    fun changeNameFilter(filter: String) {
        _state.value = _state.value.copy(
            filterName = filter,
            filteredCountries = getFilteredCountries(filter, _state.value.continentFilters)
        )
    }

    @Stable
    fun clearNameFilter() {
        _state.value = _state.value.copy(
            filterName = "",
            filteredCountries = getFilteredCountries("", _state.value.continentFilters)
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
            filteredCountries = getFilteredCountries(_state.value.filterName, newFilters)
        )
    }

    private fun getFilteredCountries(
        nameFilter: String,
        continentFilter: List<ContinentFilterEntry>
    ): List<CountryListDefinition> {
        val enabledContinents = continentFilter
            .filter { it.enable }
            .map { it.name }
        return _state.value.fetchedCountries
            .filter { country ->
                country.name.startsWith(nameFilter, ignoreCase = true)
            }
            .filter { country ->
                country.continent.name in enabledContinents
            }
            .toListOfCountryListDefinition()
            .sortedBy { it.name }
    }
}

private fun List<GetCountriesQuery.Country>?.toListOfCountryListDefinition(): List<CountryListDefinition> =
    this
        ?.map { CountryListDefinition(it.name, it.code) }
        ?: emptyList()

@Stable
data class CountryListDefinition(
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
    val fetchedCountries: List<GetCountriesQuery.Country> = emptyList(),
    val filteredCountries: List<CountryListDefinition> = emptyList(),
    val filterName: String = "",
    val continentFilters: List<ContinentFilterEntry> = emptyList()
)