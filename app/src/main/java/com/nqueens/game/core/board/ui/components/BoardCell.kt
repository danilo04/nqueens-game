package com.nqueens.game.core.board.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.nqueens.game.R
import com.nqueens.game.core.board.domain.Spot
import com.nqueens.game.core.board.ui.state.CellState
import com.nqueens.game.core.board.ui.state.SelectedState
import com.nqueens.game.core.design.theme.errorDark
import com.nqueens.game.core.icons.ChessGamesIcons
import com.nqueens.game.core.icons.pieces.WhiteQueen

@Composable
fun RowScope.BoardCell(
    row: Int,
    col: Int,
    cellState: CellState,
    modifier: Modifier = Modifier,
    onCellTapped: ((Int, Int) -> Unit)? = null,
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val lightColor = primaryColor.copy(alpha = 0.3f)
    val darkColor = primaryColor.copy(alpha = 0.8f)

    val isDarkSquare = (row + col) % 2 == 0
    val cellColor =
        when {
            cellState.hasErrorColor -> errorDark
            isDarkSquare -> darkColor
            else -> lightColor
        }

    Box(
        modifier =
            modifier
                .aspectRatio(1f)
                .weight(1f)
                .background(cellColor)
                .border(
                    width = 0.5.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f),
                ).clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    onCellTapped?.invoke(row, col)
                },
    ) {
        if (cellState.spot is Spot.PieceSpot) {
            Image(
                // NOTE(Danilo): once we implement new pieces, we need to change this icon.
                imageVector = ChessGamesIcons.Pieces.WhiteQueen,
                contentDescription = null,
                modifier =
                    Modifier
                        .fillMaxSize()
                        .padding(4.dp),
            )

            // Add X icon overlay when selected for deletion
            if (cellState.selected == SelectedState.TO_DELETE) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = stringResource(R.string.delete),
                    tint = Color.Red,
                    modifier =
                        Modifier
                            .align(Alignment.TopEnd)
                            .size(24.dp)
                            .padding(4.dp),
                )
            }
        }

        if (col == 0 || row == 0) {
            val coordinateColor =
                when {
                    cellState.hasErrorColor -> MaterialTheme.colorScheme.onError.copy(alpha = 0.9f)
                    isDarkSquare -> MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f)
                    else -> MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                }
            if (col == 0) {
                Text(
                    text = (row + 1).toString(),
                    style =
                        MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    color = coordinateColor,
                    modifier =
                        Modifier
                            .align(Alignment.BottomEnd)
                            .padding(2.dp),
                )
            }

            if (row == 0) {
                Text(
                    text = ('a' + col).toString(),
                    style =
                        MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                    color = coordinateColor,
                    modifier =
                        Modifier
                            .align(Alignment.TopStart)
                            .padding(2.dp),
                )
            }
        }
    }
}
