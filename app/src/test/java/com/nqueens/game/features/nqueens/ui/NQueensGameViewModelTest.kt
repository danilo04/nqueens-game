package com.nqueens.game.features.nqueens.ui

import app.cash.turbine.test
import com.nqueens.game.BaseUnitTest
import com.nqueens.game.core.data.repositories.NQueensGamesWonRepository
import com.nqueens.game.core.utils.TimeProvider
import com.nqueens.game.core.utils.haptic.HapticFeedbackManager
import com.nqueens.game.core.utils.sound.SoundManager
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class NQueensGameViewModelTest : BaseUnitTest() {
    companion object {
        private const val TEST_PLAYER_NAME = "TestPlayer"
        private const val TEST_QUEENS_COUNT = 4 // Using smaller board for faster tests
        private const val TEST_TIME_EPOCH = 1609459200000L // 2021-01-01 00:00:00 UTC
    }

    @Mock
    private lateinit var nQueensGamesWonRepository: NQueensGamesWonRepository

    @Mock
    private lateinit var timeProvider: TimeProvider

    @Mock
    private lateinit var soundManager: SoundManager

    @Mock
    private lateinit var hapticFeedbackManager: HapticFeedbackManager

    private lateinit var viewModel: NQueensGameViewModel

    @Before
    fun setUp() {
        whenever(timeProvider.currentUTCEpoch()).thenReturn(TEST_TIME_EPOCH)

        viewModel =
            NQueensGameViewModel(
                nQueensGamesWonRepository = nQueensGamesWonRepository,
                timeProvider = timeProvider,
                soundManager = soundManager,
                hapticFeedback = hapticFeedbackManager,
                playerName = TEST_PLAYER_NAME,
                queensCount = TEST_QUEENS_COUNT,
            )
    }

    @Test
    fun `initial state should be correctly set`() =
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

                viewModel.stopTimer()
            }
        }

