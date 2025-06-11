package com.example.srashtidemoapp.data.repository

import android.util.Log
import com.example.srashtidemoapp.data.api.HoldingsApi
import com.example.srashtidemoapp.data.local.dao.HoldingsDao
import com.example.srashtidemoapp.data.local.entity.HoldingEntity
import com.example.srashtidemoapp.data.local.entity.toDomain
import com.example.srashtidemoapp.domain.model.Holding
import com.example.srashtidemoapp.domain.repository.HoldingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HoldingsRepositoryImpl @Inject constructor(
    private val api: HoldingsApi,
    private val dao: HoldingsDao
) : HoldingsRepository {

    override suspend fun getHoldings(): List<Holding> {
        return try {
            val response = api.getHoldings().data.userHolding
            val entities = response.map {

                HoldingEntity(
                    symbol = it.symbol,
                    quantity = it.quantity,
                    ltp = it.ltp,
                    avgPrice = it.avgPrice,
                    close = it.close
                )
            }

            dao.clearAll()
            dao.insertHoldings(entities)

            entities.map { it.toDomain() }


        } catch (e: Exception) {
            e.printStackTrace()

            withContext(Dispatchers.IO) {
                dao.getHoldings().map { it.toDomain() }
            }
        }
    }

}
