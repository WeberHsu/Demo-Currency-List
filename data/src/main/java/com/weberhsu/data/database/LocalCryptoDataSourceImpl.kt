package com.weberhsu.data.database

import com.weberhsu.base.dispatchers.DispatcherProvider
import com.weberhsu.data.CryptoDataModel
import com.weberhsu.data.database.dao.CryptoItemDao
import com.weberhsu.data.database.model.CryptoLocalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * author : weber
 * desc :
 */
class LocalCryptoDataSourceImpl @Inject constructor(
    private val cryptoDao: CryptoItemDao,
    private val cryptoMapper: com.weberhsu.base.Mapper<CryptoLocalModel, CryptoDataModel>,
    private val dispatchers: DispatcherProvider
) : LocalCryptoDataSource {
    override suspend fun clear() {
        withContext(dispatchers.io) {
            cryptoDao.deleteAll()
        }
    }

    override fun getAllCryptos(): Flow<List<CryptoDataModel>> {
        return cryptoDao.getAllItems().flowOn(dispatchers.io).map { cryptoMapper.fromList(it) }
    }

    override suspend fun addCryptos(crypto: List<CryptoDataModel>) {
        withContext(dispatchers.io) {
            cryptoDao.insertAll(cryptoMapper.toList(crypto))
        }
    }

    override fun search(text: String): Flow<List<CryptoDataModel>> {
        return cryptoDao.searchCryptosByNameOrSymbol(text).flowOn(dispatchers.io).map { cryptoMapper.fromList(it) }
    }

}