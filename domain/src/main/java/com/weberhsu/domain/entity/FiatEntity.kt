package com.weberhsu.domain.entity

/**
 * author : weber
 * desc :
 */
data class FiatEntity(
    val id: String, // USD
    val name: String, // United States Dollar
    val symbol: String, // $
    val code: String // USD
) {
    fun toCurrencyInfo() = CurrencyInfo(id, name, symbol, code)
}