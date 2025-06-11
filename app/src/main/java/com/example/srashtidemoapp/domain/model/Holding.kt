package com.example.srashtidemoapp.domain.model

data class Holding(
    val symbol: String,
    val quantity: Double,
    val ltp: Double,
    val avgPrice: Double,
    val close: Double
)