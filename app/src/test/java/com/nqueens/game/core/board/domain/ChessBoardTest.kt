package com.nqueens.game.core.board.domain

import com.nqueens.game.core.board.domain.pieces.Piece
import com.nqueens.game.core.board.domain.pieces.PieceColor
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock

/**
 * Comprehensive test suite for [ChessBoard] class.
 *
 * Tests cover all public methods, edge cases, boundary conditions,
 * and reactive state management using StateFlow.
 */
class ChessBoardTest {
    private lateinit var chessBoard: ChessBoard
    private lateinit var mockPiece: Piece

    @Before
    fun setUp() {
        chessBoard = ChessBoard(8) // Standard 8x8 chess board
        mockPiece =
            mock<Piece> {
                on { pieceColor }.thenReturn(PieceColor.WHITE)
            }
    }

    // Initialization Tests

    @Test
    fun `given new chessboard, when checking size, then returns correct board size`() {
        assertThat(chessBoard.size).isEqualTo(8)
    }

    @Test
    fun `given new chessboard, when checking all positions, then all positions are empty`() =
        runTest {
            for (x in 0 until chessBoard.size) {
                for (y in 0 until chessBoard.size) {
                    val position = BoardPosition.of(x, y)
                    val spot = chessBoard[position].first()
                    assertThat(spot).isEqualTo(Spot.EmptySpot)
                }
            }
        }

    @Test
    fun `given new chessboard, when checking pieces on board, then no pieces are present`() {
        assertThat(chessBoard.getPiecesOnBoard()).isEmpty()
    }

    @Test
    fun `given different board sizes, when creating chessboards, then correct sizes are set`() {
        val smallBoard = ChessBoard(4)
        val largeBoard = ChessBoard(12)

        assertThat(smallBoard.size).isEqualTo(4)
        assertThat(largeBoard.size).isEqualTo(12)
    }

    // Position Access Tests

    @Test
    fun `given valid position, when accessing position, then returns StateFlow with empty spot`() =
        runTest {
            val position = BoardPosition.of(3, 4)
            val stateFlow = chessBoard[position]

            assertThat(stateFlow).isNotNull
            assertThat(stateFlow.first()).isEqualTo(Spot.EmptySpot)
        }

    @Test
    fun `given negative coordinates, when accessing position, then throws IllegalArgumentException`() {
        val invalidPosition = BoardPosition.of(-1, 3)

        assertThatThrownBy { chessBoard[invalidPosition] }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("out of bounds")
    }

    @Test
    fun `given coordinates too large, when accessing position, then throws IllegalArgumentException`() {
        val invalidPosition = BoardPosition.of(8, 3) // Board is 0-7

        assertThatThrownBy { chessBoard[invalidPosition] }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("out of bounds")
    }

    @Test
    fun `given chessboard, when accessing all valid positions, then no exceptions are thrown`() =
        runTest {
            for (x in 0 until chessBoard.size) {
                for (y in 0 until chessBoard.size) {
                    val position = BoardPosition.of(x, y)
                    // Should not throw exception
                    val stateFlow = chessBoard[position]
                    assertThat(stateFlow.first()).isEqualTo(Spot.EmptySpot)
                }
            }
        }

    // Piece Placement Tests

    @Test
    fun `given empty spot, when placing piece, then piece is placed successfully`() =
        runTest {
            val position = BoardPosition.of(4, 4)
            val pieceSpot = Spot.PieceSpot(mockPiece)

            chessBoard.setSpot(pieceSpot, position)

            val retrievedSpot = chessBoard[position].first()
            assertThat(retrievedSpot).isEqualTo(pieceSpot)
        }

    @Test
    fun `given empty spot, when placing piece, then pieces tracking is updated`() {
        val position = BoardPosition.of(2, 6)
        val pieceSpot = Spot.PieceSpot(mockPiece)

        chessBoard.setSpot(pieceSpot, position)

        val piecesOnBoard = chessBoard.getPiecesOnBoard()
        assertThat(piecesOnBoard).hasSize(1)
        assertThat(piecesOnBoard[position]).isEqualTo(pieceSpot)
    }

