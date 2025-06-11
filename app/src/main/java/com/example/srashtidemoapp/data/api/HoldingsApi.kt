package com.example.srashtidemoapp.data.api

import com.example.srashtidemoapp.data.model.HoldingsResponse
import retrofit2.http.GET

interface HoldingsApi {

    @GET("/")
    suspend fun getHoldings(): HoldingsResponse
}
