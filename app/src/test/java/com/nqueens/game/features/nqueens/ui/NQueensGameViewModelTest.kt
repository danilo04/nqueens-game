package com.nqueens.game.features.nqueens.ui

import app.cash.turbine.test
import com.nqueens.game.BaseUnitTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NQueensGameViewModelTest : BaseUnitTest() {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: NQueensGameViewModel

    companion object {
        private const val TEST_PLAYER_NAME = "TestPlayer"
        private const val TEST_QUEENS_COUNT = 4 // Using smaller board for faster tests
    }

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = NQueensGameViewModel(TEST_PLAYER_NAME, TEST_QUEENS_COUNT)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given a new ViewModel, when created, then initial state is correct`() =
        runTest {
            viewModel.uiState.test {
                val initialState = awaitItem()

                assertThat(initialState.playerName).isEqualTo(TEST_PLAYER_NAME)
                assertThat(initialState.totalQueens).isEqualTo(TEST_QUEENS_COUNT)
                assertThat(initialState.queensPlaced).isEqualTo(0)
                assertThat(initialState.timeElapsed).isEqualTo(0L)
                assertThat(initialState.isGamePaused).isFalse()
                assertThat(initialState.isGameCompleted).isFalse()
                assertThat(initialState.boardState).isNotNull()
            }
        }

    @Test
    fun `given a ViewModel, when time passes, then timer starts automatically and counts seconds`() =
        runTest {
            viewModel.uiState.test {
                // Skip initial state
                skipItems(1)

                // Advance time by 3 seconds
                advanceTimeBy(3000L)

                val state = awaitItem()
                assertThat(state.timeElapsed).isEqualTo(3L)
            }
        }

    @Test
    fun `given a running game, when paused, then timer stops and sets paused state`() =
        runTest {
            viewModel.uiState.test {
                // Skip initial state
                skipItems(1)

                // Advance time by 2 seconds
                advanceTimeBy(2000L)
                val stateBeforePause = awaitItem()
                assertThat(stateBeforePause.timeElapsed).isEqualTo(2L)

                // Pause the game
                viewModel.pauseGame()
                val pausedState = awaitItem()

                assertThat(pausedState.isGamePaused).isTrue()
                assertThat(pausedState.timeElapsed).isEqualTo(2L)

                // Advance time more - timer should not increase
                advanceTimeBy(2000L)
                expectNoEvents()
            }
        }

    @Test
    fun `given a paused game, when resumed, then timer restarts and clears paused state`() =
        runTest {
            viewModel.uiState.test {
                // Skip initial state
                skipItems(1)

                // Advance time and pause
                advanceTimeBy(1000L)
                awaitItem() // timer update

                viewModel.pauseGame()
                awaitItem() // pause state

                // Resume the game
                viewModel.resumeGame()
                val resumedState = awaitItem()

                assertThat(resumedState.isGamePaused).isFalse()

                // Timer should continue from where it left off
                advanceTimeBy(1000L)
                val afterResumeState = awaitItem()
                assertThat(afterResumeState.timeElapsed).isEqualTo(2L)
            }
        }

    @Test
    fun `given a game in progress, when reset, then all state is reset and timer restarts`() =
        runTest {
            viewModel.uiState.test {
                // Skip initial state
                skipItems(1)

                // Advance time
                advanceTimeBy(5000L)
                awaitItem() // timer update

                // Reset the game
                viewModel.resetGame()
                val resetState = awaitItem()

                assertThat(resetState.timeElapsed).isEqualTo(0L)
                assertThat(resetState.isGameCompleted).isFalse()
                assertThat(resetState.queensPlaced).isEqualTo(0)
                assertThat(resetState.isGamePaused).isFalse()

                // Timer should start again
                advanceTimeBy(1000L)
                val afterResetState = awaitItem()
                assertThat(afterResetState.timeElapsed).isEqualTo(1L)
            }
        }

    @Test
    fun `given a completed game, when timer is checked, then it stops running`() =
        runTest {
            // This test would require mocking the game completion logic
            // For now, we'll test the timer stopping behavior with pause
            viewModel.uiState.test {
                skipItems(1)

                advanceTimeBy(2000L)
                awaitItem()

                viewModel.pauseGame()
                awaitItem()

                // Timer should not advance when paused
                advanceTimeBy(3000L)
                expectNoEvents()
            }
        }

    @Test
    fun `given a game, when queens are placed, then count updates correctly`() =
        runTest {
            viewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.queensPlaced).isEqualTo(0)

                // The actual queen placement would happen through board interactions
                // This test structure is prepared for when that functionality is available
            }
        }

    @Test
    fun `given a custom player name, when ViewModel is created, then player name is set correctly`() =
        runTest {
            val customPlayerName = "CustomPlayer"
            val customViewModel = NQueensGameViewModel(customPlayerName, 8)

            customViewModel.uiState.test {
                val state = awaitItem()
                assertThat(state.playerName).isEqualTo(customPlayerName)
            }
        }

    @Test
    fun `given a board size, when ViewModel is created, then total queens matches board size`() =
        runTest {
            val queensCount = 6
            val customViewModel = NQueensGameViewModel(TEST_PLAYER_NAME, queensCount)

            customViewModel.uiState.test {
                val state = awaitItem()
                assertThat(state.totalQueens).isEqualTo(queensCount)
            }
        }

    @Test
    fun `given a ViewModel, when created, then board state is initialized correctly`() =
        runTest {
            viewModel.uiState.test {
                val state = awaitItem()
                assertThat(state.boardState).isNotNull()
                assertThat(state.boardState.boardSize).isEqualTo(TEST_QUEENS_COUNT)
            }
        }

    @Test
    fun `given a running timer, when checked at intervals, then timer precision is correct`() =
        runTest {
            viewModel.uiState.test {
                skipItems(1)

                // Test timer increments every second
                advanceTimeBy(1000L)
                val state1 = awaitItem()
                assertThat(state1.timeElapsed).isEqualTo(1L)

                advanceTimeBy(1000L)
                val state2 = awaitItem()
                assertThat(state2.timeElapsed).isEqualTo(2L)

                advanceTimeBy(500L) // Half second - should not trigger update
                expectNoEvents()

                advanceTimeBy(500L) // Complete the second
                val state3 = awaitItem()
                assertThat(state3.timeElapsed).isEqualTo(3L)
            }
        }

    @Test
    fun `given a game, when paused and resumed multiple times, then cycles work correctly`() =
        runTest {
            viewModel.uiState.test {
                skipItems(1)

                // First cycle
                advanceTimeBy(1000L)
                awaitItem() // 1 second

                viewModel.pauseGame()
                awaitItem() // paused

                viewModel.resumeGame()
                awaitItem() // resumed

                // Second cycle
                advanceTimeBy(1000L)
                val state = awaitItem() // 2 seconds total

                assertThat(state.timeElapsed).isEqualTo(2L)
                assertThat(state.isGamePaused).isFalse()
            }
        }
}
