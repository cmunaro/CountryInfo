package com.cmunaro.countryinfo.ui.screen.countrylist

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction.Companion.Search
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun NameInputFilter(filter: String, onChange: (String) -> Unit) {
    val focusManager = LocalFocusManager.current
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        singleLine = true,
        value = filter,
        onValueChange = onChange,
        label = { Text("Country name") },
        keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = Search,
            keyboardType = KeyboardType.Text
        )
    )
}
