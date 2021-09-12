package com.cmunaro.countryinfo.data

import com.apollographql.apollo.coroutines.await
import com.cmunaro.countryinfo.GetCountriesQuery
import com.cmunaro.countryinfo.GetCountryInfoQuery

class CountriesServiceImpl(private val countriesApollo: CountriesApollo) : CountriesService {
    override suspend fun getCountries() = countriesApollo
        .client
        .query(GetCountriesQuery())
        .await()
        .data
        ?.countries

    override suspend fun getCountryInfo(countryCode: String) = countriesApollo
        .client
        .query(GetCountryInfoQuery(countryCode))
        .await()
        .data
        ?.country
}