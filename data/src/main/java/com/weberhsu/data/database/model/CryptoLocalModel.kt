package com.weberhsu.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * author : weber
 * desc : Crypto table item
 */
@Entity(tableName = "cryptos")
data class CryptoLocalModel(
    @PrimaryKey val id: String,
    val name: String,
    val symbol: String
)