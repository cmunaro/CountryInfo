package com.cmunaro.countryinfo.ui.screen.countrylist.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cmunaro.countryinfo.Route
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListEntry
import com.cmunaro.countryinfo.ui.theme.CountryInfoTheme

@Composable
fun CountryList(
    navController: NavController,
    items: List<CountryListEntry>
) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            items,
            key = { it.countryCode }
        ) { country ->
            CountryRow(country) {
                navController.navigate(
                    Route.CountryDetails.path
                        .plus("/${country.countryCode}")
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountryListPreview() {
    CountryInfoTheme {
        CountryList(
            rememberNavController(),
            listOf(
                CountryListEntry("Italia", "IT"),
                CountryListEntry("Norvegia", "NO"),
                CountryListEntry("Spagna", "ES"),
                CountryListEntry("France", "FR"),
            )
        )
    }
}