    @Test
    fun `given existing piece, when placing new piece, then piece is replaced`() =
        runTest {
            val position = BoardPosition.of(1, 1)
            val originalPiece = Spot.PieceSpot(mockPiece)
            val newMockPiece =
                mock<Piece> {
                    on { pieceColor }.thenReturn(PieceColor.BLACK)
                }
            val newPiece = Spot.PieceSpot(newMockPiece)

            // Place original piece
            chessBoard.setSpot(originalPiece, position)
            assertThat(chessBoard[position].first()).isEqualTo(originalPiece)

            // Replace with new piece
            chessBoard.setSpot(newPiece, position)
            val retrievedSpot = chessBoard[position].first()
            assertThat(retrievedSpot).isEqualTo(newPiece)

            // Verify only new piece is tracked
            val piecesOnBoard = chessBoard.getPiecesOnBoard()
            assertThat(piecesOnBoard).hasSize(1)
            assertThat(piecesOnBoard[position]).isEqualTo(newPiece)
        }

    @Test
    fun `given piece on board, when setting empty spot, then piece is removed`() =
        runTest {
            val position = BoardPosition.of(7, 0)
            val pieceSpot = Spot.PieceSpot(mockPiece)

            // Place piece
            chessBoard.setSpot(pieceSpot, position)
            assertThat(chessBoard.getPiecesOnBoard()).hasSize(1)

            // Remove piece by setting empty spot
            chessBoard.setSpot(Spot.EmptySpot, position)

            assertThat(chessBoard[position].first()).isEqualTo(Spot.EmptySpot)
            assertThat(chessBoard.getPiecesOnBoard()).isEmpty()
        }

    @Test
    fun `given out of bounds position, when placing piece, then throws IllegalArgumentException`() {
        val invalidPosition = BoardPosition.of(10, 5)
        val pieceSpot = Spot.PieceSpot(mockPiece)

        assertThatThrownBy { chessBoard.setSpot(pieceSpot, invalidPosition) }
            .isInstanceOf(IllegalArgumentException::class.java)
            .hasMessageContaining("out of bounds")
    }

    // Piece Tracking Tests

    @Test
    fun `given multiple pieces, when placing them on board, then all pieces are tracked correctly`() {
        val positions =
            listOf(
                BoardPosition.of(0, 0),
                BoardPosition.of(2, 3),
                BoardPosition.of(7, 7),
                BoardPosition.of(4, 1),
            )
        val pieces = positions.map { Spot.PieceSpot(mockPiece) }

        // Place pieces
        positions.zip(pieces).forEach { (position, piece) ->
            chessBoard.setSpot(piece, position)
        }

        val piecesOnBoard = chessBoard.getPiecesOnBoard()
        assertThat(piecesOnBoard).hasSize(4)
        positions.forEach { position ->
            assertThat(piecesOnBoard).containsKey(position)
        }
    }

    @Test
    fun `given empty board, when getting pieces on board, then returns empty map`() {
        assertThat(chessBoard.getPiecesOnBoard()).isEmpty()
    }

    @Test
    fun `given empty spot, when setting empty spot, then no pieces are tracked`() {
        val position = BoardPosition.of(3, 3)
        chessBoard.setSpot(Spot.EmptySpot, position)

        assertThat(chessBoard.getPiecesOnBoard()).isEmpty()
    }

    // Board Clearing Tests

    @Test
    fun `given board with pieces, when clearing board, then all pieces are removed`() =
        runTest {
            // Place multiple pieces
            val positions =
                listOf(
                    BoardPosition.of(1, 1),
                    BoardPosition.of(3, 5),
                    BoardPosition.of(6, 2),
                )
            positions.forEach { position ->
                chessBoard.setSpot(Spot.PieceSpot(mockPiece), position)
            }

            // Verify pieces are placed
            assertThat(chessBoard.getPiecesOnBoard()).hasSize(3)

            // Clear board
            chessBoard.clear()

            // Verify all positions are empty
            for (x in 0 until chessBoard.size) {
                for (y in 0 until chessBoard.size) {
                    val position = BoardPosition.of(x, y)
                    assertThat(chessBoard[position].first()).isEqualTo(Spot.EmptySpot)
                }
            }

            // Verify pieces tracking is cleared
            assertThat(chessBoard.getPiecesOnBoard()).isEmpty()
        }

