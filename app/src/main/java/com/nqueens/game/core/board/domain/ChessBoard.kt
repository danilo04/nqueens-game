package com.nqueens.game.core.board.domain

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * A concrete implementation of the [Board] interface representing a chess-style game board.
 *
 * ChessBoard provides a reactive, mutable N×N square board where game pieces can be placed,
 * moved, and removed. It uses StateFlow to enable reactive UI updates when the board state
 * changes, making it ideal for real-time game applications.
 *
 * ## Key Features:
 * - **Reactive State Management**: Each board position is backed by a StateFlow for real-time updates
 * - **Flexible Size**: Supports any N×N board size (8×8 for standard chess, N×N for N-Queens, etc.)
 * - **Piece Tracking**: Efficiently tracks all pieces on the board for quick lookup operations
 * - **Bounds Checking**: Automatically validates position coordinates to prevent out-of-bounds access
 * - **Memory Efficient**: Uses a 2D array structure optimized for board-based games
 *
 * ## Usage Example:
 * ```kotlin
 * // Create a standard 8x8 chess board
 * val chessBoard = ChessBoard(8)
 *
 * // Place a piece
 * val queen = Queen(PieceColor.WHITE)
 * val position = BoardPosition.of(4, 4)
 * chessBoard.setSpot(Spot.PieceSpot(queen), position)
 *
 * // Observe changes to a specific position
 * chessBoard[position].collect { spot ->
 *     when (spot) {
 *         is Spot.EmptySpot -> println("Position is empty")
 *         is Spot.PieceSpot -> println("Piece: ${spot.piece}")
 *     }
 * }
 * ```
 *
 * @param n The size of the square board (number of rows and columns).
 *          Must be a positive integer. Common values are 8 (standard chess)
 *          or variable N (for N-Queens puzzle).
 *
 * @constructor Creates a new ChessBoard with the specified size, initially empty.
 *              All positions start as [Spot.EmptySpot].
 *
 * @see Board
 * @see Spot
 * @see BoardPosition
 *
 * @since 1.0.0
 */
class ChessBoard(
    private val n: Int,
) : Board {
    private val board: Array<Array<MutableStateFlow<Spot>>> =
        Array(n) {
            Array(n) { MutableStateFlow(Spot.EmptySpot) }
        }
    private val currentPieces = mutableMapOf<BoardPosition, Spot.PieceSpot>()

    override val size: Int
        get() = n

    override fun getPiecesOnBoard(): Map<BoardPosition, Spot.PieceSpot> = currentPieces

    override operator fun get(position: BoardPosition): StateFlow<Spot> {
        require(isValidPosition(position)) { "Position $position is out of bounds" }
        return board[position.x][position.y]
    }

    override fun setSpot(
        spot: Spot,
        position: BoardPosition,
    ) {
        require(isValidPosition(position)) { "Position $position is out of bounds" }
        // Remove the previous piece from the spot.
        val currentSpot = board[position.x][position.y]
        if (currentSpot.value is Spot.PieceSpot) {
            currentPieces.remove(position)
        }

        // Add the new spot
        board[position.x][position.y].value = spot
        if (spot is Spot.PieceSpot) {
            currentPieces[position] = spot
        }
    }

    private fun isValidPosition(position: BoardPosition): Boolean =
        position.x in 0 until board.size && position.y in 0 until board.size

    override fun clear() {
        for (i in 0 until board.size) {
            for (j in 0 until board.size) {
                board[i][j].value = Spot.EmptySpot
            }
        }
        currentPieces.clear()
    }
}
