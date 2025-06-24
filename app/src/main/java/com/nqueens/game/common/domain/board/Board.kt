package com.nqueens.game.common.domain.board

import com.nqueens.game.common.domain.pieces.Piece
import kotlinx.coroutines.flow.StateFlow

@ConsistentCopyVisibility
data class BoardPosition private constructor(val x: Int, val y: Int) {
    companion object {
        private val positionCache = mutableMapOf<Pair<Int, Int>, BoardPosition>()

        fun of(x: Int, y: Int): BoardPosition {
            return positionCache.getOrPut(Pair(x, y)) {
                BoardPosition(x, y)
            }
        }
    }
}

sealed interface Spot {
    data object EmptySpot : Spot
    data class PieceSpot(val piece: Piece) : Spot
}

interface Board {
    val size: Int
    operator fun get(position: BoardPosition): StateFlow<Spot>
    fun setSpot(spot: Spot, position: BoardPosition)
    fun getPiecesOnBoard(): Map<BoardPosition, Spot.PieceSpot>

    fun clear()
}

