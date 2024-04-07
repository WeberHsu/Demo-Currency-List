package com.weberhsu.domain.entity

/**
 * author : weber
 * desc :
 */
data class CryptoEntity(
    val id: String, // BTC
    val name: String, // Bitcoin
    val symbol: String // BTC
) {
    fun toCurrencyInfo() = CurrencyInfo(id, name, null, symbol)
}