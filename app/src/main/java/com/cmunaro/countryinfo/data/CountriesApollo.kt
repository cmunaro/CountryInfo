package com.cmunaro.countryinfo.data

import com.apollographql.apollo.ApolloClient

private const val endpoint = "https://countries.trevorblades.com/"

class CountriesApollo {
    val client: ApolloClient = ApolloClient.builder()
        .serverUrl(endpoint)
        .build()
}