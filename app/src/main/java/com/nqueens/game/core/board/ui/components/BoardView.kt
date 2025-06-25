package com.nqueens.game.core.board.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nqueens.game.core.board.domain.Board
import com.nqueens.game.core.board.domain.BoardPosition
import com.nqueens.game.core.board.domain.ChessBoard
import com.nqueens.game.core.board.domain.Spot
import com.nqueens.game.core.board.domain.games.BoardGame
import com.nqueens.game.core.board.domain.games.GameState
import com.nqueens.game.core.board.domain.pieces.Piece
import com.nqueens.game.core.board.domain.pieces.PieceColor
import com.nqueens.game.core.board.domain.pieces.QueenPiece
import com.nqueens.game.core.board.ui.state.BoardUiState
import com.nqueens.game.core.board.ui.state.CellState
import com.nqueens.game.core.design.theme.ChessGamesTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun BoardView(
    boardState: BoardUiState,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
        ) {
            val size = boardState.boardSize
            // From n-1 to 0 to create a top-down view
            (size - 1 downTo 0).forEach { col ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    repeat(size) { row ->
                        BoardCell(
                            row = row,
                            col = col,
                            cellState =
                                boardState
                                    .getCellState(row, col)
                                    .collectAsStateWithLifecycle(CellState.emptyCellState)
                                    .value,
                            onCellTapped = boardState::tapOnCell,
                        )
                    }
                }
            }
        }
    }
}

// Dummy implementations for previews
private class DummyBoardGame(
    private val size: Int,
) : BoardGame() {
    override val board: Board = ChessBoard(size)
    override val currentPlayer: PieceColor = PieceColor.WHITE
    override val gameState: StateFlow<GameState> = MutableStateFlow(GameState.IN_PROGRESS)
    override val numPlayers: Int = 1

    override fun initialize() {}

    override fun insertPiece(
        piece: Piece,
        position: BoardPosition,
    ): Boolean {
        board.setSpot(Spot.PieceSpot(piece), position)
        return true
    }

    override fun removePiece(position: BoardPosition) {
        board.setSpot(Spot.EmptySpot, position)
    }

    override fun isGameSolved(): Boolean = false

    override fun resetGame() {
        repeat(size) { x ->
            repeat(size) { y ->
                board.setSpot(Spot.EmptySpot, BoardPosition.of(x, y))
            }
        }
    }
}

private class DummyBoardUiState(
    boardGame: BoardGame,
) : BoardUiState(boardGame) {
    override val boardSize: Int = (boardGame as DummyBoardGame).board.size

    override fun tapOnCell(
        x: Int,
        y: Int,
    ) {
        // Dummy implementation - do nothing
    }

    override fun resetGame() {
        // Dummy implementation - do nothing
    }
}

@Preview(showBackground = true)
@Composable
fun ChessBoardPreview4x4() {
    val boardGame = DummyBoardGame(8)
    boardGame.insertPiece(QueenPiece(PieceColor.WHITE), BoardPosition.of(0, 3))
    boardGame.insertPiece(QueenPiece(PieceColor.WHITE), BoardPosition.of(0, 0))
    boardGame.insertPiece(QueenPiece(PieceColor.WHITE), BoardPosition.of(3, 2))
    val state = DummyBoardUiState(boardGame)
    ChessGamesTheme {
        BoardView(
            boardState = state,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChessBoardPreview8x8() {
    val boardGame = DummyBoardGame(8)
    boardGame.insertPiece(QueenPiece(PieceColor.WHITE), BoardPosition.of(0, 0))
    boardGame.insertPiece(QueenPiece(PieceColor.WHITE), BoardPosition.of(2, 2))
    val state = DummyBoardUiState(boardGame)
    ChessGamesTheme {
        BoardView(
            boardState = state,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChessBoardPreview6x6() {
    val boardGame = DummyBoardGame(6)
    ChessGamesTheme {
        BoardView(
            boardState = DummyBoardUiState(boardGame),
        )
    }
}
