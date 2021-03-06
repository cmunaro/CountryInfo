package com.cmunaro.countryinfo

import app.cash.turbine.test
import com.cmunaro.countryinfo.data.CountriesService
import com.cmunaro.countryinfo.ui.screen.countrydetails.CountryDefinition
import com.cmunaro.countryinfo.ui.screen.countrydetails.CountryDetailsAction
import com.cmunaro.countryinfo.ui.screen.countrydetails.CountryDetailsState
import com.cmunaro.countryinfo.ui.screen.countrydetails.CountryDetailsViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.mock.MockProvider
import org.koin.test.mock.declareMock
import org.mockito.ArgumentMatchers.anyString
import org.mockito.BDDMockito.*
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@ExperimentalCoroutinesApi
class CountryDetailsViewModelTest : KoinTest {
    private lateinit var countriesService: CountriesService
    private lateinit var viewModel: CountryDetailsViewModel
    private lateinit var getCountryInfoAction: () -> GetCountryInfoQuery.Country

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Before
    fun setup() {
        startKoin {}
        MockProvider.register {
            mock(it.java)
        }
        countriesService = declareMock {
            given(runBlocking { getCountryInfo(anyString()) }).willAnswer { getCountryInfoAction() }
        }
        viewModel = CountryDetailsViewModel(
            countryCode = "",
            scope = TestCoroutineScope(),
            countriesService = countriesService
        )
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `Fetch info happy path`() = runBlockingTest {
        getCountryInfoAction = { countryStub }

        viewModel.state.test {
            assertThat(awaitItem().isLoading).isFalse()

            viewModel.handleAction(CountryDetailsAction.FetchInfo)

            val loadingState = awaitItem()
            assertThat(loadingState.isLoading).isTrue()
            assertThat(loadingState.error).isFalse()

            assertThat(awaitItem()).isEqualTo(
                CountryDetailsState(
                    isLoading = false,
                    error = false,
                    country = CountryDefinition(
                        name = countryStub.name,
                        capital = countryStub.capital,
                        countryCode = countryStub.code,
                        continent = countryStub.continent.name,
                        currency = countryStub.currency,
                        languages = countryStub.languages.map { it.name!! },
                        phone = countryStub.phone
                    )
                )
            )
        }
    }

    @Test
    fun `Fetch info error`() = runBlockingTest {
        getCountryInfoAction = { throw Exception() }

        viewModel.state.test {
            assertThat(awaitItem().isLoading).isFalse()

            viewModel.handleAction(CountryDetailsAction.FetchInfo)

            assertThat(awaitItem().isLoading).isTrue()

            assertThat(awaitItem()).isEqualTo(
                CountryDetailsState(
                    isLoading = false,
                    error = true,
                    country = null
                )
            )
        }
    }
}

val countryStub = GetCountryInfoQuery.Country(
    code = "IT",
    name = "Italy",
    continent = GetCountryInfoQuery.Continent(name = "Europe"),
    native_ = "Italia",
    phone = "39",
    capital = "Rome",
    currency = "Eur",
    languages = listOf(GetCountryInfoQuery.Language(name = "Italian", native_ = "Italiano")),
)