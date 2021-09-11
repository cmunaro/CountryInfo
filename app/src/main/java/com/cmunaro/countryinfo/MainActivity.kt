package com.cmunaro.countryinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListScreen
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListViewModel
import com.cmunaro.countryinfo.ui.theme.CountryInfoTheme
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