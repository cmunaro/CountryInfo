package com.cmunaro.countryinfo.ui.screen.countrylist.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.cmunaro.countryinfo.R
import com.cmunaro.countryinfo.Route
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListDefinition
import com.cmunaro.countryinfo.ui.theme.CountryInfoTheme

@Composable
fun CountryList(
    navController: NavController,
    items: List<CountryListDefinition>
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
    if (items.isEmpty()) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.country_list_empty_message),
            fontSize = 26.sp,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CountryListPreview() {
    CountryInfoTheme {
        CountryList(
            rememberNavController(),
            listOf(
                CountryListDefinition("Italia", "IT"),
                CountryListDefinition("Norvegia", "NO"),
                CountryListDefinition("Spagna", "ES"),
                CountryListDefinition("France", "FR"),
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyCountryListPreview() {
    CountryInfoTheme {
        CountryList(
            rememberNavController(),
            listOf()
        )
    }
}