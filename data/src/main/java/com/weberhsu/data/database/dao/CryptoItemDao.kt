package com.weberhsu.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.weberhsu.data.database.model.CryptoLocalModel
import kotlinx.coroutines.flow.Flow

/**
 * author : weber
 * desc :
 */
@Dao
interface CryptoItemDao {
    @Query("DELETE FROM cryptos")
    fun deleteAll()

    @Transaction
    @Query("SELECT * FROM cryptos where id = :id")
    fun getCryptoById(id: String): Flow<CryptoLocalModel?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(currencies: List<CryptoLocalModel>)

    @Query("SELECT * FROM cryptos")
    fun getAllItems(): Flow<List<CryptoLocalModel>>

    @Query("SELECT * FROM cryptos WHERE name LIKE LOWER(:searchTerm||'%') OR name LIKE LOWER('% '||:searchTerm) OR symbol LIKE LOWER(:searchTerm||'%')")
    fun searchCryptosByNameOrSymbol(searchTerm: String): Flow<List<CryptoLocalModel>>
}