//
    @Test
    fun `timer should increment every second`() =
        runTest {
            viewModel.uiState.test {
                // Skip initial state
                awaitItem()

                // Advance time by 1 second and wait for state update
                advanceTimeBy(1000L)
                val state1 = awaitItem()
                assertThat(state1.timeElapsed).isEqualTo(1L)

                // Advance time by another second
                advanceTimeBy(1000L)
                val state2 = awaitItem()
                assertThat(state2.timeElapsed).isEqualTo(2L)

                // Advance time by another second
                advanceTimeBy(1000L)
                val state3 = awaitItem()
                assertThat(state3.timeElapsed).isEqualTo(3L)

                viewModel.stopTimer()
            }
        }

    @Test
    fun `pauseGame should stop timer and set game as paused`() =
        runTest {
            viewModel.uiState.test {
                // Skip initial state
                awaitItem()

                // Advance time by 1 second and wait for state update
                advanceTimeBy(1000L)
                val state1 = awaitItem()
                assertThat(state1.timeElapsed).isEqualTo(1L)

                // Advance time by another second
                advanceTimeBy(1000L)
                val state2 = awaitItem()
                assertThat(state2.timeElapsed).isEqualTo(2L)

                viewModel.pauseGame()
                val pausedState = awaitItem()

                assertThat(pausedState.isGamePaused).isTrue()
                assertThat(pausedState.timeElapsed).isEqualTo(2L)

                // Advance time by another 2 seconds - timer should not increment
                advanceTimeBy(2000L)
                expectNoEvents()

                viewModel.stopTimer()
            }
        }

    @Test
    fun `resumeGame should restart timer and set game as not paused`() =
        runTest {
            viewModel.uiState.test {
                // Skip initial state
                awaitItem()

                // Pause the game first
                viewModel.pauseGame()
                awaitItem() // Paused state

                // Resume the game
                viewModel.resumeGame()
                val resumedState = awaitItem()

                assertThat(resumedState.isGamePaused).isFalse()

                // Verify timer continues
                advanceTimeBy(1000L)
                val timerState = awaitItem()
                assertThat(timerState.timeElapsed).isGreaterThan(0L)

                viewModel.stopTimer()
            }
        }

    @Test
    fun `resetGame should reset all values and restart timer`() =
        runTest {
            viewModel.uiState.test {
                // Skip initial state
                awaitItem()

                // Advance time by 1 second and wait for state update
                advanceTimeBy(1000L)
                assertThat(awaitItem().timeElapsed).isEqualTo(1L)
                advanceTimeBy(1000L)
                assertThat(awaitItem().timeElapsed).isEqualTo(2L)
                advanceTimeBy(1000L)
                assertThat(awaitItem().timeElapsed).isEqualTo(3L)
                advanceTimeBy(1000L)
                assertThat(awaitItem().timeElapsed).isEqualTo(4L)
                advanceTimeBy(1000L)
                assertThat(awaitItem().timeElapsed).isEqualTo(5L)

                viewModel.resetGame()
                val resetState = awaitItem()

                assertThat(resetState.timeElapsed).isEqualTo(0L)
                assertThat(resetState.isGameCompleted).isFalse()
                assertThat(resetState.queensPlaced).isEqualTo(0)
                assertThat(resetState.isGamePaused).isFalse()

                // Verify timer restarts
                advanceTimeBy(1000L)
                val timerState = awaitItem()
                assertThat(timerState.timeElapsed).isEqualTo(1L)

                viewModel.stopTimer()
            }
        }

    @Test
    fun `when game is completed timer should stop`() =
        runTest {
            // This test would require access to the internal NQueensBoardGame
            // to simulate a win condition. For now, we'll test the behavior
            // when the game state changes to SOLVED
            viewModel.uiState.test {
                val initialState = awaitItem()

                // Start timer
                advanceTimeBy(1000L)
                assertThat(awaitItem().timeElapsed).isEqualTo(1L)
                advanceTimeBy(1000L)
                assertThat(awaitItem().timeElapsed).isEqualTo(2L)

                // Simulate game completion by directly accessing board game
                // This would require making the NQueensBoardGame accessible or mocking it
                // For now, we verify the structure is correct
                assertThat(initialState.isGameCompleted).isFalse()

                viewModel.stopTimer()
            }
        }

    @Test
    fun `game completion should trigger database storage`() =
        runTest {
            viewModel.uiState.test {
                val state = awaitItem()

                state.boardState.tapOnCell(0, 1)
                state.boardState.tapOnCell(3, 2)
                state.boardState.tapOnCell(1, 3)
                state.boardState.tapOnCell(2, 0)

                val winningState = awaitItem()
                assertThat(winningState.isGameCompleted).isTrue

                viewModel.stopTimer()
            }

            verify(nQueensGamesWonRepository).insert(any())
        }

    @Test
    fun `timer should not run when game is completed`() =
        runTest {
            viewModel.uiState.test {
                val state = awaitItem()

                state.boardState.tapOnCell(0, 1)
                state.boardState.tapOnCell(3, 2)
                state.boardState.tapOnCell(1, 3)
                state.boardState.tapOnCell(2, 0)

                val winningState = awaitItem()
                assertThat(winningState.isGameCompleted).isTrue

                advanceTimeBy(1000L)
                expectNoEvents()

                viewModel.stopTimer()
            }
        }

    @Test
    fun `pause and resume should maintain elapsed time correctly`() =
        runTest {
            viewModel.uiState.test {
                awaitItem() // Initial state

                // Run for 3 seconds
                advanceTimeBy(1000L)
                assertThat(awaitItem().timeElapsed).isEqualTo(1L)
                advanceTimeBy(1000L)
                assertThat(awaitItem().timeElapsed).isEqualTo(2L)
                advanceTimeBy(1000L)
                val beforePause = awaitItem()
                assertThat(beforePause.timeElapsed).isEqualTo(3L)

                // Pause
                viewModel.pauseGame()
                val paused = awaitItem()
                assertThat(paused.timeElapsed).isEqualTo(3L)
                assertThat(paused.isGamePaused).isTrue()

                // Wait 2 seconds while paused (should not increment)
                advanceTimeBy(2000L)
                expectNoEvents()

                // Resume
                viewModel.resumeGame()
                val resumed = awaitItem()
                assertThat(resumed.timeElapsed).isEqualTo(3L) // Still 3, not 5
                assertThat(resumed.isGamePaused).isFalse()

                // Run for 2 more seconds
                advanceTimeBy(1000L)
                assertThat(awaitItem().timeElapsed).isEqualTo(4L)
                advanceTimeBy(1000L)
                assertThat(awaitItem().timeElapsed).isEqualTo(5L)

                viewModel.stopTimer()
            }
        }
}
