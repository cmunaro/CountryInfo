package com.cmunaro.countryinfo.ui.shared

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cmunaro.countryinfo.R

@Composable
fun GenericError(retryAction: (() -> Unit)? = null) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Center,
            text = stringResource(R.string.generic_error_message),
            fontSize = 26.sp,
        )
        if (retryAction != null) {
            Button(onClick = retryAction) {
                Text(text = stringResource(R.string.retry_button_text))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GenericErrorPreview() {
    MaterialTheme {
        GenericError()
    }
}

@Preview(showBackground = true)
@Composable
fun GenericErrorWithButtonPreview() {
    MaterialTheme {
        GenericError {}
    }
}