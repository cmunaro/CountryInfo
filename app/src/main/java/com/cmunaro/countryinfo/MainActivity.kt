package com.cmunaro.countryinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import com.cmunaro.countryinfo.ui.theme.CountryInfoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            CountryInfoTheme {
                Surface(color = MaterialTheme.colors.background) {
                    CountryInfoNavHost(navController)
                }
            }
        }
    }
}