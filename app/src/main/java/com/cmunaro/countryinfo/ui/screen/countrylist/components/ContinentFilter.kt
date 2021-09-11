package com.cmunaro.countryinfo.ui.screen.countrylist.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.cmunaro.countryinfo.ui.screen.countrylist.ContinentFilterEntry
import com.cmunaro.countryinfo.ui.shared.Chip
import com.cmunaro.countryinfo.ui.shared.FlowLayout

@Composable
fun ContinentFilter(
    filters: List<ContinentFilterEntry>,
    onToggle: (ContinentFilterEntry) -> Unit
) {
    FlowLayout(
        modifier = Modifier
            .padding(8.dp),
        spacing = 8.dp
    ) {
        filters.forEach {
            Chip(text = it.name, isEnabled = it.enable) {
                onToggle(it)
            }
        }
    }
}