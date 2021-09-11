package com.cmunaro.countryinfo.ui.screen.countrylist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction.Companion.Search
import androidx.compose.ui.text.input.KeyboardType
import com.cmunaro.countryinfo.R

@Composable
fun NameInputFilter(filter: String, onChange: (String) -> Unit, onClear: () -> Unit) {
    val focusManager = LocalFocusManager.current
    TextField(
        modifier = Modifier
            .fillMaxWidth(),
        singleLine = true,
        value = filter,
        onValueChange = onChange,
        label = { Text(stringResource(R.string.coutry_name_label)) },
        keyboardActions = KeyboardActions(onSearch = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = Search,
            keyboardType = KeyboardType.Text
        ),
        trailingIcon = {
            if (filter.isNotEmpty()) {
                Icon(
                    imageVector = Icons.Filled.Clear,
                    contentDescription = stringResource(R.string.description_clear_name_filter),
                    modifier = Modifier.clickable {
                        onClear.invoke()
                        focusManager.clearFocus()
                    }
                )
            }
        }
    )
}
