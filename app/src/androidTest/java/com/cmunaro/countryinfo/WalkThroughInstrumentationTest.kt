package com.cmunaro.countryinfo

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.compose.rememberNavController
import com.cmunaro.countryinfo.di.networkModule
import com.cmunaro.countryinfo.fakes.FakeCountriesServiceImpl
import com.cmunaro.countryinfo.fakes.fakeNetworkModule
import com.cmunaro.countryinfo.ui.screen.countrylist.components.TAG_TEXT_FIELD_COUNTRY_NAME_FILTER
import com.cmunaro.countryinfo.ui.theme.CountryInfoTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules

class WalkThroughInstrumentationTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun setup() {
        unloadKoinModules(networkModule)
        loadKoinModules(fakeNetworkModule)
        setupContent()
    }

    @Test
    fun countryListFilters() {
        step("check unfiltered list") {
            composeTestRule.onNodeWithText("Italy").assertIsDisplayed()
            composeTestRule.onNodeWithText("France").assertIsDisplayed()
            composeTestRule.onNodeWithText("Algeria").assertIsDisplayed()
        }
        step("filter by continent") {
            composeTestRule.onNodeWithText("Africa").assertIsDisplayed()
                .performClick()
            composeTestRule.waitForIdle()
            composeTestRule.onNodeWithText("Algeria").assertDoesNotExist()
            composeTestRule.onNodeWithText("Europe").performClick()
            composeTestRule.waitForIdle()
            composeTestRule.onNodeWithText("No country matches these filters")
                .assertIsDisplayed()
        }
        composeTestRule.onNodeWithText("Europe").performClick()
        composeTestRule.onNodeWithText("Africa").performClick()
        composeTestRule.waitForIdle()
        step("filter by name") {
            composeTestRule.onNodeWithText("Algeria").assertIsDisplayed()
            composeTestRule.onNodeWithTag(TAG_TEXT_FIELD_COUNTRY_NAME_FILTER)
                .assertIsDisplayed()
                .performTextInput("i")
            composeTestRule.waitForIdle()
            composeTestRule.onNodeWithText("France").assertDoesNotExist()
            composeTestRule.onNodeWithText("Algeria").assertDoesNotExist()
            composeTestRule.onNodeWithText("Italy").assertIsDisplayed()
        }
        step("clear name filter and keep countries only in Africa") {
            composeTestRule.onNodeWithText("Europe").performClick()
            composeTestRule.onNodeWithText("No country matches these filters")
                .assertIsDisplayed()
            composeTestRule.onNodeWithContentDescription("Clear").assertIsDisplayed()
                .performClick()
            composeTestRule.waitForIdle()
            composeTestRule.onNodeWithText("Algeria").assertIsDisplayed()
            composeTestRule.onNodeWithText("Italy").assertDoesNotExist()
            composeTestRule.onNodeWithText("France").assertDoesNotExist()
        }
    }

    @Test
    fun countryDetails() {
        step("go to Italy details") {
            composeTestRule.onNodeWithText("Italy").assertIsDisplayed()
                .performClick()
            composeTestRule.waitForIdle()
            composeTestRule.onNodeWithText("Italy (IT)").assertIsDisplayed()
        }
        step("toggle continent visibility") {
            composeTestRule.onNodeWithText("Europe").assertDoesNotExist()
            composeTestRule.onNodeWithText("Continent").assertIsDisplayed()
                .performClick()
            composeTestRule.waitForIdle()
            composeTestRule.onNodeWithText("Europe").assertIsDisplayed()
            composeTestRule.onNodeWithText("Continent").assertIsDisplayed()
                .performClick()
            composeTestRule.waitForIdle()
            composeTestRule.onNodeWithText("Europe").assertDoesNotExist()
        }
    }

    private fun setupContent() {
        FakeCountriesServiceImpl.countries = dummyCountries
        FakeCountriesServiceImpl.continents = dummyContinents
        FakeCountriesServiceImpl.countryInfo = dummyCountryInfo

        composeTestRule.setContent {
            val navController = rememberNavController()
            CountryInfoTheme {
                Surface(color = MaterialTheme.colors.background) {
                    CountryInfoNavHost(navController)
                }
            }
        }
    }
}

val dummyCountries: List<GetCountriesQuery.Country> = listOf(
    GetCountriesQuery.Country(
        code = "IT",
        name = "Italy",
        continent = GetCountriesQuery.Continent(name = "Europe")
    ),
    GetCountriesQuery.Country(
        code = "FR",
        name = "France",
        continent = GetCountriesQuery.Continent(name = "Europe")
    ),
    GetCountriesQuery.Country(
        code = "DZ",
        name = "Algeria",
        continent = GetCountriesQuery.Continent(name = "Africa")
    ),
)
val dummyContinents: List<GetContinentsQuery.Continent> = listOf(
    GetContinentsQuery.Continent(code = "EU", name = "Europe"),
    GetContinentsQuery.Continent(code = "AF", name = "Africa")
)
val dummyCountryInfo: GetCountryInfoQuery.Country =
    GetCountryInfoQuery.Country(
        code = "IT",
        name = "Italy",
        phone = "39",
        continent = GetCountryInfoQuery.Continent(name = "Europe"),
        capital = "Rome",
        currency = "EURO",
        languages = listOf(GetCountryInfoQuery.Language(name = "Italian", native_ = "Italiano")),
        native_ = "Italia"
    )