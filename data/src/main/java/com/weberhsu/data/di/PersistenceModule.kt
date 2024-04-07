package com.weberhsu.data.di

import android.content.Context
import com.weberhsu.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * author : weber
 * desc : Module that holds database related classes
 */
@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {
    /**
     * Provides [AppDatabase] instance
     */
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    /**
     * Provides [CryptoItemDao] instance
     */
    @Provides
    @Singleton
    fun provideCryptoItemDAO(appDatabase: AppDatabase) = appDatabase.cryptoItemDao()

    /**
     * Provides [FiatItemDao] instance
     */
    @Provides
    @Singleton
    fun provideFiatItemDAO(appDatabase: AppDatabase) = appDatabase.fiatItemDao()
}