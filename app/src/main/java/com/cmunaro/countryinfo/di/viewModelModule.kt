package com.cmunaro.countryinfo.di

import com.cmunaro.countryinfo.ui.screen.countrydetails.CountryDetailsViewModel
import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { CountryListViewModel(get(), get()) }
    viewModel { CountryDetailsViewModel(get(), get(), get()) }
}