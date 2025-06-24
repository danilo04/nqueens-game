package com.nqueens.game.common.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nqueens.game.common.domain.board.BoardPosition
import com.nqueens.game.common.domain.board.Spot
import com.nqueens.game.common.domain.pieces.PieceColor
import com.nqueens.game.common.domain.pieces.Queen
import com.nqueens.game.common.ui.icons.NQueensIcons
import com.nqueens.game.common.ui.icons.pieces.WhiteQueen
import com.nqueens.game.common.ui.state.BoardState
import com.nqueens.game.common.ui.state.CellState
import com.nqueens.game.common.ui.state.SelectedState
import com.nqueens.game.features.nqueensgame.domain.NQueensBoardGame
import com.nqueens.game.features.nqueensgame.ui.NQueensBoardGameBoardState
import com.nqueens.game.ui.theme.NQueensGameTheme
import com.nqueens.game.ui.theme.errorDark

@Composable
fun BoardView(
    boardState: BoardState,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            val size = boardState.boardSize
            // From n-1 to 0 to create a top-down view
            (size - 1 downTo 0).forEach { col ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    repeat(size) { row ->
                        BoardCell(
                            row = row,
                            col = col,
                            cellState = boardState.getCellState(row, col)
                                .collectAsStateWithLifecycle(CellState.emptyCellState).value,
                            onCellTapped = boardState::tapOnCell
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RowScope.BoardCell(
    row: Int, 
    col: Int, 
    cellState: CellState, 
    modifier: Modifier = Modifier,
    onCellTapped: ((Int, Int) -> Unit)? = null
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val lightColor = primaryColor.copy(alpha = 0.3f)
    val darkColor = primaryColor.copy(alpha = 0.8f)

    val isLightSquare = (row + col) % 2 == 0
    val cellColor = when {
        cellState.hasErrorColor -> errorDark
        isLightSquare -> lightColor
        else -> darkColor
    }

    Box(
        modifier = modifier
            .aspectRatio(1f)
            .weight(1f)
            .background(cellColor)
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                onCellTapped?.invoke(row, col)
            }
    ) {
        if (cellState.spot is Spot.PieceSpot) {
            Image(
                imageVector = NQueensIcons.Pieces.WhiteQueen,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )

            // Add X icon overlay when selected for deletion
            if (cellState.selected == SelectedState.TO_DELETE) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Delete",
                    tint = Color.Red,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .size(24.dp)
                        .padding(4.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ChessBoardPreview4x4() {
    val nQueensBoardGame = NQueensBoardGame(4)
    nQueensBoardGame.insertPiece(Queen(PieceColor.WHITE), BoardPosition.of(0, 3))
    nQueensBoardGame.insertPiece(Queen(PieceColor.WHITE), BoardPosition.of(0, 0))
    nQueensBoardGame.insertPiece(Queen(PieceColor.WHITE), BoardPosition.of(3, 2))
    val state = NQueensBoardGameBoardState(nQueensBoardGame)
    NQueensGameTheme {
        BoardView(
            boardState = state,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChessBoardPreview8x8() {
    val nQueensBoardGame = NQueensBoardGame(8)
    nQueensBoardGame.insertPiece(Queen(PieceColor.WHITE), BoardPosition.of(0, 0))
    nQueensBoardGame.insertPiece(Queen(PieceColor.WHITE), BoardPosition.of(2, 2))
    val state = NQueensBoardGameBoardState(nQueensBoardGame)
    NQueensGameTheme {
        BoardView(
            boardState = state,
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChessBoardPreview6x6() {
    val nQueensBoardGame = NQueensBoardGame(6)
    NQueensGameTheme {
        BoardView(
            boardState = NQueensBoardGameBoardState(nQueensBoardGame),
        )
    }
}

