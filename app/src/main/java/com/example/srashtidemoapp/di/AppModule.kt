package com.example.srashtidemoapp.di

import android.content.Context
import androidx.room.Room
import com.example.srashtidemoapp.BuildConfig

import com.example.srashtidemoapp.data.api.HoldingsApi
import com.example.srashtidemoapp.data.local.AppDatabase
import com.example.srashtidemoapp.data.local.dao.HoldingsDao
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
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideHoldingsApi(retrofit: Retrofit): HoldingsApi {
        return retrofit.create(HoldingsApi::class.java)
    }

    //todo
    @Provides
    @Singleton
    fun provideHoldingsRepository(api: HoldingsApi,dao: HoldingsDao): HoldingsRepository {
        return HoldingsRepositoryImpl(api,dao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            BuildConfig.HOLDINGS_DB_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideHoldingsDao(db: AppDatabase): HoldingsDao = db.holdingsDao()
}