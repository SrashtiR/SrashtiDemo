package com.example.srashtidemoapp.domain.model

data class PortfolioSummary(
    val currentValue: Double,
    val totalInvestment: Double,
    val totalPNL: Double,
    val todayPNL: Double
)
