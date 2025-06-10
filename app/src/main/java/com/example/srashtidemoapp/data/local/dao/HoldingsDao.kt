//package com.example.srashtidemoapp.data.local.dao
//
//import androidx.room.Dao
//import androidx.room.Insert
//import androidx.room.OnConflictStrategy
//import androidx.room.Query
//import com.example.srashtidemoapp.data.local.entity.HoldingEntity
//
//@Dao
//interface HoldingsDao {
//    @Query("SELECT * FROM holdings")
//    suspend fun getHoldings(): List<HoldingEntity>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertHoldings(holdings: List<HoldingEntity>)
//
//    @Query("DELETE FROM holdings")
//    suspend fun clearAll()
//}