package com.cmunaro.countryinfo.ui.screen.countrydetails.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun CountryExpandableInfo(
    title: String,
    vararg info: String?
) {
    if (info.filterNotNull().isEmpty()) return
    val isExpanded = remember { mutableStateOf(false) }
    ExpandableCard(
        title = title,
        expanded = isExpanded.value,
        onCardHeaderClick = { isExpanded.value = !isExpanded.value }) {
        info.filterNotNull().forEach {
            Text(text = it)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CountryExpandableInfo() {
    MaterialTheme {
        CountryExpandableInfo("title", "content")
    }
}