package com.nqueens.game.core.board.domain

import com.nqueens.game.core.board.domain.pieces.Piece

sealed interface Spot {
    data object EmptySpot : Spot

    data class PieceSpot(
        val piece: Piece,
    ) : Spot
}
