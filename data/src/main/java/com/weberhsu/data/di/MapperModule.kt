package com.weberhsu.data.di

import com.weberhsu.base.Mapper
import com.weberhsu.data.CryptoDataModel
import com.weberhsu.data.FiatDataModel
import com.weberhsu.data.database.mapper.CryptoDataEntityMapper
import com.weberhsu.data.database.mapper.CryptoLocalDataMapper
import com.weberhsu.data.database.mapper.CurrencyInfoForCryptoMapper
import com.weberhsu.data.database.mapper.CurrencyInfoForFiatMapper
import com.weberhsu.data.database.mapper.FiatDataEntityMapper
import com.weberhsu.data.database.mapper.FiatLocalDataMapper
import com.weberhsu.data.database.model.CryptoLocalModel
import com.weberhsu.data.database.model.FiatLocalModel
import com.weberhsu.domain.entity.CryptoEntity
import com.weberhsu.domain.entity.CurrencyInfo
import com.weberhsu.domain.entity.FiatEntity
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * author : weber
 * desc :
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {
    @Binds
    abstract fun bindsCryptoDataEntityMapper(mapper: CryptoDataEntityMapper): Mapper<CryptoDataModel, CryptoEntity>

    @Binds
    abstract fun bindsCryptoLocalDataMapper(mapper: CryptoLocalDataMapper): Mapper<CryptoLocalModel, CryptoDataModel>

    @Binds
    abstract fun bindsCurrencyInfoForCryptoMapper(mapper: CurrencyInfoForCryptoMapper): Mapper<CryptoDataModel, CurrencyInfo>

    @Binds
    abstract fun bindsCurrencyInfoForFiatMapper(mapper: CurrencyInfoForFiatMapper): Mapper<FiatDataModel, CurrencyInfo>

    @Binds
    abstract fun bindsFiatDataEntityMapper(mapper: FiatDataEntityMapper): Mapper<FiatDataModel, FiatEntity>

    @Binds
    abstract fun bindsFiatLocalDataMapper(mapper: FiatLocalDataMapper): Mapper<FiatLocalModel, FiatDataModel>
}