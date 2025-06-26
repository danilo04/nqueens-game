package com.nqueens.game.features.nqueens.ui

import app.cash.turbine.test
import com.nqueens.game.BaseUnitTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Ignore
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NQueensGameViewModelTest : BaseUnitTest() {
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)
    private lateinit var viewModel: NQueensGameViewModel

    @Before
    fun setUp() {
        viewModel = NQueensGameViewModel("Player Name", 8)
    }

    @Ignore
    @Test
    fun `timer should increment time when game is active`() =
        testScope.runTest {
            viewModel.uiState.test {
                // Skip initial state
                awaitItem()

                // Advance time by 3 seconds
                advanceTimeBy(3000L)

                val updatedState = awaitItem()
                assertThat(updatedState.timeElapsed).isEqualTo(3L)
            }
        }

    @Ignore
    @Test
    fun `pauseGame should stop timer and set paused state`() =
        testScope.runTest {
            viewModel.uiState.test {
                // Skip initial state
                awaitItem()

                // Advance time to ensure timer is running
                advanceTimeBy(2000L)
                awaitItem() // time elapsed = 2

                // Pause the game
                viewModel.pauseGame()
                val pausedState = awaitItem()

                assertThat(pausedState.isGamePaused).isTrue()
                assertThat(pausedState.timeElapsed).isEqualTo(2L)

                // Advance more time and verify timer doesn't continue
                advanceTimeBy(3000L)
                expectNoEvents()
            }
        }

    @Ignore
    @Test
    fun `resumeGame should restart timer and clear paused state`() =
        testScope.runTest {
            viewModel.uiState.test {
                // Skip initial state
                awaitItem()

                // Pause the game first
                viewModel.pauseGame()
                awaitItem() // paused state

                // Resume the game
                viewModel.resumeGame()
                val resumedState = awaitItem()

                assertThat(resumedState.isGamePaused).isFalse()

                // Advance time to verify timer is running again
                advanceTimeBy(2000L)
                val timeUpdatedState = awaitItem()
                assertThat(timeUpdatedState.timeElapsed).isEqualTo(2L)
            }
        }

    @Test
    fun `resetGame should reset all state and restart timer`() =
        testScope.runTest {
            viewModel.uiState.test {
                // Skip initial state
                awaitItem()

                // Let some time pass
                advanceTimeBy(5000L)
                awaitItem() // time elapsed = 5

                // Pause the game
                viewModel.pauseGame()
                awaitItem() // paused state

                // Reset the game
                viewModel.resetGame()
                val resetState = awaitItem()

                assertThat(resetState.timeElapsed).isEqualTo(0L)
                assertThat(resetState.isGameCompleted).isFalse()
                assertThat(resetState.queensPlaced).isEqualTo(0)
                assertThat(resetState.isGamePaused).isFalse()

                // Verify timer starts again after reset
                advanceTimeBy(1000L)
                val timerResumedState = awaitItem()
                assertThat(timerResumedState.timeElapsed).isEqualTo(1L)
            }
        }
}
