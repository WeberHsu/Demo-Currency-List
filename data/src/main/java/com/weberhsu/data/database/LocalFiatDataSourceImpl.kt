package com.weberhsu.data.database

import com.weberhsu.base.Mapper
import com.weberhsu.base.dispatchers.DispatcherProvider
import com.weberhsu.data.FiatDataModel
import com.weberhsu.data.database.dao.FiatItemDao
import com.weberhsu.data.database.model.FiatLocalModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * author : weber
 * desc :
 */
class LocalFiatDataSourceImpl @Inject constructor(
    private val fiatDao: FiatItemDao,
    private val fiatMapper: Mapper<FiatLocalModel, FiatDataModel>,
    private val dispatchers: DispatcherProvider
) : LocalFiatDataSource {
    override suspend fun clear() {
        withContext(dispatchers.io) {
            fiatDao.deleteAll()
        }
    }

    override fun getAllFiats(): Flow<List<FiatDataModel>> {
        return fiatDao.getAllItems().flowOn(dispatchers.io).map { fiatMapper.fromList(it) }
    }

    override suspend fun addFiats(fiats: List<FiatDataModel>) {
        withContext(dispatchers.io) {
            fiatDao.insertAll(fiatMapper.toList(fiats))
        }
    }

    override fun search(text: String): Flow<List<FiatDataModel>> {
        return fiatDao.searchFiatsByNameOrSymbol(text).flowOn(dispatchers.io).map { fiatMapper.fromList(it) }
    }
}