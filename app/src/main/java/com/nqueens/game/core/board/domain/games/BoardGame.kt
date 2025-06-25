package com.nqueens.game.core.board.domain.games

import com.nqueens.game.core.board.domain.Board
import com.nqueens.game.core.board.domain.BoardPosition
import com.nqueens.game.core.board.domain.pieces.Piece
import com.nqueens.game.core.board.domain.pieces.PieceColor
import kotlinx.coroutines.flow.StateFlow

abstract class BoardGame {
    abstract val board: Board
    abstract val currentPlayer: PieceColor
    abstract val gameState: StateFlow<GameState>
    abstract val numPlayers: Int

    /**
     * Initialize the game with the starting board configuration
     */
    abstract fun initialize()

    /**
     * Make a move by placing or moving a piece on the board
     * @param piece The piece to place or move
     * @param position The target position on the board
     * @return true if the move was successful, false otherwise
     */
    abstract fun insertPiece(
        piece: Piece,
        position: BoardPosition,
    ): Boolean

    abstract fun removePiece(position: BoardPosition)

    /**
     * Check if the current game state represents a solved puzzle or a win condition
     * @return true if the game is won, false otherwise
     */
    abstract fun isGameSolved(): Boolean

    /**
     * Reset the game to its initial state
     */
    abstract fun resetGame()
}

enum class GameState {
    NOT_STARTED,
    IN_PROGRESS,
    SOLVED,
    BLOCKED,
}
