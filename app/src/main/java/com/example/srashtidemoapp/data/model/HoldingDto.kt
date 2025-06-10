package com.example.srashtidemoapp.data.model

data class HoldingDto(
    val symbol: String,
    val quantity: Double,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
)
