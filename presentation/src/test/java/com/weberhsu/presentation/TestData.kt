package com.weberhsu.presentation

import com.weberhsu.domain.entity.CurrencyInfo

/**
 * author : weber
 * desc :
 */
object TestData {
    fun generateTestCryptos(): List<CurrencyInfo> {
        return listOf(
            CurrencyInfo("BTC", "Bitcoin", code = "BTC"),
            CurrencyInfo("ETH", "Ethereum", code = "ETH"),
            CurrencyInfo("XRP", "XRP", code = "XRP"),
            CurrencyInfo("BCH", "Bitcoin Cash", code = "BCH"),
            CurrencyInfo("FOO", "Foobar", code = "FOO"),
            CurrencyInfo("BAR", "Barfoo", code = "BAR"),
            CurrencyInfo("ETC", "Ethereum Classic", code = "ETC"),
            CurrencyInfo("TRON", "Tronclassic", code = "TRON"),
            CurrencyInfo("BET", "Bet coin", code = "BET"),
        )
    }

    fun generateTestFiats(): List<CurrencyInfo> {
        return listOf(
            CurrencyInfo("SGD", "Singapore Dollar", "$", "SGD"),
            CurrencyInfo("EUR", "Euro", "€", "EUR"),
            CurrencyInfo("GBP", "British Pound", "£", "GBP")
        )
    }
}