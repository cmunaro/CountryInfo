package com.cmunaro.countryinfo.ui.screen.countrylist

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmunaro.countryinfo.ui.theme.CountryInfoTheme

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

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
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