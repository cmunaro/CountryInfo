package com.cmunaro.countryinfo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListEntry
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListState
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListViewModel
import com.cmunaro.countryinfo.ui.theme.CountryInfoTheme
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: CountryListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchCountries()
        setContent {
            CountryInfoTheme {
                Surface(color = MaterialTheme.colors.background) {
                    CountryListScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun CountryListScreen(viewModel: CountryListViewModel) {
    val countryListState: CountryListState by viewModel.state
    Loading(isVisible = countryListState.isLoading)
    CountryList(items = countryListState.countries)
}

@Composable
fun Loading(isVisible: Boolean) {

}

@Composable
fun CountryList(items: List<CountryListEntry>) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp)
    ) {
        items(items) { country ->
            CountryRow(country)
        }
    }
}

@Composable
fun CountryRow(country: CountryListEntry) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(country.name)
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CountryInfoTheme {
        CountryRow(country = CountryListEntry("Italia"))
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CountryInfoTheme {
        CountryList(
            listOf(
                CountryListEntry("Italia"),
                CountryListEntry("Norvegia"),
                CountryListEntry("Spagna"),
                CountryListEntry("France"),
            )
        )
    }
}