package com.cmunaro.countryinfo.fakes

import com.cmunaro.countryinfo.data.CountriesService
import org.koin.dsl.module

val fakeNetworkModule = module {
    single<CountriesService> { FakeCountriesServiceImpl() }
}