package com.cmunaro.countryinfo.ui.screen.countrylist

import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.exception.ApolloException
import com.cmunaro.countryinfo.GetContinentsQuery
import com.cmunaro.countryinfo.GetCountriesQuery
import com.cmunaro.countryinfo.data.CountriesService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CountryListViewModel(
    private val scope: CoroutineScope,
    private val countriesService: CountriesService
) : ViewModel() {
    private val _state = MutableStateFlow(CountryListScreenState())
    val state: StateFlow<CountryListScreenState> = _state

    fun handleAction(action: CountryListAction) {
        when (action) {
            CountryListAction.FetchCountries -> fetchCountries()
            is CountryListAction.ChangeNameFilter -> changeNameFilter(action.filter)
            CountryListAction.ClearNameFilter -> clearNameFilter()
            is CountryListAction.ToggleContinentFilter -> toggleFilter(action.filter)
        }
    }

    private fun fetchCountries() = scope.launch {
        _state.value = _state.value.copy(isLoading = true)
        val deferredCountries = async { getCountries() }
        val deferredContinents = async { getContinents() }
        val fetchedCountries = deferredCountries.await()
        val fetchedContinents = deferredContinents.await()
        val continentFilters = fetchedContinents?.map { it.name }
            ?.sorted()
            ?.map { ContinentFilterEntry(it) }
        _state.value = _state.value.copy(
            isLoading = false,
            error = fetchedCountries == null || fetchedContinents == null,
            fetchedCountries = fetchedCountries?: emptyList(),
            filteredCountries = fetchedCountries.toListOfCountryListDefinition(),
            filterName = "",
            continentFilters = continentFilters?: emptyList()
        )
    }

    private suspend fun getCountries(): List<GetCountriesQuery.Country>? = try {
        countriesService.getCountries()
    } catch (_: Exception) {
        null
    }

    private suspend fun getContinents(): List<GetContinentsQuery.Continent>? = try {
        countriesService.getContinents()
    } catch (_: Exception) {
        null
    }


    @Stable
    private fun changeNameFilter(filter: String) {
        _state.value = _state.value.copy(
            filterName = filter,
            filteredCountries = getFilteredCountries(filter, _state.value.continentFilters)
        )
    }

    @Stable
    private fun clearNameFilter() {
        _state.value = _state.value.copy(
            filterName = "",
            filteredCountries = getFilteredCountries("", _state.value.continentFilters)
        )
    }

    @Stable
    private fun toggleFilter(filterToBeToggled: ContinentFilterEntry) {
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

sealed interface CountryListAction {
    object FetchCountries : CountryListAction
    data class ChangeNameFilter(val filter: String) : CountryListAction
    object ClearNameFilter : CountryListAction
    data class ToggleContinentFilter(val filter: ContinentFilterEntry) : CountryListAction
}

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
    val error: Boolean = false,
    val fetchedCountries: List<GetCountriesQuery.Country> = emptyList(),
    val filteredCountries: List<CountryListDefinition> = emptyList(),
    val filterName: String = "",
    val continentFilters: List<ContinentFilterEntry> = emptyList()
)