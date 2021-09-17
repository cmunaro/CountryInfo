package com.cmunaro.countryinfo.ui.screen.countrydetails.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cmunaro.countryinfo.R
import com.cmunaro.countryinfo.ui.screen.countrydetails.CountryDefinition

@Composable
fun CountryInfo(country: CountryDefinition) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "${country.name} (${country.countryCode})",
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(4.dp)
                .align(CenterHorizontally)
        )
        CountryExpandableInfo(title = stringResource(R.string.continent), country.continent)
        CountryExpandableInfo(title = stringResource(R.string.capital), country.capital)
        CountryExpandableInfo(title = stringResource(R.string.currency), country.currency)
        CountryExpandableInfo(
            title = stringResource(R.string.language),
            *country.languages.toTypedArray()
        )
        CountryExpandableInfo(title = stringResource(R.string.phone), country.phone)
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