package com.nqueens.game.features.nqueens.ui.state

import com.nqueens.game.core.board.domain.BoardPosition
import com.nqueens.game.core.board.domain.Spot
import com.nqueens.game.core.board.domain.games.GameState
import com.nqueens.game.core.board.domain.pieces.PieceColor
import com.nqueens.game.core.board.domain.pieces.QueenPiece
import com.nqueens.game.core.board.ui.state.BoardUiState
import com.nqueens.game.core.board.ui.state.SelectedState
import com.nqueens.game.features.nqueens.domain.NQueensBoardGame
import com.nqueens.game.features.nqueens.domain.containsBoardPosition

class NQueensBoardUiState(
    private val nQueensBoardGame: NQueensBoardGame,
) : BoardUiState(nQueensBoardGame) {
    override val boardSize: Int = nQueensBoardGame.board.size

    override fun tapOnCell(
        x: Int,
        y: Int,
    ) {
        val position = BoardPosition.of(x, y)
        val currentSpot = nQueensBoardGame.board[position].value

        when {
            currentSpot is Spot.PieceSpot -> {
                if (isCellSelected(position)) {
                    nQueensBoardGame.removePiece(position)
                    updateErrorStates()
                    clearAllSelections()
                } else {
                    clearAllSelections()
                    selectCell(position, SelectedState.TO_DELETE)
                }
            }
            else -> {
                clearAllSelections()
                nQueensBoardGame.insertPiece(QueenPiece(PieceColor.WHITE), position)
                if (nQueensBoardGame.gameState.value == GameState.BLOCKED) {
                    updateErrorStates()
                }
            }
        }
    }

    override fun resetGame() {
        clearAllErrors()
        clearAllSelections()
        nQueensBoardGame.resetGame()
    }

    private fun updateErrorStates() {
        // Clear all existing errors
        clearAllErrors()

        // Set errors for attacked positions
        if (nQueensBoardGame.gameState.value == GameState.BLOCKED) {
            nQueensBoardGame.boardPositionsAttacked.keys.forEach { piecePosition ->
                // Check if there's actually a queen at this attacked position
                if (nQueensBoardGame.boardPositionsAttacked.containsBoardPosition(piecePosition)) {
                    setPositionError(piecePosition, true)
                }
            }
        }
    }
}
