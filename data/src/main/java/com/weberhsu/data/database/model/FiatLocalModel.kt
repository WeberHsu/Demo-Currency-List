package com.weberhsu.data.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * author : weber
 * desc : Fiat table item
 */
@Entity(tableName = "fiats")
data class FiatLocalModel(
    @PrimaryKey val id: String,
    val name: String,
    val symbol: String,
    val code: String
)