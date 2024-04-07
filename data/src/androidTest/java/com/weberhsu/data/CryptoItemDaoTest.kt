package com.weberhsu.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.weberhsu.data.database.AppDatabase
import com.weberhsu.data.database.dao.CryptoItemDao
import com.weberhsu.data.database.model.CryptoLocalModel
import com.weberhsu.data.database.model.FiatLocalModel
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import strikt.api.expectThat
import strikt.assertions.isEqualTo


/**
 * author : weber
 * desc :
 */
@RunWith(AndroidJUnit4::class)
class CryptoItemDaoTest {
    private lateinit var cryptoDao: CryptoItemDao
    private lateinit var database: AppDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext<Context>(), AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        cryptoDao = database.cryptoItemDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testSearch() = runTest {
        // Insert testing data
        val cryptos = TestData.generateTestCryptos()
        cryptoDao.insertAll(cryptos)

        // Testing search function with some matching criteria
        cryptoDao.searchCryptosByNameOrSymbol("foo").test {
            val item = awaitItem()
            expectThat(item.size).isEqualTo(1)
            expectThat(item.first().id).isEqualTo("FOO")
        }
        cryptoDao.searchCryptosByNameOrSymbol("Foo").test {
            val item = awaitItem()
            expectThat(item.size).isEqualTo(1)
            expectThat(item.first().id).isEqualTo("FOO")
        }
        cryptoDao.searchCryptosByNameOrSymbol("Ethereum").test {
            val item = awaitItem()
            expectThat(item.size).isEqualTo(2)
            expectThat(item.first().id).isEqualTo("ETH")
            expectThat(item[1].id).isEqualTo("ETC")
        }
        cryptoDao.searchCryptosByNameOrSymbol("ethereum").test {
            val item = awaitItem()
            expectThat(item.size).isEqualTo(2)
            expectThat(item.first().id).isEqualTo("ETH")
            expectThat(item[1].id).isEqualTo("ETC")
        }
        cryptoDao.searchCryptosByNameOrSymbol("Classic").test {
            val item = awaitItem()
            expectThat(item.size).isEqualTo(1)
            expectThat(item.first().id).isEqualTo("ETC")
        }
    }

    @Test
    fun insertAll() = runTest {
        // Insert testing data
        val tests = TestData.generateTestCryptos()
        cryptoDao.insertAll(tests)
        cryptoDao.getCryptoById(tests[0].id).test {
            expectThat(awaitItem()?.symbol).isEqualTo(tests[0].symbol)
        }
    }

    @Test
    fun getAllCryptos() = runTest {
        // Insert all testing data
        val tests = TestData.generateTestCryptos()
        cryptoDao.insertAll(tests)

        // Check if get all cryptos from database
        cryptoDao.getAllItems().test {
            val item = awaitItem()
            expectThat(item.size).isEqualTo(tests.size)
            expectThat(item).isEqualTo(tests)
        }
    }

    @Test
    fun clearFiats() = runTest {
        // Insert all testing data
        val tests = TestData.generateTestCryptos()
        cryptoDao.insertAll(tests)
        cryptoDao.deleteAll()

        // Check if get all cryptos from database
        cryptoDao.getAllItems().test {
            val item = awaitItem()
            expectThat(item.size).isEqualTo(0)
        }
    }
}