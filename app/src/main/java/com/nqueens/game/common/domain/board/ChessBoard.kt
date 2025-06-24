package com.nqueens.game.common.domain.board

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ChessBoard(private val n: Int) : Board {
    // Create n x n matrix initialized with empty pieces
    private val board: Array<Array<MutableStateFlow<Spot>>> = Array(n) {
        Array(n) { MutableStateFlow(Spot.EmptySpot) }
    }
    private val currentPieces = mutableMapOf<BoardPosition, Spot.PieceSpot>()

    override val size: Int
        get() = n

    override fun getPiecesOnBoard(): Map<BoardPosition, Spot.PieceSpot> {
        return currentPieces
    }

    override operator fun get(position: BoardPosition): StateFlow<Spot> {
        require(isValidPosition(position)) { "Position $position is out of bounds" }
        return board[position.x][position.y]
    }

    override fun setSpot(spot: Spot, position: BoardPosition) {
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

    private fun isValidPosition(position: BoardPosition): Boolean {
        return position.x in 0 until board.size && position.y in 0 until board.size
    }

    override fun clear() {
        for (i in 0 until board.size) {
            for (j in 0 until board.size) {
                board[i][j].value = Spot.EmptySpot
            }
        }
        currentPieces.clear()
    }
}

fun BoardPosition.column(n: Int): Int {
    require(n > 0) { "Board size must be greater than 0" }
    return n - y
}
