package com.nqueens.game.features.nqueensgame.ui

import com.nqueens.game.common.domain.board.BoardPosition
import com.nqueens.game.common.domain.board.Spot
import com.nqueens.game.common.domain.games.GameState
import com.nqueens.game.common.domain.pieces.PieceColor
import com.nqueens.game.common.domain.pieces.Queen
import com.nqueens.game.common.ui.state.BoardState
import com.nqueens.game.common.ui.state.SelectedState
import com.nqueens.game.features.nqueensgame.domain.NQueensBoardGame
import com.nqueens.game.features.nqueensgame.domain.containsBoardPosition

class NQueensBoardGameBoardState(
    private val nQueensBoardGame: NQueensBoardGame
) : BoardState(nQueensBoardGame) {
    override val boardSize: Int = nQueensBoardGame.board.size

    override fun tapOnCell(x: Int, y: Int) {
        val position = BoardPosition.of(x, y)
        val currentSpot = nQueensBoardGame.board[position].value

        when {
            currentSpot is Spot.PieceSpot -> {
                if (isCellSelected(position)) {
                    nQueensBoardGame.removePiece(position)
                    updateErrorStates()
                } else {
                    selectCell(position, SelectedState.TO_DELETE)
                }
            }
            else -> {
                clearAllSelections()
                nQueensBoardGame.insertPiece(Queen(PieceColor.WHITE), position)
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