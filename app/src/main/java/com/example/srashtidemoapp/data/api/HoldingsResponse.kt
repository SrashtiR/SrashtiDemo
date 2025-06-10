package com.example.srashtidemoapp.data.api

import com.example.srashtidemoapp.data.model.HoldingDto
import com.example.srashtidemoapp.data.model.HoldingsData
import com.example.srashtidemoapp.domain.model.Holding

data class HoldingsResponse(
    val status: String,
    val data: HoldingsData
)
