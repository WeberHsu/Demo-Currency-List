package com.weberhsu.data.repository

import com.weberhsu.base.Mapper
import com.weberhsu.base.dispatchers.DispatcherProvider
import com.weberhsu.data.FiatDataModel
import com.weberhsu.data.database.LocalFiatDataSource
import com.weberhsu.domain.entity.CurrencyInfo
import com.weberhsu.domain.repository.FiatRepository
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
class FiatRepositoryImpl @Inject constructor(
    private val localDataSource: LocalFiatDataSource,
    private val fiatMapper: Mapper<FiatDataModel, CurrencyInfo>,
    private val dispatchers: DispatcherProvider
) : FiatRepository {
    override suspend fun clear(): Result<Unit> {
        return Result.runCatching {
            localDataSource.clear()
        }
    }

    override fun getAllFiats(): Flow<List<CurrencyInfo>> {
        return localDataSource.getAllFiats()
            .catch {
                throw it
            }
            .distinctUntilChanged()
            .map { fiatMapper.fromList(it) }
            .flowOn(dispatchers.io)
    }

    override suspend fun insertFiats(fiats: List<CurrencyInfo>): Result<Unit> {
        return Result.runCatching {
            localDataSource.addFiats(fiatMapper.toList(fiats))
        }
    }

    override fun search(text: String): Flow<List<CurrencyInfo>> {
        return localDataSource.search(text)
            .catch {
                throw it
            }
            .distinctUntilChanged()
            .map { fiatMapper.fromList(it) }
            .flowOn(dispatchers.io)
    }

}