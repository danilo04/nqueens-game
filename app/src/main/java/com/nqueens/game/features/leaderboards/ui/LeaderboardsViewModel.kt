package com.nqueens.game.features.leaderboards.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nqueens.game.core.data.database.entities.NQueensGamesWon
import com.nqueens.game.core.data.repositories.NQueensGamesWonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LeaderboardsUiState(
    val isLoading: Boolean = true,
    val games: List<NQueensGamesWon> = emptyList(),
    val selectedQueensFilter: Int? = null,
    val availableQueensCounts: List<Int> = emptyList(),
    val error: String? = null,
)

@HiltViewModel
class LeaderboardsViewModel
    @Inject
    constructor(
        private val nQueensGamesWonRepository: NQueensGamesWonRepository,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(LeaderboardsUiState())
        val uiState: StateFlow<LeaderboardsUiState> = _uiState.asStateFlow()

        init {
            loadGames()
        }

        fun applyQueensFilter(queensCount: Int?) {
            _uiState.value = _uiState.value.copy(selectedQueensFilter = queensCount)
            loadGames()
        }

        private fun loadGames() {
            viewModelScope.launch {
                try {
                    _uiState.value = _uiState.value.copy(isLoading = true, error = null)

                    val games =
                        if (_uiState.value.selectedQueensFilter != null) {
                            nQueensGamesWonRepository.getGamesByQueensCount(_uiState.value.selectedQueensFilter!!)
                        } else {
                            nQueensGamesWonRepository.getAllGames()
                        }

                    // Get unique queen counts for filter options
                    val allGames = nQueensGamesWonRepository.getAllGames()
                    val availableQueensCounts = allGames.map { it.queensCount }.distinct().sorted()

                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            games = games.sortedBy { it.timeInSeconds }, // Sort by time (fastest first)
                            availableQueensCounts = availableQueensCounts,
                        )
                } catch (e: Exception) {
                    _uiState.value =
                        _uiState.value.copy(
                            isLoading = false,
                            error = "Failed to load leaderboards: ${e.message}",
                        )
                }
            }
        }
    }
