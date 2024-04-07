package com.weberhsu.data

import app.cash.turbine.test
import com.weberhsu.base.Mapper
import com.weberhsu.base.test.BaseCoroutineTestWithTestDispatcherProvider
import com.weberhsu.data.database.LocalFiatDataSource
import com.weberhsu.data.database.mapper.CurrencyInfoForFiatMapper
import com.weberhsu.data.database.mapper.FiatDataEntityMapper
import com.weberhsu.data.database.mapper.FiatLocalDataMapper
import com.weberhsu.data.repository.FiatRepositoryImpl
import com.weberhsu.domain.entity.CurrencyInfo
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
import strikt.assertions.isEqualTo
import strikt.assertions.isFailure
import strikt.assertions.isSuccess

/**
 * author : weber
 * desc :
 */
@OptIn(ExperimentalCoroutinesApi::class)
class FiatRepositoryImplTest : BaseCoroutineTestWithTestDispatcherProvider(
    dispatcher = StandardTestDispatcher()
) {

    private lateinit var repo: FiatRepositoryImpl

    @MockK
    private lateinit var localDataSource: LocalFiatDataSource

    private val currencyInfoMapper = CurrencyInfoForFiatMapper()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        repo = FiatRepositoryImpl(
            localDataSource,
            currencyInfoMapper,
            testDispatcherProvider
        )
    }

    @Test
    fun test_get_all_fiats_success() = runTest {
        val fiatLocalDataMapper = FiatLocalDataMapper()
        val tests = fiatLocalDataMapper.fromList(TestData.generateTestFiats())
        val expected = currencyInfoMapper.fromList(tests)

        // Given
        coEvery { localDataSource.getAllFiats() } returns flow {
            emit(tests)
        }

        // When
        val returned = repo.getAllFiats()

        // Then
        coVerify { localDataSource.getAllFiats() }

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
        coEvery { localDataSource.getAllFiats() } returns flow {
            throw Exception()
        }

        // When
        val returned = repo.getAllFiats()

        // Then
        coVerify { localDataSource.getAllFiats() }

        // Assertion
        returned.test {
            expectCatching { awaitItem() }.isFailure()
        }
    }
}