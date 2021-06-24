package com.dicoding.bfaa.tmdb.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * As far as I discovered, this method is the preferable approach
 * to test coroutine without repeating codes in the UT
 * https://proandroiddev.com/how-to-unit-test-code-with-coroutines-50c1640f6bef
 */

@ExperimentalCoroutinesApi
class TestCoroutineRule(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher(),
) : TestWatcher() {
    private val testCoroutineScope = TestCoroutineScope(testDispatcher)
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testCoroutineScope.cleanupTestCoroutines()
        testDispatcher.cleanupTestCoroutines()
    }

    fun runBlockingTest(testBlock: suspend TestCoroutineScope.() -> Unit) =
        testDispatcher.runBlockingTest(testBlock)

}