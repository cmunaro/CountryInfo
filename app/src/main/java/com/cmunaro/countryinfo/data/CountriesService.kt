package com.cmunaro.countryinfo.data

import com.cmunaro.countryinfo.GetContinentsQuery
import com.cmunaro.countryinfo.GetCountriesQuery
import com.cmunaro.countryinfo.GetCountryInfoQuery

interface CountriesService {
    suspend fun getCountries(): List<GetCountriesQuery.Country>?
    suspend fun getCountryInfo(countryCode: String): GetCountryInfoQuery.Country?
    suspend fun getContinents(): List<GetContinentsQuery.Continent>?
}