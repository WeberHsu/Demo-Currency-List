package com.weberhsu.presentation

import app.cash.turbine.test
import com.weberhsu.base.test.BaseCoroutineTestWithTestDispatcherProvider
import com.weberhsu.domain.entity.CurrencyInfo
import com.weberhsu.domain.usecase.CurrencyUseCase
import com.weberhsu.presentation.contract.MainEvent
import com.weberhsu.presentation.contract.MainState
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.isA
import strikt.assertions.isEqualTo
import strikt.assertions.isSameInstanceAs

/**
 * author : weber
 * desc :
 */
@OptIn(ExperimentalCoroutinesApi::class)
class CurrencyListViewModelTest : BaseCoroutineTestWithTestDispatcherProvider(
    dispatcher = StandardTestDispatcher()
) {

    @MockK
    private lateinit var useCase: CurrencyUseCase

    private lateinit var viewModel: CurrencyListViewModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true) // turn relaxUnitFun on for all mocks
        // Create DetailViewModel before every test
        viewModel = CurrencyListViewModel(useCase)
    }

    @Test
    fun test_search_cryptos_success() = runTest {
        val tests = TestData.generateTestCryptos()

        // Given
        every { useCase.searchCryptoCurrencies(any()) } returns flow {
            emit(Result.success(tests))
        }

        // When& Assertion
        viewModel.handleEvent(MainEvent.Search("b"))
        handleSearchSuccessFlowTest(tests)

        // Then
        verify { useCase.searchCryptoCurrencies(any()) }
    }

    @Test
    fun test_search_cryptos_with_empty_search_text_success() = runTest {
        val tests = TestData.generateTestCryptos()

        // Given
        every { useCase.getCryptoCurrencies() } returns flow {
            emit(Result.success(tests))
        }

        // When& Assertion
        viewModel.handleEvent(MainEvent.Search(""))
        handleSearchSuccessFlowTest(tests)

        // Then
        verify { useCase.getCryptoCurrencies() }
    }

    @Test
    fun test_search_cryptos_with_search_text_null_success() = runTest {
        val tests = TestData.generateTestCryptos()

        // Given
        every { useCase.getCryptoCurrencies() } returns flow {
            emit(Result.success(tests))
        }

        // When& Assertion
        viewModel.handleEvent(MainEvent.Search(null))
        handleSearchSuccessFlowTest(tests)

        // Then
        verify { useCase.getCryptoCurrencies() }
    }

    private fun handleSearchSuccessFlowTest(tests: List<CurrencyInfo>) = runTest {
        viewModel.model.stateFlow.test {
            expectThat(awaitItem()).isSameInstanceAs(MainState.Init)
            expectThat(awaitItem()).isSameInstanceAs(MainState.Loading)
            expectThat(awaitItem()).isSameInstanceAs(MainState.Init)
            val currencyItem = awaitItem()
            expectThat(currencyItem).isA<MainState.CurrencyList>()
            expectThat((currencyItem as MainState.CurrencyList).currencies?.size).isEqualTo(tests.size)
        }
    }
}