    @Test
    fun `given empty board, when clearing board, then board remains empty without exceptions`() =
        runTest {
            chessBoard.clear()

            // Should still be empty and not throw exception
            assertThat(chessBoard.getPiecesOnBoard()).isEmpty()
            val position = BoardPosition.of(0, 0)
            assertThat(chessBoard[position].first()).isEqualTo(Spot.EmptySpot)
        }

    // StateFlow Reactivity Tests

    @Test
    fun `given position StateFlow, when changing spot, then StateFlow emits new state`() =
        runTest {
            val position = BoardPosition.of(5, 3)
            val stateFlow = chessBoard[position]
            val pieceSpot = Spot.PieceSpot(mockPiece)

            // Initial state should be empty
            assertThat(stateFlow.first()).isEqualTo(Spot.EmptySpot)

            // Place piece and verify state change
            chessBoard.setSpot(pieceSpot, position)
            assertThat(stateFlow.first()).isEqualTo(pieceSpot)

            // Remove piece and verify state change
            chessBoard.setSpot(Spot.EmptySpot, position)
            assertThat(stateFlow.first()).isEqualTo(Spot.EmptySpot)
        }

    @Test
    fun `given different positions, when changing one position, then other positions remain unchanged`() =
        runTest {
            val position1 = BoardPosition.of(0, 0)
            val position2 = BoardPosition.of(7, 7)
            val stateFlow1 = chessBoard[position1]
            val stateFlow2 = chessBoard[position2]

            // Place piece at position1 only
            chessBoard.setSpot(Spot.PieceSpot(mockPiece), position1)

            // Position1 should have piece, position2 should remain empty
            assertThat(stateFlow1.first()).isInstanceOf(Spot.PieceSpot::class.java)
            assertThat(stateFlow2.first()).isEqualTo(Spot.EmptySpot)
        }

    // Edge Cases Tests

    @Test
    fun `given minimum board size, when creating board, then 1x1 board works correctly`() =
        runTest {
            val tinyBoard = ChessBoard(1)
            val position = BoardPosition.of(0, 0)

            assertThat(tinyBoard.size).isEqualTo(1)
            assertThat(tinyBoard[position].first()).isEqualTo(Spot.EmptySpot)

            tinyBoard.setSpot(Spot.PieceSpot(mockPiece), position)
            assertThat(tinyBoard.getPiecesOnBoard()).hasSize(1)
        }

    @Test
    fun `given large board size, when creating board, then large board is handled correctly`() {
        val largeBoard = ChessBoard(100)
        assertThat(largeBoard.size).isEqualTo(100)
        assertThat(largeBoard.getPiecesOnBoard()).isEmpty()
    }

    @Test
    fun `given boundary positions, when accessing them, then all corner positions work correctly`() =
        runTest {
            val boundaryPositions =
                listOf(
                    BoardPosition.of(0, 0), // Top-left corner
                    BoardPosition.of(0, 7), // Top-right corner
                    BoardPosition.of(7, 0), // Bottom-left corner
                    BoardPosition.of(7, 7), // Bottom-right corner
                )

            boundaryPositions.forEach { position ->
                // Should be accessible without exception
                val stateFlow = chessBoard[position]
                assertThat(stateFlow.first()).isEqualTo(Spot.EmptySpot)

                // Should be able to place and remove pieces
                chessBoard.setSpot(Spot.PieceSpot(mockPiece), position)
                assertThat(stateFlow.first()).isInstanceOf(Spot.PieceSpot::class.java)
            }
        }
}
