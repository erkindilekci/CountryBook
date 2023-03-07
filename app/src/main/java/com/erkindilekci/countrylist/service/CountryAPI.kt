package com.erkindilekci.countrylist.service

import com.erkindilekci.countrylist.model.Country
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface CountryAPI {
    @GET("erkindil/Json/main/cryptolist.json")
    fun getCountries(): Single<List<Country>>
}

