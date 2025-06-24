package com.nqueens.game.features.nqueens.ui

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class StartGameUiState(
    val playerName: String = "",
    val numberOfQueens: String = "",
    val playerNameError: Boolean = false,
    val numberOfQueensError: Boolean = false,
    val isFormValid: Boolean = false,
)

@HiltViewModel
class StartNQueensGameViewModel
    @Inject
    constructor() : ViewModel() {
        private val _uiState = MutableStateFlow(StartGameUiState())
        val uiState: StateFlow<StartGameUiState> = _uiState.asStateFlow()

        fun updatePlayerName(name: String) {
            _uiState.value =
                _uiState.value.copy(
                    playerName = name,
                    playerNameError = false,
                )
            validateForm()
        }

        fun updateNumberOfQueens(queens: String) {
            // Only allow numeric input
            val filteredQueens = queens.filter { it.isDigit() }
            _uiState.value =
                _uiState.value.copy(
                    numberOfQueens = filteredQueens,
                    numberOfQueensError = false,
                )
            validateForm()
        }

        private fun validateForm() {
            val currentState = _uiState.value
            val isPlayerNameValid = currentState.playerName.trim().isNotEmpty()
            val queensNumber = currentState.numberOfQueens.toIntOrNull()
            val isQueensValid = validateQueensNumber(queensNumber)

            _uiState.value =
                currentState.copy(
                    isFormValid = isPlayerNameValid && isQueensValid,
                )
        }

        fun validateAndGetGameData(): Pair<String, Int>? {
            val currentState = _uiState.value
            val trimmedPlayerName = currentState.playerName.trim()
            val queensNumber = currentState.numberOfQueens.toIntOrNull()

            val isPlayerNameValid = trimmedPlayerName.isNotEmpty()
            val isQueensValid = validateQueensNumber(queensNumber)

            _uiState.value =
                currentState.copy(
                    playerNameError = !isPlayerNameValid,
                    numberOfQueensError = !isQueensValid,
                )

            return (
                if (isPlayerNameValid && isQueensValid) {
                    Pair(trimmedPlayerName, queensNumber!!)
                } else {
                    null
                }
            )
        }

        private fun validateQueensNumber(number: Int?): Boolean = number != null && number in 4..12

        companion object {
            const val MIN_QUEENS = 4
            const val MAX_QUEENS = 11
        }
    }
