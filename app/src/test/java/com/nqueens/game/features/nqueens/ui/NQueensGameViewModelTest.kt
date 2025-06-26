package com.nqueens.game.features.nqueens.ui

import com.nqueens.game.BaseUnitTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
class NQueensGameViewModelTest : BaseUnitTest() {
    private val testDispatcher = StandardTestDispatcher()

    companion object {
        private const val TEST_PLAYER_NAME = "TestPlayer"
        private const val TEST_QUEENS_COUNT = 4 // Using smaller board for faster tests
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
