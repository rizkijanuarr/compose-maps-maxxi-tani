package com.kiki.hellocompose.data.api

import com.kiki.hellocompose.data.response.AddressResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface Routes {

    @GET("address")
    suspend fun getAddress(
        @Query("location")
        location: String
    ): AddressResponse

    @GET("staticmap/v5/map")
    suspend fun getMap()

}