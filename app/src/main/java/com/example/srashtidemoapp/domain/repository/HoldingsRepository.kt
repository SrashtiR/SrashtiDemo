package com.example.srashtidemoapp.domain.repository

import com.example.srashtidemoapp.domain.model.Holding

interface HoldingsRepository {
    suspend fun getHoldings(): List<Holding>

}