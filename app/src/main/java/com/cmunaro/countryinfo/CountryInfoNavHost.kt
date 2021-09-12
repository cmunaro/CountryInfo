package com.cmunaro.countryinfo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cmunaro.countryinfo.ui.screen.countrydetails.CountryDetailsScreen
import com.cmunaro.countryinfo.ui.screen.countrydetails.CountryDetailsViewModel
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListScreen
import org.koin.androidx.compose.getViewModel

@Composable
fun CountryInfoNavHost(navController: NavHostController) = NavHost(
    navController = navController,
    startDestination = Route.CountryList.path
) {
    composable(Route.CountryList.path) {
        CountryListScreen(navController = navController)
    }
    composable(Route.CountryDetails.path.plus("/{countryCode}")) {
        val countryCode = remember {
            it.arguments
                ?.getString("countryCode")
                ?: return@composable
        }
        val viewModel: CountryDetailsViewModel = getViewModel()
        LaunchedEffect(countryCode) { viewModel.getInfoOf(countryCode) }
        CountryDetailsScreen(viewModelStateFlow = viewModel.state)
    }
}

sealed class Route(val path: String) {
    object CountryList : Route("countryList")
    object CountryDetails : Route("countryDetails")
}