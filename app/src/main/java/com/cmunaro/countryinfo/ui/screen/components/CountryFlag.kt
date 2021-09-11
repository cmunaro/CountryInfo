package com.cmunaro.countryinfo.ui.screen.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.TextUnit
import com.cmunaro.countryinfo.utils.countryCodeToEmojiFlag

@Composable
fun CountryFlag(countryCode: String, fontSize: TextUnit) {
    val emojiFlag = remember { countryCodeToEmojiFlag(countryCode) }
    Text(emojiFlag, fontSize = fontSize)
}