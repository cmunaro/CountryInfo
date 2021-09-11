package com.cmunaro.countryinfo.di

import com.cmunaro.countryinfo.ui.screen.countrylist.CountryListViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { CountryListViewModel(get()) }
}