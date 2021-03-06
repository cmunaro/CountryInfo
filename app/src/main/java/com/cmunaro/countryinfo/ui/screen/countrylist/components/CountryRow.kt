package com.cmunaro.countryinfo.ui.screen.countrylist.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListDefinition
import com.cmunaro.countryinfo.ui.shared.CountryFlag
import com.cmunaro.countryinfo.ui.theme.CountryInfoTheme

@Composable
fun CountryRow(
    country: CountryListDefinition,
    onClick: () -> Unit
) {
    val fontSize = 30.sp
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        CountryFlag(country.countryCode, fontSize)
        Spacer(modifier = Modifier.width(20.dp))
        Text(
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            text = country.name,
            fontSize = fontSize,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CountryRowPreview() {
    CountryInfoTheme {
        CountryRow(country = CountryListDefinition("Italia", "it")) {}
    }
}
