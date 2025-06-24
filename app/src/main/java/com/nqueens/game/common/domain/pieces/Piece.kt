package com.nqueens.game.common.domain.pieces

import com.nqueens.game.common.domain.board.BoardPosition
import com.nqueens.game.common.domain.board.Spot


interface Piece {
    val pieceColor: PieceColor
    fun getPossibleMoves(
        boardSize: Int,
        fromPosition: BoardPosition,
        currentPieces: Map<BoardPosition, Spot.PieceSpot> = emptyMap()
    ): List<BoardPosition>
}

enum class PieceColor {
    WHITE,
    BLACK
}