package com.weberhsu.data.database

import com.weberhsu.data.CryptoDataModel
import kotlinx.coroutines.flow.Flow

/**
 * author : weber
 * desc :
 */
interface LocalCryptoDataSource {

    suspend fun clear()

    fun getAllCryptos(): Flow<List<CryptoDataModel>>

    suspend fun addCryptos(crypto: List<CryptoDataModel>)

    fun search(text: String): Flow<List<CryptoDataModel>>
}