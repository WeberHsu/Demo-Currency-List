package com.weberhsu.base.test

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import org.junit.Rule

/**
 * author : weber
 * desc :
 */
@OptIn(ExperimentalCoroutinesApi::class)
abstract class BaseCoroutineTestWithTestDispatcherProvider(
    private val dispatcher: TestDispatcher
) {

    /**
     * Sets the Main dispatcher to the test dispatcher provided as input.
     */
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(dispatcher = dispatcher)

    /**
     * DispatcherProvider to be provided as dependency to mocked classes.
     */
    protected val testDispatcherProvider = TestDispatcherProvider(testDispatcher = dispatcher)

}