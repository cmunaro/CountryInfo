package com.cmunaro.countryinfo.data

import com.apollographql.apollo.coroutines.await
import com.cmunaro.countryinfo.GetCountriesQuery

class CountriesServiceImpl(private val countriesApollo: CountriesApollo) : CountriesService {
    override suspend fun getCountries() = countriesApollo
        .client
        .query(GetCountriesQuery())
        .await()
        .data
        ?.countries
}