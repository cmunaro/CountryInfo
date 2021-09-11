package com.cmunaro.countryinfo.di

import com.cmunaro.countryinfo.data.CountriesApollo
import com.cmunaro.countryinfo.data.CountriesService
import com.cmunaro.countryinfo.data.CountriesServiceImpl
import org.koin.dsl.module

val networkModule = module {
    single { CountriesApollo() }
    single<CountriesService> { CountriesServiceImpl(get()) }
}