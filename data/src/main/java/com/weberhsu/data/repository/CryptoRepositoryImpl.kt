package com.weberhsu.data.repository

import com.weberhsu.base.dispatchers.DispatcherProvider
import com.weberhsu.data.CryptoDataModel
import com.weberhsu.data.database.LocalCryptoDataSource
import com.weberhsu.domain.entity.CurrencyInfo
import com.weberhsu.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * author : weber
 * desc : Repository for crypto
 */
class CryptoRepositoryImpl @Inject constructor(
    private val localDataSource: LocalCryptoDataSource,
    private val cryptoMapper: com.weberhsu.base.Mapper<CryptoDataModel, CurrencyInfo>,
    private val dispatchers: DispatcherProvider
) : CryptoRepository {
    override suspend fun clear(): Result<Unit> {
        return Result.runCatching {
            localDataSource.clear()
        }
    }

    override fun getAllCryptos(): Flow<List<CurrencyInfo>> {
        return localDataSource.getAllCryptos()
            .catch {
                throw it
            }
            .distinctUntilChanged()
            .map { cryptoMapper.fromList(it) }
            .flowOn(dispatchers.io)

    }

    override suspend fun insertCryptos(cryptos: List<CurrencyInfo>): Result<Unit> {
        return Result.runCatching {
            localDataSource.addCryptos(cryptoMapper.toList(cryptos))
        }
    }

    override fun search(text: String): Flow<List<CurrencyInfo>> {
        return localDataSource.search(text)
            .catch {
                throw it
            }
            .distinctUntilChanged()
            .map { cryptoMapper.fromList(it) }
            .flowOn(dispatchers.io)
    }
}