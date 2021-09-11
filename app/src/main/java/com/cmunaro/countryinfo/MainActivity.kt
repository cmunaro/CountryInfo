package com.cmunaro.countryinfo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListEntry
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListState
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListViewModel
import com.cmunaro.countryinfo.ui.theme.CountryInfoTheme
import com.cmunaro.countryinfo.utils.countryCodeToEmojiFlag
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    private val viewModel: CountryListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.fetchCountries()
        setContent {
            CountryInfoTheme {
                Surface(color = MaterialTheme.colors.background) {
                    CountryListScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun CountryListScreen(viewModel: CountryListViewModel) {
    val countryListState: CountryListState by viewModel.state
    CountryList(items = countryListState.countries)
    Loading(isVisible = countryListState.isLoading)
}

@Composable
fun Loading(isVisible: Boolean) {
    if (!isVisible) return
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Gray.copy(alpha = 0.7f))
            .clickable(enabled = false) { },

        ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun CountryList(items: List<CountryListEntry>) {
    LazyColumn(
        contentPadding = PaddingValues(8.dp)
    ) {
        items(items) { country ->
            CountryRow(country)
        }
    }
}

@Composable
fun CountryRow(country: CountryListEntry) {
    val fontSize = MaterialTheme.typography.h4.fontSize
    Row(
        modifier = Modifier.fillMaxWidth()
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

@Composable
fun CountryFlag(countryCode: String, fontSize: TextUnit) {
    val emojiFlag = remember { countryCodeToEmojiFlag(countryCode) }
    Text(emojiFlag, fontSize = fontSize)
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    CountryInfoTheme {
        CountryRow(country = CountryListEntry("Italia", "it"))
    }
}

@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CountryInfoTheme {
        CountryList(
            listOf(
                CountryListEntry("Italia", "IT"),
                CountryListEntry("Norvegia", "NO"),
                CountryListEntry("Spagna", "ES"),
                CountryListEntry("France", "FR"),
            )
        )
    }
}