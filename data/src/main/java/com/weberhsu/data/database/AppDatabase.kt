package com.weberhsu.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.weberhsu.data.database.dao.CryptoItemDao
import com.weberhsu.data.database.dao.FiatItemDao
import com.weberhsu.data.database.model.CryptoLocalModel
import com.weberhsu.data.database.model.FiatLocalModel

/**
 * author : weber
 * desc :
 */
@Database(entities = [CryptoLocalModel::class, FiatLocalModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cryptoItemDao(): CryptoItemDao
    abstract fun fiatItemDao(): FiatItemDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "currency_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}