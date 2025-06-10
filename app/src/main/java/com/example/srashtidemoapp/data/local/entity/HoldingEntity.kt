//package com.example.srashtidemoapp.data.local.entity
//
//import androidx.room.Entity
//import androidx.room.PrimaryKey
//import com.example.srashtidemoapp.domain.model.Holding
//
//@Entity(tableName = "holdings")
//data class HoldingEntity(
//    @PrimaryKey val symbol: String,
//    val quantity: Double,
//    val ltp: Double,
//    val avgPrice: Double,
//    val close: Double
//)
//fun HoldingEntity.toDomain(): Holding {
//    return Holding(
//        symbol = symbol,
//        quantity = quantity,
//        ltp = ltp,
//        avgPrice = avgPrice,
//        close = close
//    )
//}