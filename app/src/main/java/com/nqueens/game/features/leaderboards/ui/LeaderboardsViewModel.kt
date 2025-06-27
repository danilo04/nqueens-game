package com.nqueens.game.features.leaderboards.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nqueens.game.core.data.database.entities.NQueensGamesWon
import com.nqueens.game.core.data.repositories.NQueensGamesWonRepository
import com.nqueens.game.core.utils.PagingDataSource
import com.nqueens.game.core.utils.di.IOThreadDispatcher
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LeaderboardsUiState(
    val isLoading: Boolean = true,
    val games: List<NQueensGamesWon> = emptyList(),
    val selectedQueensFilter: Int? = null,
    val availableQueensCounts: List<Int> = emptyList(),
    val error: String? = null,
    val loadMore: () -> Unit = {},
)

data class BoardSizeInfo(
    val boardSizesAvailable: List<Int>,
    val selectedBoardSize: Int? = null,
)

@HiltViewModel
class LeaderboardsViewModel
    @Inject
    constructor(
        private val nQueensGamesWonRepository: NQueensGamesWonRepository,
        @IOThreadDispatcher private val ioThreadDispatcher: CoroutineDispatcher,
    ) : ViewModel() {
        // When the user changes the filter, we need to react to the change and filter the data.
        private val boardSizeFilter: MutableStateFlow<Int?> = MutableStateFlow(null)

        // Used to keep a reference of the actual callback to load more elements.
        private var loadMoreRef: (() -> Unit)? = null

        private val paginationSource: PagingDataSource<BoardSizeInfo, NQueensGamesWon, LeaderboardsUiState> =
            PagingDataSource(
                viewModelScope = viewModelScope,
                backgroundDispatcher = ioThreadDispatcher,
                loadPage = { boardSizeInfo, page ->
                    if (boardSizeInfo.selectedBoardSize != null) {
                        nQueensGamesWonRepository.getGamesOffsetByQueensCountFlow(
                            queensCount = boardSizeInfo.selectedBoardSize,
                            page = page,
                        )
                    } else {
                        nQueensGamesWonRepository.getAllGamesOffsetFlow(page = page)
                    }
                },
                builder = { pages ->
                    pages.map { page ->
                        val games = page.data
                        val boardSizeInfo = page.params

                        loadMoreRef = page.loadMore

                        LeaderboardsUiState(
                            isLoading = false,
                            games = games,
                            selectedQueensFilter = boardSizeInfo.selectedBoardSize,
                            availableQueensCounts = boardSizeInfo.boardSizesAvailable,
                        ) {
                            loadMoreRef?.invoke()
                        }
                    }
                },
                combine(
                    boardSizeFilter,
                    nQueensGamesWonRepository.getDistinctQueensCountsFlow(),
                ) { selectedBoardSize, availableBoardSizes ->
                    BoardSizeInfo(
                        boardSizesAvailable = availableBoardSizes,
                        selectedBoardSize = selectedBoardSize,
                    )
                },
            )

        val uiState: StateFlow<LeaderboardsUiState> =
            paginationSource.uiState.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(1000),
                LeaderboardsUiState(isLoading = true),
            )

        init {
            viewModelScope.launch {
                val availableQueensCounts = nQueensGamesWonRepository.getDistinctQueensCounts()
                paginationSource.start(
                    BoardSizeInfo(
                        boardSizesAvailable = availableQueensCounts,
                        selectedBoardSize = null,
                    ),
                )
            }
        }

        fun applyQueensFilter(queensCount: Int?) {
            boardSizeFilter.value = queensCount
        }
    }
