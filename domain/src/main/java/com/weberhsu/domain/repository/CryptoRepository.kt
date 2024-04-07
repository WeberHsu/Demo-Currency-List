package com.weberhsu.domain.repository

import com.weberhsu.domain.entity.CurrencyInfo
import kotlinx.coroutines.flow.Flow

/**
 * author : weber
 * desc :
 */
interface CryptoRepository {
    suspend fun clear(): Result<Unit>
    fun getAllCryptos(): Flow<List<CurrencyInfo>>
    suspend fun insertCryptos(cryptos: List<CurrencyInfo>): Result<Unit>
    fun search(text: String): Flow<List<CurrencyInfo>>
}