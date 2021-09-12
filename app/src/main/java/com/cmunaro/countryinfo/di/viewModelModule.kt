package com.cmunaro.countryinfo.di

import com.cmunaro.countryinfo.ui.screen.countrydetails.CountryDetailsViewModel
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

val viewModelModule = module {
    single { CountryListViewModel(get()) }
    single { CountryDetailsViewModel(CoroutineScope(SupervisorJob()), get()) }
}