package com.cmunaro.countryinfo.ui.screen.countrylist.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListEntry
import com.cmunaro.countryinfo.ui.theme.CountryInfoTheme

@Composable
fun CountryList(items: List<CountryListEntry>) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp)
    ) {
        items(
            items,
            key = { it.countryCode }
        ) { country ->
            CountryRow(country)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountryListPreview() {
    CountryInfoTheme {
        CountryList(
            listOf(
                CountryListEntry("Italia", "IT"),
                CountryListEntry("Norvegia", "NO"),
                CountryListEntry("Spagna", "ES"),
                CountryListEntry("France", "FR"),
            )
        )
    }
}