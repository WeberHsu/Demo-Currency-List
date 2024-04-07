package com.weberhsu.domain.repository

import com.weberhsu.domain.entity.CurrencyInfo
import kotlinx.coroutines.flow.Flow

/**
 * author : weber
 * desc :
 */
interface FiatRepository {
    suspend fun clear(): Result<Unit>
    fun getAllFiats(): Flow<List<CurrencyInfo>>
    suspend fun insertFiats(fiats: List<CurrencyInfo>): Result<Unit>
    fun search(text: String): Flow<List<CurrencyInfo>>
}