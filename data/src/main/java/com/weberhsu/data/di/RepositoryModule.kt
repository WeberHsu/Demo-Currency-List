package com.weberhsu.data.di

import com.weberhsu.data.database.LocalCryptoDataSource
import com.weberhsu.data.database.LocalCryptoDataSourceImpl
import com.weberhsu.data.database.LocalFiatDataSource
import com.weberhsu.data.database.LocalFiatDataSourceImpl
import com.weberhsu.data.repository.CryptoRepositoryImpl
import com.weberhsu.data.repository.FiatRepositoryImpl
import com.weberhsu.domain.repository.CryptoRepository
import com.weberhsu.domain.repository.FiatRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

/**
 * author : weber
 * desc :
 */
@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideLocalCryptoDataSource(localDataSourceImpl: LocalCryptoDataSourceImpl): LocalCryptoDataSource

    @Binds
    abstract fun provideLocalFiatDataSource(remoteDataSourceImp: LocalFiatDataSourceImpl): LocalFiatDataSource

    @Binds
    @ViewModelScoped
    abstract fun provideCryptoRepository(repository: CryptoRepositoryImpl): CryptoRepository

    @Binds
    @ViewModelScoped
    abstract fun provideFiatRepository(repository: FiatRepositoryImpl): FiatRepository
}