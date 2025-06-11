package com.example.srashtidemoapp

import com.example.srashtidemoapp.domain.model.Holding
import com.example.srashtidemoapp.domain.usecase.CalculatePortfolioUseCase
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class CalculatePortfolioUseCaseTest {

    private lateinit var useCase: CalculatePortfolioUseCase

    @Before
    fun setup() {
        useCase = CalculatePortfolioUseCase()
    }

    //  Test basic calculation with valid holding
    @Test
    fun `calculate portfolio summary correctly`() {
        val holdings = listOf(
            Holding("ABC", quantity = 10.0, ltp = 90.0, avgPrice = 100.0, close = 95.0)
        )

        val summary = useCase.calculateSummary(holdings)

        assertEquals(900.0, summary.currentValue, 0.01)
        assertEquals(1000.0, summary.totalInvestment, 0.01)
        assertEquals(-100.0, summary.totalPNL, 0.01)
        assertEquals(50.0, summary.todayPNL, 0.01)
    }

    //  Test when holdings list is empty
    @Test
    fun `calculate summary for empty holdings`() {
        val holdings = emptyList<Holding>()

        val summary = useCase.calculateSummary(holdings)

        assertEquals(0.0, summary.currentValue, 0.01)
        assertEquals(0.0, summary.totalInvestment, 0.01)
        assertEquals(0.0, summary.totalPNL, 0.01)
        assertEquals(0.0, summary.todayPNL, 0.01)
    }

    // Test when holding quantity is zero
    @Test
    fun `calculate summary when quantity is zero`() {
        val holdings = listOf(
            Holding("XYZ", quantity = 0.0, ltp = 150.0, avgPrice = 100.0, close = 145.0)
        )

        val summary = useCase.calculateSummary(holdings)

        assertEquals(0.0, summary.currentValue, 0.01)
        assertEquals(0.0, summary.totalInvestment, 0.01)
        assertEquals(0.0, summary.totalPNL, 0.01)
        assertEquals(0.0, summary.todayPNL, 0.01)
    }

    // Test with negative price and LTP values
    @Test
    fun `calculate summary with negative ltp and price values`() {
        val holdings = listOf(
            Holding("NEG", quantity = 5.0, ltp = -80.0, avgPrice = -100.0, close = -90.0)
        )

        val summary = useCase.calculateSummary(holdings)

        assertEquals(-400.0, summary.currentValue, 0.01)
        assertEquals(-500.0, summary.totalInvestment, 0.01)
        assertEquals(100.0, summary.totalPNL, 0.01)
        assertEquals(-50.0, summary.todayPNL, 0.01)
    }

    //  Test with mixed gain and loss holdings
    @Test
    fun `calculate summary with mixed positive and negative pnl`() {
        val holdings = listOf(
            Holding("A", quantity = 2.0, ltp = 110.0, avgPrice = 100.0, close = 120.0), // profit
            Holding("B", quantity = 3.0, ltp = 90.0, avgPrice = 100.0, close = 85.0)    // loss
        )

        val summary = useCase.calculateSummary(holdings)

        val expectedCurrentValue = 2 * 110.0 + 3 * 90.0
        val expectedInvestment = 2 * 100.0 + 3 * 100.0
        val expectedTotalPnl = expectedCurrentValue - expectedInvestment
        val expectedTodayPnl = (120.0 - 110.0) * 2 + (85.0 - 90.0) * 3

        assertEquals(expectedCurrentValue, summary.currentValue, 0.01)
        assertEquals(expectedInvestment, summary.totalInvestment, 0.01)
        assertEquals(expectedTotalPnl, summary.totalPNL, 0.01)
        assertEquals(expectedTodayPnl, summary.todayPNL, 0.01)
    }
}