package com.weberhsu.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import com.weberhsu.data.database.AppDatabase
import com.weberhsu.data.database.dao.FiatItemDao
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
class FiatItemDaoTest {
    private lateinit var fiatDao: FiatItemDao
    private lateinit var database: AppDatabase

    @Before
    fun setUp() {
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext<Context>(), AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        fiatDao = database.fiatItemDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertAll() = runTest {
        // Insert testing data
        val tests = TestData.generateTestFiats()
        fiatDao.insertAll(tests)
        fiatDao.getFiatById(tests[0].id).test {
            expectThat(awaitItem()?.code).isEqualTo(tests[0].code)
        }
    }

    @Test
    fun getAllFiats() = runTest {
        // Insert all testing data
        val tests = TestData.generateTestFiats()
        fiatDao.insertAll(tests)

        // Check if get all cryptos from database
        fiatDao.getAllItems().test {
            val item = awaitItem()
            expectThat(item.size).isEqualTo(tests.size)
            expectThat(item).isEqualTo(tests)
        }
    }

    @Test
    fun clearFiats() = runTest {
        // Insert all testing data
        val tests = TestData.generateTestFiats()
        fiatDao.insertAll(tests)
        fiatDao.deleteAll()

        // Check if get all cryptos from database
        fiatDao.getAllItems().test {
            val item = awaitItem()
            expectThat(item.size).isEqualTo(0)
        }
    }
}