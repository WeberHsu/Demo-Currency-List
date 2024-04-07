package com.weberhsu.domain.entity

/**
 * author : weber
 * desc : Entity about currency information (crypto & fiat)
 */
data class CurrencyInfo(
    val id: String, // BTC
    val name: String, // Bitcoin
    val symbol: String? = null,
    val code: String // // BTC
) {
    fun isFiat() = symbol != null
}