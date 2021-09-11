package com.cmunaro.countryinfo

import app.cash.turbine.test
import com.cmunaro.countryinfo.data.CountriesService
import com.cmunaro.countryinfo.ui.screen.countrylist.ContinentFilterEntry
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListEntry
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListScreenState
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListViewModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.mock.MockProviderRule
import org.koin.test.mock.declareMock
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
@ExperimentalCoroutinesApi
class CountryListviewModelTest : KoinTest {
    private lateinit var countriesService: CountriesService
    private lateinit var viewModel: CountryListViewModel

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Before
    fun setup() {
        startKoin {}
        countriesService = declareMock {
            given(runBlocking { getCountries() }).willReturn(stubCountries)
        }

        viewModel = CountryListViewModel(countriesService)
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `fetchCountries initializes the state`() = runBlockingTest {
        viewModel.state.test {
            assertThat(awaitItem().isLoading).isFalse()

            viewModel.fetchCountries()

            assertThat(awaitItem().isLoading).isTrue()

            assertThat(awaitItem()).isEqualTo(
                CountryListScreenState(
                    isLoading = false,
                    countries = countriesSortedByName,
                    filterName = "",
                    continentFilters = continentsFilter
                )
            )
        }
    }

    @Test
    fun `filter only by name`() = runBlockingTest {
        viewModel.state.test {
            assertThat(awaitItem().isLoading).isFalse()

            viewModel.fetchCountries()

            assertThat(awaitItem().isLoading).isTrue()
            assertThat(awaitItem().isLoading).isFalse()

            viewModel.changeNameFilter("a")

            assertThat(awaitItem()).isEqualTo(
                CountryListScreenState(
                    isLoading = false,
                    countries = countriesSortedByName
                        .filter { it.name.startsWith("a", ignoreCase = true) },
                    filterName = "a",
                    continentFilters = continentsFilter
                )
            )
        }
    }

    @Test
    fun `remove name filter`() = runBlockingTest {
        viewModel.state.test {
            assertThat(awaitItem().isLoading).isFalse()

            viewModel.fetchCountries()

            assertThat(awaitItem().isLoading).isTrue()
            assertThat(awaitItem().isLoading).isFalse()

            viewModel.changeNameFilter("a")

            assertThat(awaitItem().filterName).isEqualTo("a")

            viewModel.clearNameFilter()

            assertThat(awaitItem()).isEqualTo(
                CountryListScreenState(
                    isLoading = false,
                    countries = countriesSortedByName,
                    filterName = "",
                    continentFilters = continentsFilter
                )
            )
        }
    }

    @Test
    fun `toggle continent filter`() = runBlockingTest {
        viewModel.state.test {
            assertThat(awaitItem().isLoading).isFalse()

            viewModel.fetchCountries()

            assertThat(awaitItem().isLoading).isTrue()
            assertThat(awaitItem().isLoading).isFalse()

            viewModel.toggleFilter(continentsFilter[1])

            val expectedContinentsFilter = continentsFilter.map {
                if (it.name == continentsFilter[1].name)
                    it.copy(enable = false)
                else it
            }
            val expectedEnabledContinents = expectedContinentsFilter
                .filter { it.enable }
                .map { it.name }
            val expectedCountryList = stubCountries
                .filter { it.continent.name in expectedEnabledContinents }
                .map { CountryListEntry(it.name, it.code) }
                .sortedBy { it.name }

            assertThat(awaitItem()).isEqualTo(
                CountryListScreenState(
                    isLoading = false,
                    countries = expectedCountryList,
                    filterName = "",
                    continentFilters = expectedContinentsFilter
                )
            )
        }
    }

    @Test
    fun `filter for name and continent`() = runBlockingTest {
        viewModel.state.test {
            assertThat(awaitItem().isLoading).isFalse()

            viewModel.fetchCountries()

            assertThat(awaitItem().isLoading).isTrue()
            assertThat(awaitItem().isLoading).isFalse()

            viewModel.toggleFilter(continentsFilter[0])
            awaitItem()
            viewModel.toggleFilter(continentsFilter[1])
            awaitItem()
            viewModel.changeNameFilter("a")

            val expectedContinentsFilter = continentsFilter.map {
                if (it.name in continentsFilter.subList(0, 2).map { cont -> cont.name })
                    it.copy(enable = false)
                else it
            }

            assertThat(awaitItem()).isEqualTo(
                CountryListScreenState(
                    isLoading = false,
                    countries = countriesSortedByName
                        .filter { it.countryCode in listOf("AD", "AG") },
                    filterName = "a",
                    continentFilters = expectedContinentsFilter
                )
            )
        }
    }
}

private val stubCountries = listOf(
    GetCountriesQuery.Country(
        code = "AD",
        name = "Andorra",
        continent = GetCountriesQuery.Continent(name = "Europe")
    ),
    GetCountriesQuery.Country(
        code = "AG",
        name = "Antigua and Barbuda",
        continent = GetCountriesQuery.Continent(name = "North America")
    ),
    GetCountriesQuery.Country(
        code = "AO",
        name = "Angola",
        continent = GetCountriesQuery.Continent(name = "Africa")
    ),
    GetCountriesQuery.Country(
        code = "BN",
        name = "Brunei",
        continent = GetCountriesQuery.Continent(name = "Asia")
    ),
    GetCountriesQuery.Country(
        code = "CF",
        name = "Central African Republic",
        continent = GetCountriesQuery.Continent(name = "Africa")
    ),
    GetCountriesQuery.Country(
        code = "IT",
        name = "Italy",
        continent = GetCountriesQuery.Continent(name = "Europe")
    ),
)
private val countriesSortedByName = stubCountries
    .map { CountryListEntry(it.name, it.code) }
    .sortedBy { it.name }
private val continentsFilter = stubCountries
    .map { it.continent.name }
    .distinct()
    .sorted()
    .map { ContinentFilterEntry(it) }