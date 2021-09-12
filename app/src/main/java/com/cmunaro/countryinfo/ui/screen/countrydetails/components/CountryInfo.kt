package com.cmunaro.countryinfo.ui.screen.countrydetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmunaro.countryinfo.ui.screen.countrydetails.CountryDefinition

@Composable
fun CountryInfo(country: CountryDefinition) {
    Column {
        Text(
            text = "${country.name} (${country.countryCode})",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(4.dp)
        )
        CountryExpandableInfo(title = "Continent", country.continent)
        CountryExpandableInfo(title = "Capital", country.capital)
        CountryExpandableInfo(title = "Currency", country.currency)
        CountryExpandableInfo(title = "Language", *country.languages.toTypedArray())
        CountryExpandableInfo(title = "Phone", country.phone)
    }
}

@Preview(showBackground = true)
@Composable
fun CountryInfoPreview() {
    MaterialTheme {
        CountryInfo(
            country = CountryDefinition(
                "IT",
                "Italy",
                "Rome",
                "Europe",
                "Euro",
                listOf("Italian"),
                "39"
            )
        )
    }
}