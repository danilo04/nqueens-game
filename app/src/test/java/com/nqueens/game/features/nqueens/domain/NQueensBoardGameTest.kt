package com.nqueens.game.features.nqueens.domain

import com.nqueens.game.BaseUnitTest
import com.nqueens.game.core.board.domain.BoardPosition
import com.nqueens.game.core.board.domain.Spot
import com.nqueens.game.core.board.domain.games.GameState
import com.nqueens.game.core.board.domain.pieces.PieceColor
import com.nqueens.game.core.board.domain.pieces.QueenPiece
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class NQueensBoardGameTest : BaseUnitTest() {
    private lateinit var nQueensGame4x4: NQueensBoardGame
    private lateinit var nQueensGame8x8: NQueensBoardGame
    private lateinit var whiteQueen: QueenPiece

    @Before
    fun setUp() {
        nQueensGame4x4 = NQueensBoardGame(4)
        nQueensGame8x8 = NQueensBoardGame(8)
        whiteQueen = QueenPiece(PieceColor.WHITE)
    }

    // Initialization Tests

    @Test
    fun `given an N-Queens game, when created, then initial state is correct`() =
        runTest {
            assertThat(nQueensGame4x4.n).isEqualTo(4)
            assertThat(nQueensGame4x4.board.size).isEqualTo(4)
            assertThat(nQueensGame4x4.currentPlayer).isEqualTo(PieceColor.WHITE)
            assertThat(nQueensGame4x4.numPlayers).isEqualTo(1)
            assertThat(nQueensGame4x4.gameState.first()).isEqualTo(GameState.NOT_STARTED)
            assertThat(nQueensGame4x4.queensPlaced.first()).isEqualTo(0)
            assertThat(nQueensGame4x4.boardPositionsAttacked).isEmpty()
        }

    @Test
    fun `given an N-Queens game, when initialized, then game state changes to IN_PROGRESS`() =
        runTest {
            nQueensGame4x4.initialize()

            assertThat(nQueensGame4x4.gameState.first()).isEqualTo(GameState.IN_PROGRESS)
        }

    @Test
    fun `given different board sizes, when created, then properties are set correctly`() =
        runTest {
            val game1x1 = NQueensBoardGame(1)
            val game12x12 = NQueensBoardGame(12)

            assertThat(game1x1.n).isEqualTo(1)
            assertThat(game1x1.board.size).isEqualTo(1)

            assertThat(game12x12.n).isEqualTo(12)
            assertThat(game12x12.board.size).isEqualTo(12)
        }

    // Piece Insertion Tests

    @Test
    fun `given an initialized game, when inserting a valid queen, then queen is placed and tracked`() =
        runTest {
            nQueensGame4x4.initialize()
            val position = BoardPosition.of(0, 0)

            val result = nQueensGame4x4.insertPiece(whiteQueen, position)

            assertThat(result).isTrue()
            assertThat(nQueensGame4x4.queensPlaced.first()).isEqualTo(1)
            assertThat(nQueensGame4x4.board[position].first()).isInstanceOf(Spot.PieceSpot::class.java)
            assertThat(nQueensGame4x4.boardPositionsAttacked).containsKey(position)
            assertThat(nQueensGame4x4.gameState.first()).isEqualTo(GameState.IN_PROGRESS)
        }

    @Test
    fun `given a blocked game, when trying to insert a piece, then insertion fails`() =
        runTest {
            nQueensGame4x4.initialize()
            // Place two queens that attack each other
            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(0, 0))
            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(1, 1)) // Diagonal attack

            assertThat(nQueensGame4x4.gameState.first()).isEqualTo(GameState.BLOCKED)

            // Try to insert another piece
            val result = nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(2, 2))

            assertThat(result).isFalse()
            assertThat(nQueensGame4x4.queensPlaced.first()).isEqualTo(2) // Should remain 2
        }

    @Test
    fun `given a 4x4 game, when placing all 4 queens correctly, then game is solved`() =
        runTest {
            nQueensGame4x4.initialize()

            // Valid 4-Queens solution: (1,0), (3,1), (0,2), (2,3)
            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(1, 0))
            assertThat(nQueensGame4x4.gameState.first()).isEqualTo(GameState.IN_PROGRESS)

            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(3, 1))
            assertThat(nQueensGame4x4.gameState.first()).isEqualTo(GameState.IN_PROGRESS)

            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(0, 2))
            assertThat(nQueensGame4x4.gameState.first()).isEqualTo(GameState.IN_PROGRESS)

            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(2, 3))

            assertThat(nQueensGame4x4.gameState.first()).isEqualTo(GameState.SOLVED)
            assertThat(nQueensGame4x4.queensPlaced.first()).isEqualTo(4)
            assertThat(nQueensGame4x4.isGameSolved()).isTrue()
        }

    // Conflict Detection Tests

    @Test
    fun `given a queen placed, when placing another queen in same row, then game becomes blocked`() =
        runTest {
            nQueensGame4x4.initialize()
            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(0, 0))

            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(1, 0)) // Same row

            assertThat(nQueensGame4x4.gameState.first()).isEqualTo(GameState.BLOCKED)
        }

    @Test
    fun `given a queen placed, when placing another queen in same column, then game becomes blocked`() =
        runTest {
            nQueensGame4x4.initialize()
            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(0, 0))

            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(0, 1)) // Same column

            assertThat(nQueensGame4x4.gameState.first()).isEqualTo(GameState.BLOCKED)
        }

    @Test
    fun `given a queen placed, when placing another queen on diagonal, then game becomes blocked`() =
        runTest {
            nQueensGame4x4.initialize()
            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(0, 0))

            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(1, 1)) // Diagonal

            assertThat(nQueensGame4x4.gameState.first()).isEqualTo(GameState.BLOCKED)
        }

    @Test
    fun `given queens attacking each other, when checking attacked positions, then positions are tracked correctly`() =
        runTest {
            nQueensGame4x4.initialize()
            val position1 = BoardPosition.of(0, 0)
            val position2 = BoardPosition.of(1, 1)

            nQueensGame4x4.insertPiece(whiteQueen, position1)
            nQueensGame4x4.insertPiece(whiteQueen, position2)

            val attackedByFirst = nQueensGame4x4.boardPositionsAttacked[position1]
            val attackedBySecond = nQueensGame4x4.boardPositionsAttacked[position2]

            assertThat(attackedByFirst).contains(position2)
            assertThat(attackedBySecond).contains(position1)
        }

    // Piece Removal Tests

    @Test
    fun `given a queen placed, when removing it, then queen count decreases and board is updated`() =
        runTest {
            nQueensGame4x4.initialize()
            val position = BoardPosition.of(0, 0)
            nQueensGame4x4.insertPiece(whiteQueen, position)

            nQueensGame4x4.removePiece(position)

            assertThat(nQueensGame4x4.queensPlaced.first()).isEqualTo(0)
            assertThat(nQueensGame4x4.board[position].first()).isEqualTo(Spot.EmptySpot)
            assertThat(nQueensGame4x4.boardPositionsAttacked).doesNotContainKey(position)
        }

    @Test
    fun `given multiple queens with minimum at zero, when removing pieces, then count never goes below zero`() =
        runTest {
            nQueensGame4x4.initialize()

            // Try to remove piece when no pieces are placed
            nQueensGame4x4.removePiece(BoardPosition.of(0, 0))

            assertThat(nQueensGame4x4.queensPlaced.first()).isEqualTo(0)
        }

    @Test
    fun `given a blocked game, when removing conflicting piece, then game returns to IN_PROGRESS`() =
        runTest {
            nQueensGame4x4.initialize()
            val position1 = BoardPosition.of(0, 0)
            val position2 = BoardPosition.of(1, 1)

            // Create conflict
            nQueensGame4x4.insertPiece(whiteQueen, position1)
            nQueensGame4x4.insertPiece(whiteQueen, position2)
            assertThat(nQueensGame4x4.gameState.first()).isEqualTo(GameState.BLOCKED)

            // Remove one conflicting piece
            nQueensGame4x4.removePiece(position2)

            assertThat(nQueensGame4x4.gameState.first()).isEqualTo(GameState.IN_PROGRESS)
            assertThat(nQueensGame4x4.queensPlaced.first()).isEqualTo(1)
        }

    // Game Reset Tests

    @Test
    fun `given a game in progress, when resetting, then game returns to IN_PROGRESS state`() =
        runTest {
            nQueensGame4x4.initialize()
            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(0, 0))
            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(2, 1))

            nQueensGame4x4.resetGame()

            assertThat(nQueensGame4x4.gameState.first()).isEqualTo(GameState.IN_PROGRESS)
            assertThat(nQueensGame4x4.queensPlaced.first()).isEqualTo(0)
            assertThat(nQueensGame4x4.boardPositionsAttacked).isEmpty()
            assertThat(nQueensGame4x4.board.getPiecesOnBoard()).isEmpty()
        }

    @Test
    fun `given a solved game, when resetting, then game returns to initial state`() =
        runTest {
            nQueensGame4x4.initialize()
            // Place valid solution
            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(1, 0))
            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(3, 1))
            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(0, 2))
            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(2, 3))
            assertThat(nQueensGame4x4.gameState.first()).isEqualTo(GameState.SOLVED)

            nQueensGame4x4.resetGame()

            assertThat(nQueensGame4x4.gameState.first()).isEqualTo(GameState.IN_PROGRESS)
            assertThat(nQueensGame4x4.queensPlaced.first()).isEqualTo(0)
            assertThat(nQueensGame4x4.boardPositionsAttacked).isEmpty()
            assertThat(nQueensGame4x4.isGameSolved()).isFalse()
        }

    @Test
    fun `given a blocked game, when resetting, then game returns to initial state`() =
        runTest {
            nQueensGame4x4.initialize()
            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(0, 0))
            nQueensGame4x4.insertPiece(whiteQueen, BoardPosition.of(1, 1)) // Creates conflict
            assertThat(nQueensGame4x4.gameState.first()).isEqualTo(GameState.BLOCKED)

            nQueensGame4x4.resetGame()

            assertThat(nQueensGame4x4.gameState.first()).isEqualTo(GameState.IN_PROGRESS)
            assertThat(nQueensGame4x4.queensPlaced.first()).isEqualTo(0)
            assertThat(nQueensGame4x4.boardPositionsAttacked).isEmpty()
        }

    // Edge Cases Tests

    @Test
    fun `given a 1x1 board, when placing one queen, then game is immediately solved`() =
        runTest {
            val game1x1 = NQueensBoardGame(1)
            game1x1.initialize()

            game1x1.insertPiece(whiteQueen, BoardPosition.of(0, 0))

            assertThat(game1x1.gameState.first()).isEqualTo(GameState.SOLVED)
            assertThat(game1x1.queensPlaced.first()).isEqualTo(1)
            assertThat(game1x1.isGameSolved()).isTrue()
        }

    @Test
    fun `given a large board, when placing queens correctly, then game mechanics work correctly`() =
        runTest {
            val game12x12 = NQueensBoardGame(12)
            game12x12.initialize()

            // Place a few queens in non-conflicting positions
            game12x12.insertPiece(whiteQueen, BoardPosition.of(0, 0))
            game12x12.insertPiece(whiteQueen, BoardPosition.of(2, 1))
            game12x12.insertPiece(whiteQueen, BoardPosition.of(4, 2))

            assertThat(game12x12.gameState.first()).isEqualTo(GameState.IN_PROGRESS)
            assertThat(game12x12.queensPlaced.first()).isEqualTo(3)
            assertThat(game12x12.boardPositionsAttacked).hasSize(3)
        }

    // Extension Function Tests

    @Test
    fun `given boardPositionsAttacked map, when checking if position is attacked, then extension function works correctly`() =
        runTest {
            nQueensGame4x4.initialize()
            val pos1 = BoardPosition.of(0, 0)
            val pos2 = BoardPosition.of(1, 1)
            val pos3 = BoardPosition.of(2, 2)

            nQueensGame4x4.insertPiece(whiteQueen, pos1)

            // pos2 should be attacked by the queen at pos1
            assertThat(nQueensGame4x4.boardPositionsAttacked.containsBoardPosition(pos2)).isTrue()
            // pos3 should be attacked by the queen at pos1
            assertThat(nQueensGame4x4.boardPositionsAttacked.containsBoardPosition(pos3)).isTrue()
            // A position not attacked should return false
            assertThat(nQueensGame4x4.boardPositionsAttacked.containsBoardPosition(BoardPosition.of(1, 2))).isFalse()
        }

    @Test
    fun `given empty boardPositionsAttacked map, when checking positions, then extension function returns false`() =
        runTest {
            assertThat(nQueensGame4x4.boardPositionsAttacked.containsBoardPosition(BoardPosition.of(0, 0))).isFalse()
        }

    // Game State Consistency Tests

    @Test
    fun `given various game operations, when checking state consistency, then states are maintained correctly`() =
        runTest {
            nQueensGame8x8.initialize()
            assertThat(nQueensGame8x8.gameState.first()).isEqualTo(GameState.IN_PROGRESS)

            // Place non-conflicting pieces
            nQueensGame8x8.insertPiece(whiteQueen, BoardPosition.of(0, 0))
            nQueensGame8x8.insertPiece(whiteQueen, BoardPosition.of(2, 1))
            assertThat(nQueensGame8x8.gameState.first()).isEqualTo(GameState.IN_PROGRESS)

            // Create conflict
            nQueensGame8x8.insertPiece(whiteQueen, BoardPosition.of(3, 3))
            assertThat(nQueensGame8x8.gameState.first()).isEqualTo(GameState.BLOCKED)

            // Remove conflict
            nQueensGame8x8.removePiece(BoardPosition.of(3, 3))
            assertThat(nQueensGame8x8.gameState.first()).isEqualTo(GameState.IN_PROGRESS)
        }

    @Test
    fun `given queens placed, when checking attacked positions tracking, then tracking is accurate`() =
        runTest {
            nQueensGame8x8.initialize()
            val centerPos = BoardPosition.of(3, 3)

            nQueensGame8x8.insertPiece(whiteQueen, centerPos)

            val attackedPositions = nQueensGame8x8.boardPositionsAttacked[centerPos]
            assertThat(attackedPositions).isNotNull

            // Check that it contains expected positions (sample check)
            assertThat(attackedPositions).contains(
                BoardPosition.of(3, 0), // Same column
                BoardPosition.of(0, 3), // Same row
                BoardPosition.of(0, 0), // Diagonal
                BoardPosition.of(6, 6), // Diagonal
            )
        }
}
