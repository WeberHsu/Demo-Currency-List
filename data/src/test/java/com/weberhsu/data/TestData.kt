package com.weberhsu.data

import com.weberhsu.data.database.model.CryptoLocalModel
import com.weberhsu.data.database.model.FiatLocalModel

/**
 * author : weber
 * desc :
 */
object TestData {
    fun generateTestCryptos(): List<CryptoLocalModel> {
        return listOf(
            CryptoLocalModel("BTC", "Bitcoin", "BTC"),
            CryptoLocalModel("ETH", "Ethereum", "ETH"),
            CryptoLocalModel("XRP", "XRP", "XRP"),
            CryptoLocalModel("BCH", "Bitcoin Cash", "BCH"),
            CryptoLocalModel("FOO", "Foobar", "FOO"),
            CryptoLocalModel("BAR", "Barfoo", "BAR"),
            CryptoLocalModel("ETC", "Ethereum Classic", "ETC"),
            CryptoLocalModel("TRON", "Tronclassic", "TRON"),
            CryptoLocalModel("BET", "Bet coin", "BET"),
        )
    }

    fun generateTestFiats(): List<FiatLocalModel> {
        return listOf(
            FiatLocalModel("SGD", "Singapore Dollar", "$", "SGD"),
            FiatLocalModel("EUR", "Euro", "€", "EUR"),
            FiatLocalModel("GBP", "British Pound", "£", "GBP")
        )
    }
}