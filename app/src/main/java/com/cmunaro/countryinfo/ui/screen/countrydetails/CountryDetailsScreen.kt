package com.cmunaro.countryinfo.ui.screen.countrydetails

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.cmunaro.countryinfo.ui.screen.countrydetails.components.CountryInfo
import com.cmunaro.countryinfo.ui.shared.Loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun CountryDetailsScreen(
    viewModelStateFlow: StateFlow<CountryDetailsState>
) {
    val state by viewModelStateFlow.collectAsState()
    state.country?.let { country ->
        CountryInfo(country = country)
    }
    Loading(isVisible = state.isLoading)
}

@Preview(showBackground = true)
@Composable
fun CountryDetailsScreenPreview() {
    MaterialTheme {
        CountryDetailsScreen(
            viewModelStateFlow = MutableStateFlow(
                CountryDetailsState(
                    isLoading = false,
                    error = false,
                    CountryDefinition(
                        "IT",
                        "Italy",
                        "Rome",
                        "Europe",
                        "Euro",
                        listOf("Italian"),
                        "39"
                    )
                )
            )
        )
    }
}