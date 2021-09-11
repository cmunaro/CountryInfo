package com.cmunaro.countryinfo.data

import com.cmunaro.countryinfo.GetCountriesQuery

interface CountriesService {
    suspend fun getCountries(): List<GetCountriesQuery.Country>?
}