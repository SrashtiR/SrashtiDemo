package com.example.srashtidemoapp.di

import android.content.Context

import com.example.srashtidemoapp.data.api.HoldingsApi
import com.example.srashtidemoapp.data.repository.HoldingsRepositoryImpl
import com.example.srashtidemoapp.domain.repository.HoldingsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://35dee773a9ec441e9f38d5fc249406ce.api.mockbin.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideHoldingsApi(retrofit: Retrofit): HoldingsApi {
        return retrofit.create(HoldingsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHoldingsRepository(api: HoldingsApi): HoldingsRepository {
        return HoldingsRepositoryImpl(api)
    }

//    @Provides
//    @Singleton
//    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
//        return Room.databaseBuilder(
//            context,
//            AppDatabase::class.java,
//            "holdings_db"
//        ).build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideHoldingsDao(db: AppDatabase): HoldingsDao = db.holdingsDao()
}