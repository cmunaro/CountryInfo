package com.cmunaro.countryinfo

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.cmunaro.countryinfo.ui.screen.countrylist.ContinentFilterEntry
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListDefinition
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListScreen
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class CountryListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun populatedNameFilter() {
        setScreen(filterName = "Asd")

        composeTestRule
            .onNodeWithText("Country name")
        composeTestRule
            .onNodeWithText("Asd")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("Clear")
            .assertIsDisplayed()
            .performClick()
    }

    @Test
    fun emptyNameFilter() {
        setScreen(filterName = "")

        composeTestRule
            .onNodeWithText("Country name")
        composeTestRule
            .onNodeWithContentDescription("Clear")
            .assertDoesNotExist()
    }

    @Test
    fun showCountryList() {
        setScreen()

        composeTestRule
            .onNodeWithText("Italia")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Norvegia")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Spagna")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("France")
            .assertIsDisplayed()
    }

    @Test
    fun errorState() {
        setScreen(error = true)

        composeTestRule
            .onNodeWithText("Something went wrong")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Retry")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Country name")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithText("Asie")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithText("Europe")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithText("Italia")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithText("Norvegia")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithText("Spagna")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithText("France")
            .assertDoesNotExist()
    }

    @Test
    fun emptyCountryList() {
        setScreen(emptyList = true)

        composeTestRule
            .onNodeWithText("No country matches these filters")
            .assertIsDisplayed()
    }

    private fun setScreen(
        filterName: String = "",
        emptyList: Boolean = false,
        error: Boolean = false
    ) {
        composeTestRule.setContent {
            CountryListScreen(
                navController = rememberNavController(),
                handleAction = {},
                viewModelStateFlow = MutableStateFlow(
                    dummyCountryListScreenState.copy(
                        filterName = filterName,
                        error = error,
                        filteredCountries = if (emptyList) emptyList()
                        else dummyCountryListScreenState.filteredCountries
                    )
                )
            )
        }
    }
}

private val dummyCountryListScreenState = CountryListScreenState(
    isLoading = false,
    filteredCountries = listOf(
        CountryListDefinition("Italia", "IT"),
        CountryListDefinition("Norvegia", "NO"),
        CountryListDefinition("Spagna", "ES"),
        CountryListDefinition("France", "FR"),
    ),
    filterName = "",
    continentFilters = listOf(
        ContinentFilterEntry("Europe", false),
        ContinentFilterEntry("Asia", false)
    )
)