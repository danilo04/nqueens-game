package com.nqueens.game.features.nqueens.ui.state

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.nqueens.game.core.board.domain.BoardPosition
import com.nqueens.game.core.board.domain.Spot
import com.nqueens.game.core.board.domain.games.GameState
import com.nqueens.game.core.board.domain.pieces.PieceColor
import com.nqueens.game.core.board.domain.pieces.QueenPiece
import com.nqueens.game.core.board.ui.state.SelectedState
import com.nqueens.game.features.nqueens.domain.NQueensBoardGame
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class NQueensBoardUiStateTest {
    private lateinit var realGame: NQueensBoardGame
    private lateinit var uiState: NQueensBoardUiState

    @Before
    fun setUp() {
        realGame = NQueensBoardGame(4)
        realGame.initialize()
        uiState = NQueensBoardUiState(realGame)
    }

    @Test
    fun `given board size 4, when creating ui state, then board size is 4`() {
        // Then
        assertThat(uiState.boardSize).isEqualTo(4)
    }

    @Test
    fun `given empty cell, when tapping cell, then queen is placed and board flow emits changes`() =
        runTest {
            // Given
            val x = 0
            val y = 0
            val position = BoardPosition.of(x, y)

            // When/Then - Test the flow changes using Turbine
            realGame.board[position].test {
                // Initial state should be empty
                val initialState = awaitItem()
                assertThat(initialState).isEqualTo(Spot.EmptySpot)

                // Tap the cell to place a queen
                uiState.tapOnCell(x, y)

                // Should emit the new piece state
                val newState = awaitItem()
                assertThat(newState).isInstanceOf(Spot.PieceSpot::class.java)
                val pieceSpot = newState as Spot.PieceSpot
                assertThat(pieceSpot.piece).isInstanceOf(QueenPiece::class.java)
                assertThat(pieceSpot.piece.pieceColor).isEqualTo(PieceColor.WHITE)
            }
        }

    @Test
    fun `given piece on cell, when tapping cell, then cell ui state shows selection changes`() =
        runTest {
            // Given
            val x = 1
            val y = 1
            val position = BoardPosition.of(x, y)

            // Place a piece first
            realGame.insertPiece(QueenPiece(PieceColor.WHITE), position)

            // When/Then - Test the cell state flow changes using Turbine
            uiState.getCellState(x, y).test {
                // Get the state with the piece placed
                val withPieceState = awaitItem()
                assertThat(withPieceState.spot).isInstanceOf(Spot.PieceSpot::class.java)
                assertThat(withPieceState.selected).isEqualTo(SelectedState.NOT_SELECTED)

                // Tap the cell to select for deletion
                uiState.tapOnCell(x, y)

                // Should emit the selected state
                val selectedState = awaitItem()
                assertThat(selectedState.selected).isEqualTo(SelectedState.TO_DELETE)
                assertThat(selectedState.spot).isInstanceOf(Spot.PieceSpot::class.java)
            }

            // Also verify the selection state directly
            assertThat(uiState.isCellSelected(position)).isTrue()
        }

    @Test
    fun `given selected piece, when tapping same cell again, then board flow shows piece removal`() =
        runTest {
            // Given
            val x = 2
            val y = 2
            val position = BoardPosition.of(x, y)

            // Place a piece and select it
            realGame.insertPiece(QueenPiece(PieceColor.WHITE), position)
            uiState.tapOnCell(x, y) // First tap to select

            // When/Then - Test the board flow changes using Turbine
            uiState.getCellState(x, y).test {
                // Get the state with piece placed
                val withPieceState = awaitItem()
                assertThat(withPieceState.spot).isInstanceOf(Spot.PieceSpot::class.java)

                // Second tap to remove the piece
                uiState.tapOnCell(x, y)

                // Should emit empty spot
                val removedState = awaitItem()
                assertThat(removedState.spot).isEqualTo(Spot.EmptySpot)
                // Also verify selection state is cleared
                assertThat(uiState.isCellSelected(position)).isFalse()

                assertThat(awaitItem().selected).isEqualTo(SelectedState.NOT_SELECTED)
            }
        }

    @Test
    fun `given placing queens that attack each other, when observing game state, then flow emits blocked state`() =
        runTest {
            // Given - Place a queen at (0,0)
            realGame.insertPiece(QueenPiece(PieceColor.WHITE), BoardPosition.of(0, 0))

            // When/Then - Test game state flow changes using Turbine
            realGame.gameState.test {
                // Skip the initial IN_PROGRESS state
                skipItems(1)

                // Place another queen in same row - should cause blocking
                uiState.tapOnCell(0, 1) // Same row as first queen

                // Should emit blocked state
                val blockedState = awaitItem()
                assertThat(blockedState).isEqualTo(GameState.BLOCKED)
            }
        }

    @Test
    fun `given queens placed count, when placing and removing queens, then flow emits count changes`() =
        runTest {
            // When/Then - Test queens placed count flow using Turbine
            realGame.queensPlaced.test {
                // Initial count should be 0
                val initialCount = awaitItem()
                assertThat(initialCount).isEqualTo(0)

                // Place first queen
                uiState.tapOnCell(0, 0)
                val afterFirstQueen = awaitItem()
                assertThat(afterFirstQueen).isEqualTo(1)

                // Place second queen
                uiState.tapOnCell(2, 2)
                val afterSecondQueen = awaitItem()
                assertThat(afterSecondQueen).isEqualTo(2)

                // Remove first queen (select and tap again)
                uiState.tapOnCell(0, 0) // Select
                uiState.tapOnCell(0, 0) // Remove
                val afterRemoval = awaitItem()
                assertThat(afterRemoval).isEqualTo(1)
            }
        }

    @Test
    fun `given game in progress, when placing non-attacking queens, then no error states in cell flows`() =
        runTest {
            // When placing non-attacking queens
            uiState.tapOnCell(0, 1) // Queen at (0,1)
            uiState.tapOnCell(2, 0) // Queen at (2,0) - doesn't attack (0,1)

            // Then - verify game state
            assertThat(realGame.gameState.value).isEqualTo(GameState.IN_PROGRESS)

            // Test cell states using Turbine - both should show no errors
            uiState.getCellState(0, 1).test {
                val cellState1 = awaitItem()
                assertThat(cellState1.hasErrorColor).isFalse()
            }

            uiState.getCellState(2, 0).test {
                val cellState2 = awaitItem()
                assertThat(cellState2.hasErrorColor).isFalse()
            }
        }

    @Test
    fun `when resetting game, then all flows emit reset states`() =
        runTest {
            // Given - Place some pieces and create error state
            realGame.insertPiece(QueenPiece(PieceColor.WHITE), BoardPosition.of(0, 0))
            realGame.insertPiece(QueenPiece(PieceColor.WHITE), BoardPosition.of(0, 1)) // Attacking position

            // Select a piece
            uiState.tapOnCell(0, 0)
            val selectedPosition = BoardPosition.of(0, 0)
            assertThat(uiState.isCellSelected(selectedPosition)).isTrue()

            // When/Then - Test game state flow during reset
            realGame.gameState.test {
                skipItems(1) // Skip current state

                uiState.resetGame()

                // Should emit NOT_STARTED state
                val resetState = awaitItem()
                assertThat(resetState).isEqualTo(GameState.NOT_STARTED)
            }

            // Test queens count flow during reset
            realGame.queensPlaced.test {
                val resetCount = awaitItem()
                assertThat(resetCount).isEqualTo(0)
            }

            // Verify all selections are cleared
            assertThat(uiState.isCellSelected(selectedPosition)).isFalse()
            assertThat(uiState.isCellSelected(BoardPosition.of(0, 1))).isFalse()
        }

    @Test
    fun `given valid 4-queens solution, when placing all queens, then game state flow emits solved`() =
        runTest {
            // When/Then - Test solving the game using Turbine
            realGame.gameState.test {
                skipItems(1) // Skip initial state

                // Place queens in a valid 4x4 solution: (1,0), (3,1), (0,2), (2,3)
                uiState.tapOnCell(1, 0)
                // Skip intermediate states as needed
                skipItems(1) // Skip any intermediate game state changes

                uiState.tapOnCell(3, 1)
                skipItems(1)

                uiState.tapOnCell(0, 2)
                skipItems(1)

                uiState.tapOnCell(2, 3)

                // Should emit solved state
                val solvedState = awaitItem()
                assertThat(solvedState).isEqualTo(GameState.SOLVED)
            }

            // Verify final queen count
            assertThat(realGame.queensPlaced.value).isEqualTo(4)
        }

    @Test
    fun `given a selected piece, when tapping on another piece, then first piece is NOT_SELECTED and second piece is TO_DELETE`() =
        runTest {
            // Given
            val x1 = 2
            val y1 = 2
            val position = BoardPosition.of(x1, y1)
            // Place a piece and select it
            realGame.insertPiece(QueenPiece(PieceColor.WHITE), position)
            val x2 = 0
            val y2 = 1
            val position2 = BoardPosition.of(x2, y2)
            realGame.insertPiece(QueenPiece(PieceColor.WHITE), position2)

            turbineScope {
                val cell1 = uiState.getCellState(x1, y1).testIn(backgroundScope)
                val cell2 = uiState.getCellState(x2, y2).testIn(backgroundScope)

                uiState.tapOnCell(x1, x1)

                cell1.skipItems(1)
                cell2.skipItems(1)
                val cell1State1 = cell1.awaitItem()
                assertThat(cell1State1.selected).isEqualTo(SelectedState.TO_DELETE)

                uiState.tapOnCell(x2, y2)

                val cell1State2 = cell1.awaitItem()
                assertThat(cell1State2.selected).isEqualTo(SelectedState.NOT_SELECTED)
                val cell2State1 = cell2.awaitItem()
                assertThat(cell2State1.selected).isEqualTo(SelectedState.TO_DELETE)
            }
        }
}
