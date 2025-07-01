package com.nqueens.game.features.nqueens.ui

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nqueens.game.core.board.domain.games.GameState
import com.nqueens.game.core.data.database.entities.NQueensGamesWon
import com.nqueens.game.core.data.repositories.NQueensGamesWonRepository
import com.nqueens.game.core.utils.TimeProvider
import com.nqueens.game.core.utils.haptic.HapticFeedbackManager
import com.nqueens.game.core.utils.sound.SoundManager
import com.nqueens.game.features.nqueens.domain.NQueensBoardGame
import com.nqueens.game.features.nqueens.ui.state.NQueensBoardUiState
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class NQueensGameUiState(
    val playerName: String = "Player",
    val boardState: NQueensBoardUiState,
    val timeElapsed: Long = 0L, // in seconds
    val isGamePaused: Boolean = false,
    val isGameCompleted: Boolean = false,
    val queensPlaced: Int = 0,
    val totalQueens: Int = 8,
)

@HiltViewModel(assistedFactory = NQueensGameViewModel.NQueensGameViewModelFactory::class)
class NQueensGameViewModel
    @AssistedInject
    constructor(
        private val nQueensGamesWonRepository: NQueensGamesWonRepository,
        private val timeProvider: TimeProvider,
        private val soundManager: SoundManager,
        hapticFeedback: HapticFeedbackManager,
        @Assisted private val playerName: String,
        @Assisted private val queensCount: Int,
    ) : ViewModel() {
        @AssistedFactory
        interface NQueensGameViewModelFactory {
            fun create(
                playerName: String,
                queensCount: Int,
            ): NQueensGameViewModel
        }

        private val nQueensBoardGame = NQueensBoardGame(queensCount)
        private val boardState = NQueensBoardUiState(nQueensBoardGame, soundManager, hapticFeedback)

        private val _uiState =
            MutableStateFlow(
                NQueensGameUiState(
                    playerName = playerName,
                    boardState = boardState,
                    totalQueens = nQueensBoardGame.board.size,
                ),
            )
        val uiState: StateFlow<NQueensGameUiState> = _uiState.asStateFlow()

        private var timerJob: Job? = null

        init {
            nQueensBoardGame.initialize()

            startTimer()

            viewModelScope.launch {
                nQueensBoardGame.queensPlaced
                    .combine(nQueensBoardGame.gameState) { queensPlaced, gameState ->
                        _uiState.value =
                            _uiState.value.copy(
                                isGameCompleted = gameState == GameState.SOLVED,
                                queensPlaced = queensPlaced,
                            )

                        if (gameState == GameState.SOLVED) {
                            storeGameWon()
                        }
                    }.collect {}
            }
        }

        fun pauseGame() {
            _uiState.value = _uiState.value.copy(isGamePaused = true)
            stopTimer()
        }

        fun resumeGame() {
            _uiState.value = _uiState.value.copy(isGamePaused = false)
            startTimer()
        }

        fun resetGame() {
            stopTimer()
            _uiState.value.boardState.resetGame()
            _uiState.value =
                _uiState.value.copy(
                    timeElapsed = 0L,
                    isGameCompleted = false,
                    queensPlaced = 0,
                    isGamePaused = false,
                )
            startTimer()
        }

        private suspend fun storeGameWon() {
            nQueensGamesWonRepository.insert(
                NQueensGamesWon(
                    playerName = playerName,
                    queensCount = queensCount,
                    timeInSeconds = _uiState.value.timeElapsed,
                    datePlayed = timeProvider.currentUTCEpoch(),
                ),
            )
        }

        private fun startTimer() {
            timerJob =
                viewModelScope.launch {
                    while (!_uiState.value.isGameCompleted && !_uiState.value.isGamePaused) {
                        delay(1000L)
                        _uiState.value =
                            _uiState.value.copy(
                                timeElapsed = _uiState.value.timeElapsed + 1,
                            )
                    }
                }
        }

        @VisibleForTesting
        fun stopTimer() {
            timerJob?.cancel()
            timerJob = null
        }

        override fun onCleared() {
            super.onCleared()
            stopTimer()
            soundManager.release()
            boardState.resetGame()
        }
    }
