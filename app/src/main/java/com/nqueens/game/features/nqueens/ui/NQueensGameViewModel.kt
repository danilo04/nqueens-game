package com.nqueens.game.features.nqueens.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nqueens.game.core.board.domain.games.GameState
import com.nqueens.game.features.nqueens.domain.NQueensBoardGame
import com.nqueens.game.features.nqueens.ui.state.NQueensBoardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

data class NQueensGameUiState(
    val playerName: String = "Player",
    val boardState: NQueensBoardUiState,
    val timeElapsed: Long = 0L, // in seconds
    val isGamePaused: Boolean = false,
    val isGameCompleted: Boolean = false,
    val queensPlaced: Int = 0,
    val totalQueens: Int = 8,
)

@HiltViewModel
class NQueensGameViewModel
    @Inject
    constructor() : ViewModel() {
        private val nQueensBoardGame = NQueensBoardGame(8) // Default 8x8 board
        private val boardState = NQueensBoardUiState(nQueensBoardGame)

        private val _uiState =
            MutableStateFlow(
                NQueensGameUiState(
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

        private fun stopTimer() {
            timerJob?.cancel()
            timerJob = null
        }

        override fun onCleared() {
            super.onCleared()
            stopTimer()
        }
    }
