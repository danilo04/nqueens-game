package com.nqueens.game.common.domain.pieces

import com.nqueens.game.common.domain.board.BoardPosition
import com.nqueens.game.common.domain.board.Spot

class Queen(override val pieceColor: PieceColor) : Piece {
    override fun getPossibleMoves(
        boardSize: Int,
        fromPosition: BoardPosition,
        currentPieces: Map<BoardPosition, Spot.PieceSpot>
    ): List<BoardPosition> {
        val possibleMoves = mutableListOf<BoardPosition>()

        // Horizontal moves (along rows) - right
        for (x in (fromPosition.x + 1) until boardSize) {
            val position = BoardPosition.of(x, fromPosition.y)
            val currentPiece = currentPieces[position]
            when {
                currentPiece == null -> {
                    possibleMoves.add(position)
                }
                currentPiece.piece.pieceColor != pieceColor -> {
                    possibleMoves.add(position)
                    break
                }
                else -> {
                    break
                }
            }
        }

        // Horizontal moves (along rows) - left
        for (x in (fromPosition.x - 1) downTo 0) {
            val position = BoardPosition.of(x, fromPosition.y)
            val currentPiece = currentPieces[position]
            when {
                currentPiece == null -> {
                    possibleMoves.add(position)
                }
                currentPiece.piece.pieceColor != pieceColor -> {
                    possibleMoves.add(position)
                    break
                }
                else -> {
                    break
                }
            }
        }

        // Vertical moves (along columns) - up
        for (y in (fromPosition.y + 1) until boardSize) {
            val position = BoardPosition.of(fromPosition.x, y)
            val currentPiece = currentPieces[position]
            when {
                currentPiece == null -> {
                    possibleMoves.add(position)
                }
                currentPiece.piece.pieceColor != pieceColor -> {
                    possibleMoves.add(position)
                    break
                }
                else -> {
                    break
                }
            }
        }

        // Vertical moves (along columns) - down
        for (y in (fromPosition.y - 1) downTo 0) {
            val position = BoardPosition.of(fromPosition.x, y)
            val currentPiece = currentPieces[position]
            when {
                currentPiece == null -> {
                    possibleMoves.add(position)
                }
                currentPiece.piece.pieceColor != pieceColor -> {
                    possibleMoves.add(position)
                    break
                }
                else -> {
                    break
                }
            }
        }

        // Diagonal moves - up-right
        var x = fromPosition.x + 1
        var y = fromPosition.y + 1
        while (x < boardSize && y < boardSize) {
            val position = BoardPosition.of(x, y)
            val currentPiece = currentPieces[position]
            when {
                currentPiece == null -> {
                    possibleMoves.add(position)
                }
                currentPiece.piece.pieceColor != pieceColor -> {
                    possibleMoves.add(position)
                    break
                }
                else -> {
                    break
                }
            }
            x++
            y++
        }

        // Diagonal moves - down-left
        x = fromPosition.x - 1
        y = fromPosition.y - 1
        while (x >= 0 && y >= 0) {
            val position = BoardPosition.of(x, y)
            val currentPiece = currentPieces[position]
            when {
                currentPiece == null -> {
                    possibleMoves.add(position)
                }
                currentPiece.piece.pieceColor != pieceColor -> {
                    possibleMoves.add(position)
                    break
                }
                else -> {
                    break
                }
            }
            x--
            y--
        }

        // Diagonal moves - down-right
        x = fromPosition.x + 1
        y = fromPosition.y - 1
        while (x < boardSize && y >= 0) {
            val position = BoardPosition.of(x, y)
            val currentPiece = currentPieces[position]
            when {
                currentPiece == null -> {
                    possibleMoves.add(position)
                }
                currentPiece.piece.pieceColor != pieceColor -> {
                    possibleMoves.add(position)
                    break
                }
                else -> {
                    break
                }
            }
            x++
            y--
        }

        // Diagonal moves - up-left
        x = fromPosition.x - 1
        y = fromPosition.y + 1
        while (x >= 0 && y < boardSize) {
            val position = BoardPosition.of(x, y)
            val currentPiece = currentPieces[position]
            when {
                currentPiece == null -> {
                    possibleMoves.add(position)
                }
                currentPiece.piece.pieceColor != pieceColor -> {
                    possibleMoves.add(position)
                    break
                }
                else -> {
                    break
                }
            }
            x--
            y++
        }

        return possibleMoves
    

    }
}