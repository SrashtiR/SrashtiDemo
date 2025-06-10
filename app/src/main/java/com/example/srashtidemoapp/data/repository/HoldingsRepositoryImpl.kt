package com.example.srashtidemoapp.data.repository

import com.example.srashtidemoapp.data.api.HoldingsApi
import com.example.srashtidemoapp.domain.model.Holding
import com.example.srashtidemoapp.domain.repository.HoldingsRepository
import javax.inject.Inject

class HoldingsRepositoryImpl @Inject constructor(
    private val api: HoldingsApi
) : HoldingsRepository {

    override suspend fun getHoldings(): List<Holding> {
        return api.getHoldings().data.userHolding.map {
            Holding(
                symbol = it.symbol,
                quantity = it.quantity,
                ltp = it.ltp,
                avgPrice = it.avgPrice,
                close = it.close
            )
        }
    }
}
