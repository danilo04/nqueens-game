package com.nqueens.game.core.board.ui.state

import com.nqueens.game.core.board.domain.BoardPosition
import com.nqueens.game.core.board.domain.Spot
import com.nqueens.game.core.board.domain.games.BoardGame
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine

enum class SelectedState {
    TO_MOVE,
    TO_DELETE,
    NOT_SELECTED,
}

data class CellState(
    val spot: Spot,
    val hasErrorColor: Boolean,
    val selected: SelectedState = SelectedState.NOT_SELECTED,
) {
    companion object {
        val emptyCellState = CellState(Spot.EmptySpot, false, SelectedState.NOT_SELECTED)
    }
}

abstract class BoardUiState(
    private val boardGame: BoardGame,
) {
    abstract val boardSize: Int

    protected val cellErrors = mutableMapOf<BoardPosition, MutableStateFlow<Boolean>>()
    protected val cellSelections = mutableMapOf<BoardPosition, MutableStateFlow<SelectedState>>()

    val boardCells: Map<BoardPosition, Flow<CellState>> =
        buildMap {
            val n = boardGame.board.size
            repeat(n) { x ->
                repeat(n) { y ->
                    val currentPosition = BoardPosition.of(x, y)

                    // Create error flow for this position
                    val errorFlow = MutableStateFlow<Boolean>(false)
                    cellErrors[currentPosition] = errorFlow

                    // Create selection flow for this position
                    val selectionFlow = MutableStateFlow(SelectedState.NOT_SELECTED) // Initialize with not selected
                    cellSelections[currentPosition] = selectionFlow

                    // Combine spot changes with error state and selection state
                    val cellState =
                        combine(
                            boardGame.board[currentPosition],
                            errorFlow,
                            selectionFlow,
                        ) { spot, hasError, isSelected ->
                            CellState(
                                spot = spot,
                                hasErrorColor = hasError,
                                selected = isSelected,
                            )
                        }

                    put(currentPosition, cellState)
                }
            }
        }

    fun selectCell(
        position: BoardPosition,
        state: SelectedState = SelectedState.TO_MOVE,
    ) {
        cellSelections[position]?.value = state
    }

    fun deselectCell(position: BoardPosition) {
        cellSelections[position]?.value = SelectedState.NOT_SELECTED
    }

    fun clearAllSelections() {
        cellSelections.values.forEach { selectionFlow ->
            selectionFlow.value = SelectedState.NOT_SELECTED
        }
    }

    fun isCellSelected(position: BoardPosition): Boolean = cellSelections[position]?.value != SelectedState.NOT_SELECTED

    fun setPositionError(
        position: BoardPosition,
        hasError: Boolean,
    ) {
        cellErrors[position]?.value = hasError
    }

    fun clearAllErrors() {
        cellErrors.values.forEach { errorFlow ->
            errorFlow.value = false
        }
    }

    abstract fun tapOnCell(
        x: Int,
        y: Int,
    )

    abstract fun resetGame()

    fun getCellState(
        x: Int,
        y: Int,
    ): Flow<CellState> {
        val position = BoardPosition.of(x, y)
        return boardCells[position]!!
    }
}
