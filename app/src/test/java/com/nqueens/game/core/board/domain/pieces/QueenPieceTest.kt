package com.nqueens.game.core.board.domain.pieces

import com.nqueens.game.BaseUnitTest
import com.nqueens.game.core.board.domain.BoardPosition
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class QueenPieceTest : BaseUnitTest() {
    private val queen = QueenPiece(PieceColor.WHITE)
    private val board4x4 = 4
    private val board8x8 = 8
    private val board9x9 = 9

    @Test
    fun `given an empty 4x4 chess board, when putting a queen on A1, the possible moves are available`() {
        val initialPosition = BoardPosition.of(x = 0, y = 0)

        val result = queen.getPossibleMoves(board4x4, initialPosition, emptyMap())

        val expected =
            setOf(
                BoardPosition.of(1, 1),
                BoardPosition.of(2, 2),
                BoardPosition.of(3, 3),
                BoardPosition.of(0, 1),
                BoardPosition.of(0, 2),
                BoardPosition.of(0, 3),
                BoardPosition.of(1, 0),
                BoardPosition.of(2, 0),
                BoardPosition.of(3, 0),
            )

        assertThat(expected).isEqualTo(result.toSet())
    }

    @Test
    fun `given an empty 4x4 chess board, when putting a queen on C3, the possible moves are available`() {
        val initialPosition = BoardPosition.of(x = 2, y = 2)

        val result = queen.getPossibleMoves(board4x4, initialPosition, emptyMap())

        val expected =
            setOf(
                // Horizontal
                BoardPosition.of(0, 2),
                BoardPosition.of(1, 2),
                BoardPosition.of(3, 2),
                // Vertical
                BoardPosition.of(2, 0),
                BoardPosition.of(2, 1),
                BoardPosition.of(2, 3),
                // Diagonal up-right
                BoardPosition.of(3, 3),
                // Diagonal down-left
                BoardPosition.of(1, 1),
                BoardPosition.of(0, 0),
                // Diagonal down-right
                BoardPosition.of(3, 1),
                // Diagonal up-left
                BoardPosition.of(1, 3),
            )

        assertThat(expected).isEqualTo(result.toSet())
    }

    @Test
    fun `given an empty 4x4 chess board, when putting a queen on D4, the possible moves are available`() {
        val initialPosition = BoardPosition.of(x = 3, y = 3)

        val result = queen.getPossibleMoves(board4x4, initialPosition, emptyMap())

        val expected =
            setOf(
                // Horizontal
                BoardPosition.of(0, 3),
                BoardPosition.of(1, 3),
                BoardPosition.of(2, 3),
                // Vertical
                BoardPosition.of(3, 0),
                BoardPosition.of(3, 1),
                BoardPosition.of(3, 2),
                // Diagonal down-left
                BoardPosition.of(2, 2),
                BoardPosition.of(1, 1),
                BoardPosition.of(0, 0),
            )

        assertThat(expected).isEqualTo(result.toSet())
    }

    @Test
    fun `given an empty 8x8 chess board, when putting a queen on D4, the possible moves are available`() {
        val initialPosition = BoardPosition.of(x = 3, y = 3)

        val result = queen.getPossibleMoves(board8x8, initialPosition, emptyMap())

        val expected =
            setOf(
                // Horizontal
                BoardPosition.of(0, 3),
                BoardPosition.of(1, 3),
                BoardPosition.of(2, 3),
                BoardPosition.of(4, 3),
                BoardPosition.of(5, 3),
                BoardPosition.of(6, 3),
                BoardPosition.of(7, 3),
                // Vertical
                BoardPosition.of(3, 0),
                BoardPosition.of(3, 1),
                BoardPosition.of(3, 2),
                BoardPosition.of(3, 4),
                BoardPosition.of(3, 5),
                BoardPosition.of(3, 6),
                BoardPosition.of(3, 7),
                // Diagonal up-right
                BoardPosition.of(4, 4),
                BoardPosition.of(5, 5),
                BoardPosition.of(6, 6),
                BoardPosition.of(7, 7),
                // Diagonal down-left
                BoardPosition.of(2, 2),
                BoardPosition.of(1, 1),
                BoardPosition.of(0, 0),
                // Diagonal up-left
                BoardPosition.of(2, 4),
                BoardPosition.of(1, 5),
                BoardPosition.of(0, 6),
                // Diagonal down-right
                BoardPosition.of(4, 2),
                BoardPosition.of(5, 1),
                BoardPosition.of(6, 0),
            )

        assertThat(expected).isEqualTo(result.toSet())
    }

    @Test
    fun `given an empty 8x8 chess board, when putting a queen on A8, the possible moves are available`() {
        val initialPosition = BoardPosition.of(x = 0, y = 7)

        val result = queen.getPossibleMoves(board8x8, initialPosition, emptyMap())

        val expected =
            setOf(
                // Horizontal
                BoardPosition.of(1, 7),
                BoardPosition.of(2, 7),
                BoardPosition.of(3, 7),
                BoardPosition.of(4, 7),
                BoardPosition.of(5, 7),
                BoardPosition.of(6, 7),
                BoardPosition.of(7, 7),
                // Vertical
                BoardPosition.of(0, 0),
                BoardPosition.of(0, 1),
                BoardPosition.of(0, 2),
                BoardPosition.of(0, 3),
                BoardPosition.of(0, 4),
                BoardPosition.of(0, 5),
                BoardPosition.of(0, 6),
                // Diagonal down-right
                BoardPosition.of(1, 6),
                BoardPosition.of(2, 5),
                BoardPosition.of(3, 4),
                BoardPosition.of(4, 3),
                BoardPosition.of(5, 2),
                BoardPosition.of(6, 1),
                BoardPosition.of(7, 0),
            )

        assertThat(expected).isEqualTo(result.toSet())
    }

    @Test
    fun `given an empty 8x8 chess board, when putting a queen on E5, the possible moves are available`() {
        val initialPosition = BoardPosition.of(x = 4, y = 4)

        val result = queen.getPossibleMoves(board8x8, initialPosition, emptyMap())

        val expected =
            setOf(
                // Horizontal
                BoardPosition.of(0, 4),
                BoardPosition.of(1, 4),
                BoardPosition.of(2, 4),
                BoardPosition.of(3, 4),
                BoardPosition.of(5, 4),
                BoardPosition.of(6, 4),
                BoardPosition.of(7, 4),
                // Vertical
                BoardPosition.of(4, 0),
                BoardPosition.of(4, 1),
                BoardPosition.of(4, 2),
                BoardPosition.of(4, 3),
                BoardPosition.of(4, 5),
                BoardPosition.of(4, 6),
                BoardPosition.of(4, 7),
                // Diagonal up-right
                BoardPosition.of(5, 5),
                BoardPosition.of(6, 6),
                BoardPosition.of(7, 7),
                // Diagonal down-left
                BoardPosition.of(3, 3),
                BoardPosition.of(2, 2),
                BoardPosition.of(1, 1),
                BoardPosition.of(0, 0),
                // Diagonal up-left
                BoardPosition.of(3, 5),
                BoardPosition.of(2, 6),
                BoardPosition.of(1, 7),
                // Diagonal down-right
                BoardPosition.of(5, 3),
                BoardPosition.of(6, 2),
                BoardPosition.of(7, 1),
            )

        assertThat(expected).isEqualTo(result.toSet())
    }

    @Test
    fun `given an empty 9x9 chess board, when putting a queen on E5, the possible moves are available`() {
        val initialPosition = BoardPosition.of(x = 4, y = 4)

        val result = queen.getPossibleMoves(board9x9, initialPosition, emptyMap())

        val expected =
            setOf(
                // Horizontal
                BoardPosition.of(0, 4),
                BoardPosition.of(1, 4),
                BoardPosition.of(2, 4),
                BoardPosition.of(3, 4),
                BoardPosition.of(5, 4),
                BoardPosition.of(6, 4),
                BoardPosition.of(7, 4),
                BoardPosition.of(8, 4),
                // Vertical
                BoardPosition.of(4, 0),
                BoardPosition.of(4, 1),
                BoardPosition.of(4, 2),
                BoardPosition.of(4, 3),
                BoardPosition.of(4, 5),
                BoardPosition.of(4, 6),
                BoardPosition.of(4, 7),
                BoardPosition.of(4, 8),
                // Diagonal up-right
                BoardPosition.of(5, 5),
                BoardPosition.of(6, 6),
                BoardPosition.of(7, 7),
                BoardPosition.of(8, 8),
                // Diagonal down-left
                BoardPosition.of(3, 3),
                BoardPosition.of(2, 2),
                BoardPosition.of(1, 1),
                BoardPosition.of(0, 0),
                // Diagonal up-left
                BoardPosition.of(3, 5),
                BoardPosition.of(2, 6),
                BoardPosition.of(1, 7),
                BoardPosition.of(0, 8),
                // Diagonal down-right
                BoardPosition.of(5, 3),
                BoardPosition.of(6, 2),
                BoardPosition.of(7, 1),
                BoardPosition.of(8, 0),
            )

        assertThat(expected).isEqualTo(result.toSet())
    }

    @Test
    fun `given an empty 9x9 chess board, when putting a queen on A1, the possible moves are available`() {
        val initialPosition = BoardPosition.of(x = 0, y = 0)

        val result = queen.getPossibleMoves(board9x9, initialPosition, emptyMap())

        val expected =
            setOf(
                // Horizontal
                BoardPosition.of(1, 0),
                BoardPosition.of(2, 0),
                BoardPosition.of(3, 0),
                BoardPosition.of(4, 0),
                BoardPosition.of(5, 0),
                BoardPosition.of(6, 0),
                BoardPosition.of(7, 0),
                BoardPosition.of(8, 0),
                // Vertical
                BoardPosition.of(0, 1),
                BoardPosition.of(0, 2),
                BoardPosition.of(0, 3),
                BoardPosition.of(0, 4),
                BoardPosition.of(0, 5),
                BoardPosition.of(0, 6),
                BoardPosition.of(0, 7),
                BoardPosition.of(0, 8),
                // Diagonal up-right
                BoardPosition.of(1, 1),
                BoardPosition.of(2, 2),
                BoardPosition.of(3, 3),
                BoardPosition.of(4, 4),
                BoardPosition.of(5, 5),
                BoardPosition.of(6, 6),
                BoardPosition.of(7, 7),
                BoardPosition.of(8, 8),
            )

        assertThat(expected).isEqualTo(result.toSet())
    }

    @Test
    fun `given an empty 9x9 chess board, when putting a queen on I9, the possible moves are available`() {
        val initialPosition = BoardPosition.of(x = 8, y = 8)

        val result = queen.getPossibleMoves(board9x9, initialPosition, emptyMap())

        val expected =
            setOf(
                // Horizontal
                BoardPosition.of(0, 8),
                BoardPosition.of(1, 8),
                BoardPosition.of(2, 8),
                BoardPosition.of(3, 8),
                BoardPosition.of(4, 8),
                BoardPosition.of(5, 8),
                BoardPosition.of(6, 8),
                BoardPosition.of(7, 8),
                // Vertical
                BoardPosition.of(8, 0),
                BoardPosition.of(8, 1),
                BoardPosition.of(8, 2),
                BoardPosition.of(8, 3),
                BoardPosition.of(8, 4),
                BoardPosition.of(8, 5),
                BoardPosition.of(8, 6),
                BoardPosition.of(8, 7),
                // Diagonal down-left
                BoardPosition.of(7, 7),
                BoardPosition.of(6, 6),
                BoardPosition.of(5, 5),
                BoardPosition.of(4, 4),
                BoardPosition.of(3, 3),
                BoardPosition.of(2, 2),
                BoardPosition.of(1, 1),
                BoardPosition.of(0, 0),
            )

        assertThat(expected).isEqualTo(result.toSet())
    }

    @Test
    fun `given a board with enemy pieces, queen can capture but not move beyond them`() {
        val initialPosition = BoardPosition.of(x = 4, y = 4) // Queen at E5
        val enemyQueen = QueenPiece(PieceColor.BLACK)

        val currentPieces =
            mapOf(
                BoardPosition.of(4, 6) to enemyQueen,
                BoardPosition.of(2, 4) to enemyQueen,
                BoardPosition.of(6, 2) to enemyQueen,
            )

        val result = queen.getPossibleMoves(board8x8, initialPosition, currentPieces)

        // Queen should be able to capture enemy pieces
        assertThat(result).contains(
            BoardPosition.of(4, 6), // Enemy above
            BoardPosition.of(2, 4), // Enemy to the left
            BoardPosition.of(6, 2), // Enemy on down-right diagonal
        )

        // Queen shouldn't be able to move beyond enemy pieces
        assertThat(result).doesNotContain(
            BoardPosition.of(4, 7), // Beyond enemy above
            BoardPosition.of(1, 4), // Beyond enemy to the left
            BoardPosition.of(7, 1), // Beyond enemy on down-right diagonal
        )
    }

    @Test
    fun `given a board with friendly pieces, queen cannot move to or beyond them`() {
        val initialPosition = BoardPosition.of(x = 4, y = 4) // Queen at E5
        val friendlyQueen = QueenPiece(PieceColor.WHITE)

        val currentPieces =
            mapOf(
                BoardPosition.of(4, 2) to friendlyQueen,
                BoardPosition.of(7, 4) to friendlyQueen,
                BoardPosition.of(2, 6) to friendlyQueen,
            )

        val result = queen.getPossibleMoves(board8x8, initialPosition, currentPieces)

        // Queen shouldn't be able to move to spaces with friendly pieces
        assertThat(result).doesNotContain(
            BoardPosition.of(4, 2), // Friendly below
            BoardPosition.of(7, 4), // Friendly to the right
            BoardPosition.of(2, 6), // Friendly on up-left diagonal
        )

        // Queen shouldn't be able to move beyond friendly pieces
        assertThat(result).doesNotContain(
            BoardPosition.of(4, 1), // Beyond friendly below
            BoardPosition.of(4, 0), // Beyond friendly below
            BoardPosition.of(8, 4), // Beyond friendly to the right (would be out of bounds anyway)
            BoardPosition.of(1, 7), // Beyond friendly on up-left diagonal
            BoardPosition.of(0, 8), // Beyond friendly on up-left diagonal
        )
    }

    @Test
    fun `given a complex board with mixed pieces, queen moves correctly`() {
        val initialPosition = BoardPosition.of(x = 3, y = 3) // Queen at D4

        // Setup pieces on the board
        val currentPieces =
            mapOf(
                // Friendly pieces
                BoardPosition.of(3, 1) to QueenPiece(PieceColor.WHITE), // D2 - blocks don
                BoardPosition.of(5, 5) to QueenPiece(PieceColor.WHITE), // F6 - blocks up-right diagonl
                // Enemy pieces
                BoardPosition.of(0, 3) to QueenPiece(PieceColor.BLACK), // A4 - can capture let
                BoardPosition.of(3, 6) to QueenPiece(PieceColor.BLACK), // D7 - can capture p
                BoardPosition.of(1, 5) to QueenPiece(PieceColor.BLACK), // B6 - can capture up-left diagonl
            )

        val result = queen.getPossibleMoves(board8x8, initialPosition, currentPieces)

        // Places queen should be able to move (no pieces blocking)
        assertThat(result).contains(
            // Right horizontal (unblocked)
            BoardPosition.of(4, 3),
            BoardPosition.of(5, 3),
            BoardPosition.of(6, 3),
            BoardPosition.of(7, 3),
            // Left horizontal (until enemy piece)
            BoardPosition.of(2, 3),
            BoardPosition.of(1, 3),
            BoardPosition.of(0, 3), // Can capture at A4
            // Up vertical (until enemy piece)
            BoardPosition.of(3, 4),
            BoardPosition.of(3, 5),
            BoardPosition.of(3, 6), // Can capture at D7
            // Down vertical (until friendly piece)
            BoardPosition.of(3, 2), // Can move until D2
            // Down-left diagonal (unblocked)
            BoardPosition.of(2, 2),
            BoardPosition.of(1, 1),
            BoardPosition.of(0, 0),
            // Down-right diagonal (unblocked)
            BoardPosition.of(4, 2),
            BoardPosition.of(5, 1),
            BoardPosition.of(6, 0),
            // Up-left diagonal (until enemy piece)
            BoardPosition.of(2, 4),
            BoardPosition.of(1, 5), // Can capture at B6
            // Up-right diagonal (until friendly piece)
            BoardPosition.of(4, 4), // Can move until F6
        )

        // Places queen shouldn't be able to move (blocked by pieces)
        assertThat(result).doesNotContain(
            // Blocked by friendly pieces
            BoardPosition.of(3, 1), // Friendly at D2
            BoardPosition.of(3, 0), // Beyond friendly at D2
            BoardPosition.of(5, 5), // Friendly at F6
            BoardPosition.of(6, 6), // Beyond friendly at F6
            BoardPosition.of(7, 7), // Beyond friendly at F6
            // Beyond enemy pieces
            BoardPosition.of(3, 7), // Beyond enemy at D7
            BoardPosition.of(0, 6), // Beyond enemy at B6
            BoardPosition.of(-1, 3), // Beyond enemy at A4 (would be out of bounds anyway)
        )
    }

    @Test
    fun `given a board with pieces surrounding queen, queen can only capture enemy pieces`() {
        val initialPosition = BoardPosition.of(x = 4, y = 4) // Queen at E5

        // Setup a mix of friendly and enemy pieces surrounding the queen
        val currentPieces =
            mapOf(
                // Friendly pieces (4 directions)
                BoardPosition.of(3, 3) to QueenPiece(PieceColor.WHITE), // Down-let
                BoardPosition.of(4, 3) to QueenPiece(PieceColor.WHITE), // Don
                BoardPosition.of(5, 5) to QueenPiece(PieceColor.WHITE), // Up-rigt
                BoardPosition.of(3, 5) to QueenPiece(PieceColor.WHITE), // Up-let
                // Enemy pieces (4 directions)
                BoardPosition.of(5, 3) to QueenPiece(PieceColor.BLACK), // Down-rigt
                BoardPosition.of(5, 4) to QueenPiece(PieceColor.BLACK), // Rigt
                BoardPosition.of(4, 5) to QueenPiece(PieceColor.BLACK), // p
                BoardPosition.of(3, 4) to QueenPiece(PieceColor.BLACK), // Let
            )

        val result = queen.getPossibleMoves(board8x8, initialPosition, currentPieces)

        // Queen should only be able to capture the 4 enemy pieces
        assertThat(result).containsExactlyInAnyOrder(
            BoardPosition.of(5, 3), // Enemy down-right
            BoardPosition.of(5, 4), // Enemy right
            BoardPosition.of(4, 5), // Enemy up
            BoardPosition.of(3, 4), // Enemy left
        )

        // Should not contain friendly pieces or any spaces beyond pieces
        assertThat(result).doesNotContain(
            BoardPosition.of(3, 3), // Friendly down-left
            BoardPosition.of(4, 3), // Friendly down
            BoardPosition.of(5, 5), // Friendly up-right
            BoardPosition.of(3, 5), // Friendly up-left
        )
    }
}
