package com.nqueens.game.features.nqueensgame.domain

import com.nqueens.game.common.domain.board.Board
import com.nqueens.game.common.domain.board.BoardPosition
import com.nqueens.game.common.domain.board.ChessBoard
import com.nqueens.game.common.domain.board.Spot
import com.nqueens.game.common.domain.games.BoardGame
import com.nqueens.game.common.domain.games.GameState
import com.nqueens.game.common.domain.pieces.Piece
import com.nqueens.game.common.domain.pieces.PieceColor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class NQueensBoardGame(val n: Int) : BoardGame() {
    private var _gameState = MutableStateFlow(GameState.NOT_STARTED)
    val boardPositionsAttacked = mutableMapOf<BoardPosition, Set<BoardPosition>>()
    private val _queensInPlace = MutableStateFlow(0)
    val queensInPlace = _queensInPlace.asStateFlow()

    override val board: Board by lazy {
        ChessBoard(n)
    }

    override val currentPlayer: PieceColor
        get() = PieceColor.WHITE

    override val gameState: StateFlow<GameState>
        get() = _gameState.asStateFlow()

    override val numPlayers: Int
        get() = 1

    override fun initialize() {
        _gameState.value = GameState.IN_PROGRESS
    }

    override fun insertPiece(
        piece: Piece,
        position: BoardPosition
    ): Boolean {
        if (_gameState.value == GameState.BLOCKED) {
            return false
        }

        board.setSpot(Spot.PieceSpot(piece), position)
        boardPositionsAttacked[position] = piece.getPossibleMoves(
            boardSize = n,
            fromPosition = position,
        ).toSet()

        _queensInPlace.value += 1

        when {
            boardPositionsAttacked.containsBoardPosition(position) -> {
                _gameState.value = GameState.BLOCKED
            }

            _queensInPlace.value == n -> {
                _gameState.value = GameState.SOLVED
            }
        }

        return true
    }

    override fun removePiece(position: BoardPosition) {
        board.setSpot(Spot.EmptySpot, position)
        boardPositionsAttacked.remove(position)
        _queensInPlace.value = maxOf(0, _queensInPlace.value - 1)

        // Recalculate error states
        if (_gameState.value == GameState.BLOCKED) {
            // Check if removing this piece resolves the blocking state
            val stillBlocked = boardPositionsAttacked.keys.any {
                boardPositionsAttacked.containsBoardPosition(it)
            }

            if (!stillBlocked) {
                _gameState.value = GameState.IN_PROGRESS
            }
        }
    }

    override fun isGameSolved(): Boolean = _gameState.value == GameState.SOLVED

    override fun resetGame() {
        board.clear()
        _gameState.value = GameState.NOT_STARTED
        boardPositionsAttacked.clear()
        _queensInPlace.value = 0
    }
}

fun MutableMap<BoardPosition, Set<BoardPosition>>.containsBoardPosition(
    position: BoardPosition
): Boolean {
    return values.any { it.contains(position) }
}