package com.cmunaro.countryinfo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cmunaro.countryinfo.ui.screen.countrydetails.CountryDetailsScreen
import com.cmunaro.countryinfo.ui.screen.countrydetails.CountryDetailsViewModel
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListScreen
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListViewModel
import org.koin.androidx.compose.getViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun CountryInfoNavHost(navController: NavHostController) = NavHost(
    navController = navController,
    startDestination = Route.CountryList.path
) {
    composable(Route.CountryList.path) {
        val scope = rememberCoroutineScope()
        val viewModel: CountryListViewModel = getViewModel {
            parametersOf(scope)
        }
        CountryListScreen(
            navController = navController,
            handleAction = viewModel::handleAction,
            viewModelStateFlow = viewModel.state
        )
    }
    composable(Route.CountryDetails.path.plus("/{countryCode}")) {
        val scope = rememberCoroutineScope()
        val countryCode = remember {
            it.arguments
                ?.getString("countryCode")
                ?: return@composable
        }
        val viewModel: CountryDetailsViewModel = getViewModel {
            parametersOf(countryCode, scope)
        }
        CountryDetailsScreen(
            handleAction = viewModel::handleAction,
            viewModelStateFlow = viewModel.state
        )
    }
}

sealed class Route(val path: String) {
    object CountryList : Route("countryList")
    object CountryDetails : Route("countryDetails")
}