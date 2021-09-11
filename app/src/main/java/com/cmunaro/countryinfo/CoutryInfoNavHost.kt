package com.cmunaro.countryinfo

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cmunaro.countryinfo.ui.screen.countrydetails.CountryDetailsScreen
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListScreen

@Composable
fun CoutryInfoNavHost(navController: NavHostController) = NavHost(
    navController = navController,
    startDestination = Route.CountryList.path
) {
    composable(Route.CountryList.path) {
        CountryListScreen(navController = navController)
    }
    composable(Route.CountryDetails.path.plus("/{countryCode}")) {
        val countryCode = it.arguments
            ?.getString("countryCode")
            ?: return@composable
        CountryDetailsScreen(countryCode)
    }
}

sealed class Route(val path: String) {
    object CountryList : Route("countryList")
    object CountryDetails : Route("countryDetails")
}