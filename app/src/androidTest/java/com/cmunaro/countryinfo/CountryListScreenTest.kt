package com.cmunaro.countryinfo

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.rememberNavController
import com.cmunaro.countryinfo.ui.screen.countrylist.ContinentFilterEntry
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListEntry
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListScreen
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class CountryListScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun ifThereIsTextsOnTheSearchBarTheClearIconIsVisible() {
        setScreen(filterName = "Asd")

        composeTestRule
            .onNodeWithText("Asd")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithContentDescription("Clear")
            .assertIsDisplayed()
            .performClick()
    }

    @Test
    fun ifThereIsNotTextsOnTheSearchBarTheClearIconIsInvisible() {
        setScreen(filterName = "")

        composeTestRule
            .onNodeWithContentDescription("Clear")
            .assertDoesNotExist()
    }

    private fun setScreen(filterName: String = "") {
        composeTestRule.setContent {
            CountryListScreen(
                navController = rememberNavController(),
                viewModelStateFlow = MutableStateFlow(
                    dummyCountryListScreenState.copy(
                        filterName = filterName
                    )
                ),
                onChangeNameFilter = {},
                onClearNameFilter = {},
                onToggleContinentFilter = {}
            )
        }
    }
}

private val dummyCountryListScreenState = CountryListScreenState(
    isLoading = false,
    countries = listOf(
        CountryListEntry("Italia", "IT"),
        CountryListEntry("Norvegia", "NO"),
        CountryListEntry("Spagna", "ES"),
        CountryListEntry("France", "FR"),
    ),
    filterName = "",
    continentFilters = listOf(
        ContinentFilterEntry("Europe", false),
        ContinentFilterEntry("Asia", false)
    )
)