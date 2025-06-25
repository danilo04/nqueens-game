package com.nqueens.game.core.board.domain

/**
 * Represents a position on the game board using Cartesian coordinates.
 *
 * BoardPosition is an immutable data class that encapsulates the x and y coordinates
 * of a specific position on the N-Queens game board. It uses the Flyweight pattern
 * for memory efficiency, ensuring that identical positions share the same instance.
 *
 * The coordinate system uses zero-based indexing where:
 * - x represents the column (0 to board.size - 1)
 * - y represents the row (0 to board.size - 1)
 * - (0,0) typically represents the top-left corner of the board
 *
 * @property x The horizontal coordinate (column index)
 * @property y The vertical coordinate (row index)
 */
@ConsistentCopyVisibility
data class BoardPosition private constructor(
    val x: Int,
    val y: Int,
) {
    companion object {
        private val positionCache = mutableMapOf<Pair<Int, Int>, BoardPosition>()

        /**
         * Creates or retrieves a BoardPosition instance for the given coordinates.
         *
         * This factory method implements the Flyweight pattern by returning cached
         * instances when available, or creating and caching new instances when needed.
         * This ensures that identical coordinates always return the same object instance.
         *
         * @param x The horizontal coordinate (column index), should be non-negative
         * @param y The vertical coordinate (row index), should be non-negative
         * @return A BoardPosition instance representing the specified coordinates
         *
         * @see BoardPosition
         */
        fun of(
            x: Int,
            y: Int,
        ): BoardPosition =
            positionCache.getOrPut(Pair(x, y)) {
                BoardPosition(x, y)
            }
    }
}
