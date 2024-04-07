package com.weberhsu.data.database

import com.weberhsu.data.FiatDataModel
import kotlinx.coroutines.flow.Flow

/**
 * author : weber
 * desc :
 */
interface LocalFiatDataSource {

    suspend fun clear()

    fun getAllFiats(): Flow<List<FiatDataModel>>

    suspend fun addFiats(fiats: List<FiatDataModel>)

    fun search(text: String): Flow<List<FiatDataModel>>
}