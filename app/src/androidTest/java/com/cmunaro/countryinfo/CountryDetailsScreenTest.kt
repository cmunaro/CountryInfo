package com.cmunaro.countryinfo

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.cmunaro.countryinfo.ui.screen.countrydetails.CountryDefinition
import com.cmunaro.countryinfo.ui.screen.countrydetails.CountryDetailsScreen
import com.cmunaro.countryinfo.ui.screen.countrydetails.CountryDetailsState
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test

class CountryDetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun countryDetailsScreenMainContent() {
        setContent()

        composeTestRule
            .onNodeWithText("Italy (IT)")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Continent")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Capital")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Currency")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithText("Language")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Phone")
            .assertIsDisplayed()
    }

    @Test
    fun toggleCard() {
        setContent()

        composeTestRule
            .onNodeWithText("Rome")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithText("Capital")
            .assertIsDisplayed()
            .performClick()
        composeTestRule
            .onNodeWithText("Rome")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Capital")
            .assertIsDisplayed()
            .performClick()
        composeTestRule
            .onNodeWithText("Rome")
            .assertDoesNotExist()
    }

    @Test
    fun errorState() {
        setContent(error = true)

        composeTestRule
            .onNodeWithText("Something went wrong")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Retry")
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText("Italy (IT)")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithText("Continent")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithText("Capital")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithText("Currency")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithText("Language")
            .assertDoesNotExist()
        composeTestRule
            .onNodeWithText("Phone")
            .assertDoesNotExist()
    }

    private fun setContent(error: Boolean = false) {
        composeTestRule.setContent {
            CountryDetailsScreen(
                viewModelStateFlow = MutableStateFlow(
                    dummyCountryDetailsState.copy(
                        error = error
                    )
                ),
                handleAction = {}
            )
        }
    }
}

private val dummyCountryDetailsState = CountryDetailsState(
    isLoading = false,
    error = false,
    CountryDefinition(
        "IT",
        "Italy",
        "Rome",
        "Europe",
        null,
        listOf("Italian"),
        "39"
    )
)