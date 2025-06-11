package com.example.srashtidemoapp.domain.usecase
import com.example.srashtidemoapp.domain.model.Holding
import com.example.srashtidemoapp.domain.model.PortfolioSummary
import com.example.srashtidemoapp.domain.repository.HoldingsRepository

import javax.inject.Inject

class CalculatePortfolioUseCase @Inject constructor() {

    fun calculateSummary(holdings: List<Holding>): PortfolioSummary {
        val currentValue = holdings.sumOf { it.ltp * it.quantity }
        val totalInvestment = holdings.sumOf { it.avgPrice * it.quantity }
        val totalPNL = currentValue - totalInvestment
        val todayPNL = holdings.sumOf { (it.close - it.ltp) * it.quantity }

        return PortfolioSummary(
            currentValue = currentValue,
            totalInvestment = totalInvestment,
            totalPNL = totalPNL,
            todayPNL = todayPNL
        )
    }
}