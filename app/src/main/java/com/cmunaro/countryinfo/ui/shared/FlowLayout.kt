package com.cmunaro.countryinfo.ui.shared

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

@Composable
fun FlowLayout(
    modifier: Modifier = Modifier,
    spacing: Dp,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier
    ) { measurables, constraints ->
        var row = 0
        var origin = IntOffset.Zero
        val spacingValue = spacing.toPx().toInt()
        val placeables = measurables.map { measurable ->
            val placeable = measurable.measure(constraints)

            if (origin.x + placeable.width > constraints.maxWidth) {
                row += 1
                origin = origin.copy(
                    x = 0,
                    y = origin.y + placeable.height + spacingValue
                )
            }

            placeable to origin.also {
                origin = it.copy(x = it.x + placeable.width + spacingValue)
            }
        }

        layout(
            width = constraints.maxWidth,
            height = placeables.lastOrNull()?.run { first.height + second.y } ?: 0
        ) {
            placeables.forEach {
                val (placeable, origin) = it
                placeable.place(origin.x, origin.y)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChipGroupPreview() {
    val words = listOf(
        "hello", "world", "hello", "world", "hello", "world", "hello",
        "hello", "world", "hello", "world", "hello", "world", "hello",
    )
    FlowLayout(
        spacing = 8.dp,
        modifier = Modifier.padding(8.dp)
    ) {
        words.forEach { word ->
            Chip(word, true) {}
        }
    }
}