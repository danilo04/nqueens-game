package com.nqueens.game.core.board.domain.pieces

import com.nqueens.game.core.board.domain.BoardPosition

/**
 * Represents a game piece that can be placed on the board.
 *
 * The Piece interface defines the contract for all game pieces in games like chess or N-Queens.
 * Each piece has a color and the ability to calculate its possible moves based on
 * the current board state and game rules.
 *
 * Implementations of this interface should define the specific movement patterns
 * for different types of pieces (e.g., Queen, Bishop, Rook, etc.).
 */
interface Piece {
    val pieceColor: PieceColor

    /**
     * Calculates all possible valid moves for this piece from the given position.
     *
     * This method determines where the piece can legally move based on its
     * movement rules, the board boundaries, and the current positions of other
     * pieces on the board. The implementation should consider:
     * - The piece's specific movement pattern
     * - Board boundaries (positions must be within 0 to boardSize-1)
     * - Blocked paths due to other pieces
     * - Game-specific rules (e.g., capturing, piece interactions)
     *
     * @param boardSize The size of the square board (N×N)
     * @param fromPosition The current position of this piece
     * @param currentPieces A map of all pieces currently on the board,
     *                      used to determine blocked positions and potential captures.
     *                      Defaults to an empty map if no other pieces are present.
     * @return A list of valid [BoardPosition]s where this piece can move.
     *         Returns an empty list if no moves are possible.
     *
     * @throws IllegalArgumentException if fromPosition is outside board boundaries
     */
    fun getPossibleMoves(
        boardSize: Int,
        fromPosition: BoardPosition,
        currentPieces: Map<BoardPosition, Piece> = emptyMap(),
    ): List<BoardPosition>
}

enum class PieceColor {
    WHITE,
    BLACK,
}
