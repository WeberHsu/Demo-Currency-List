package com.weberhsu.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.weberhsu.data.database.model.FiatLocalModel
import kotlinx.coroutines.flow.Flow

/**
 * author : weber
 * desc :
 */
@Dao
interface FiatItemDao {
    @Query("DELETE FROM fiats")
    fun deleteAll()

    @Transaction
    @Query("SELECT * FROM fiats where id = :id")
    fun getFiatById(id: String): Flow<FiatLocalModel?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(currencies: List<FiatLocalModel>)

    @Query("SELECT * FROM fiats")
    fun getAllItems(): Flow<List<FiatLocalModel>>

    @Query("SELECT * FROM fiats WHERE name LIKE LOWER(:searchTerm||'%') OR name LIKE LOWER('% '||:searchTerm) OR code LIKE LOWER(:searchTerm||'%')")
    fun searchFiatsByNameOrSymbol(searchTerm: String): Flow<List<FiatLocalModel>>
}