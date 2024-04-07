package com.weberhsu.data

import app.cash.turbine.test
import com.weberhsu.base.test.BaseCoroutineTestWithTestDispatcherProvider
import com.weberhsu.data.database.LocalFiatDataSource
import com.weberhsu.data.database.LocalFiatDataSourceImpl
import com.weberhsu.data.database.dao.FiatItemDao
import com.weberhsu.data.database.mapper.FiatLocalDataMapper
import com.weberhsu.data.database.model.FiatLocalModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import strikt.api.expectCatching
import strikt.api.expectThat
import strikt.api.expectThrows
import strikt.assertions.isEqualTo
import strikt.assertions.isFailure
import strikt.assertions.isSuccess

/**
 * author : weber
 * desc :
 */
@OptIn(ExperimentalCoroutinesApi::class)
class LocalFiatDataSourceImplTest : BaseCoroutineTestWithTestDispatcherProvider(
    dispatcher = StandardTestDispatcher()
) {

    @MockK
    private lateinit var fiatDao: FiatItemDao

    private val fiatLocalDataMapper = FiatLocalDataMapper()

    private lateinit var localDataSource: LocalFiatDataSource

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create LocalDataSourceImp before every test
        localDataSource = LocalFiatDataSourceImpl(
            fiatDao = fiatDao,
            fiatMapper = fiatLocalDataMapper,
            dispatchers = testDispatcherProvider
        )
    }

    @Test
    fun test_get_all_fiats_success() = runTest {

        val tests = TestData.generateTestFiats()
        val expected = fiatLocalDataMapper.fromList(tests)

        // Given
        coEvery { fiatDao.getAllItems() } returns flow {
            emit(tests)
        }

        // When
        val returned = localDataSource.getAllFiats()

        // Then
        coVerify { fiatDao.getAllItems() }

        // Assertion
        returned.test {
            expectThat(awaitItem()).isEqualTo(
                expected
            )
            awaitComplete()
        }
    }

    @Test
    fun test_get_all_fiats_fail() = runTest {
        // Given
        coEvery { fiatDao.getAllItems() } returns flow {
            throw Exception()
        }

        // When
        val returned = localDataSource.getAllFiats()

        // Then
        coVerify { fiatDao.getAllItems() }

        // Assertion
        returned.test {
            expectCatching { awaitItem() }.isFailure()
        }
    }
}