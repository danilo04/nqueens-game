package com.nqueens.game.core.board.domain

import kotlinx.coroutines.flow.StateFlow

/**
 * Represents a game board for a Chess game or similar board game,
 *
 * The Board interface provides a reactive contract for managing the state of a chess-like
 * board where pieces can be placed and moved. It uses StateFlow to enable reactive UI
 * updates when the board state changes.
 */
interface Board {
    /**
     * The size of the board (number of rows and columns).
     *
     * For an N-Queens puzzle, this represents the dimension of the square board
     * (N x N). The size determines how many queens need to be placed to solve
     * the puzzle. For a standard chess board, this would be 8.
     *
     * @return The board dimension as a positive integer
     */
    val size: Int

    /**
     * Gets a reactive stream of the spot state at the specified position.
     *
     * This operator function allows accessing board positions using array-like syntax
     * (e.g., `board[position]`). The returned StateFlow enables observers to react
     * to changes in the spot state at the given position.
     *
     * @param position The board position to query, must be within board bounds
     * @return A StateFlow emitting the current and future states of the spot
     *         at the specified position
     * @throws IndexOutOfBoundsException if the position is outside the board bounds
     */
    operator fun get(position: BoardPosition): StateFlow<Spot>

    /**
     * Sets the spot at the specified board position.
     *
     * This method updates the board state by placing or removing a piece at the
     * given position. When called, it will trigger updates to any observers
     * of the StateFlow for that position.
     *
     * @param spot The new spot state to set (either EmptySpot or PieceSpot)
     * @param position The board position where to place the spot, must be within bounds
     * @throws IndexOutOfBoundsException if the position is outside the board bounds
     */
    fun setSpot(
        spot: Spot,
        position: BoardPosition,
    )

    /**
     * Retrieves all pieces currently placed on the board.
     *
     * This method returns a snapshot of all non-empty spots on the board,
     * providing a map from board positions to the pieces at those positions.
     * Empty spots are not included in the returned map.
     *
     * @return A map containing all positions with pieces and their corresponding
     *         PieceSpot instances. Returns an empty map if no pieces are on the board.
     */
    fun getPiecesOnBoard(): Map<BoardPosition, Spot.PieceSpot>

    /**
     * Clears all pieces from the board.
     *
     * This method removes all pieces from the board, setting every position
     * to EmptySpot. After calling this method, [getPiecesOnBoard] will return
     * an empty map, and all position StateFlows will emit EmptySpot.
     */
    fun clear()
}
