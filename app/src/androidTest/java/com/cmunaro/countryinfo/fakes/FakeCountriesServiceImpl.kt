package com.cmunaro.countryinfo.fakes

import com.cmunaro.countryinfo.GetContinentsQuery
import com.cmunaro.countryinfo.GetCountriesQuery
import com.cmunaro.countryinfo.GetCountryInfoQuery
import com.cmunaro.countryinfo.data.CountriesService

class FakeCountriesServiceImpl : CountriesService {
    override suspend fun getCountries(): List<GetCountriesQuery.Country>? {
        return countries
    }

    override suspend fun getCountryInfo(countryCode: String): GetCountryInfoQuery.Country? {
        return countryInfo
    }

    override suspend fun getContinents(): List<GetContinentsQuery.Continent>? {
        return continents
    }

    companion object {
        var countries: List<GetCountriesQuery.Country>? = null
        var countryInfo: GetCountryInfoQuery.Country? = null
        var continents: List<GetContinentsQuery.Continent>? = null
    }
}
