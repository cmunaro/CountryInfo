package com.cmunaro.countryinfo.ui.shared

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Chip(
    text: String,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable(onClick = onClick),
        color = when {
            isEnabled -> MaterialTheme.colors.primary
            else -> Color.Transparent
        },
        contentColor = when {
            isEnabled -> MaterialTheme.colors.onPrimary
            else -> MaterialTheme.colors.onBackground
        },
        shape = CircleShape,
        border = BorderStroke(
            width = 1.dp,
            color = when {
                isEnabled -> MaterialTheme.colors.primary
                else -> MaterialTheme.colors.onBackground
            }
        ),
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.h6.copy(fontSize = 16.sp),
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChipEnabledPreview() {
    MaterialTheme {
        Chip("Hello world", true) {}
    }
}

@Preview(showBackground = true)
@Composable
fun ChipDisabledPreview() {
    MaterialTheme {
        Chip("Hello world", false) {}
    }
}