package com.cmunaro.countryinfo.ui.screen.countrydetails

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.lifecycle.ViewModel
import com.apollographql.apollo.exception.ApolloException
import com.cmunaro.countryinfo.GetCountryInfoQuery
import com.cmunaro.countryinfo.data.CountriesService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CountryDetailsViewModel(
    private val countryCode: String,
    private val scope: CoroutineScope,
    private val countriesService: CountriesService
) : ViewModel() {
    private val _state = MutableStateFlow(CountryDetailsState())
    val state: StateFlow<CountryDetailsState> = _state

    fun handleAction(action: CountryDetailsAction) {
        when (action) {
            CountryDetailsAction.FetchInfo -> fetchInfo()
        }
    }

    private fun fetchInfo() = scope.launch {
        if (_state.value.isLoading) return@launch
        _state.value = _state.value.copy(
            isLoading = true,
            error = false
        )
        val country: CountryDefinition? = try {
            countriesService.getCountryInfo(countryCode)
                ?.toCountryDefinition()
        } catch (exception: ApolloException) {
            null
        }
        _state.value = _state.value.copy(
            error = country == null,
            isLoading = false,
            country = country
        )
    }
}

sealed interface CountryDetailsAction {
    object FetchInfo : CountryDetailsAction
}

private fun GetCountryInfoQuery.Country?.toCountryDefinition(): CountryDefinition? {
    return if (this == null) null
    else CountryDefinition(
        countryCode = code,
        name = name,
        capital = capital,
        continent = continent.name,
        currency = currency,
        languages = languages.mapNotNull { it.name },
        phone = phone
    )
}

@Immutable
data class CountryDefinition(
    val countryCode: String,
    val name: String,
    val capital: String?,
    val continent: String,
    val currency: String?,
    val languages: List<String>,
    val phone: String
)

@Stable
data class CountryDetailsState(
    val isLoading: Boolean = false,
    val error: Boolean = false,
    val country: CountryDefinition? = null,
)