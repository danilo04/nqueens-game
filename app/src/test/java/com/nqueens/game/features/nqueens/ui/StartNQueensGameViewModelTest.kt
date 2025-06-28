package com.nqueens.game.features.nqueens.ui

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class StartNQueensGameViewModelTest {
    private lateinit var viewModel: StartNQueensGameViewModel

    @Before
    fun setup() {
        viewModel = StartNQueensGameViewModel()
    }

    @Test
    fun `initial state should have empty values and no errors`() =
        runTest {
            val state = viewModel.uiState.first()

            assertEquals("", state.playerName)
            assertEquals("", state.numberOfQueens)
            assertFalse(state.playerNameError)
            assertFalse(state.numberOfQueensError)
            assertFalse(state.isFormValid)
        }

    @Test
    fun `updatePlayerName should update player name and clear error`() =
        runTest {
            viewModel.updatePlayerName("TestPlayer")
            val state = viewModel.uiState.first()

            assertEquals("TestPlayer", state.playerName)
            assertFalse(state.playerNameError)
        }

    @Test
    fun `updateNumberOfQueens should filter non-digit characters`() =
        runTest {
            viewModel.updateNumberOfQueens("8abc")
            val state = viewModel.uiState.first()

            assertEquals("8", state.numberOfQueens)
        }

    @Test
    fun `updateNumberOfQueens should allow empty string`() =
        runTest {
            viewModel.updateNumberOfQueens("8")
            viewModel.updateNumberOfQueens("")
            val state = viewModel.uiState.first()

            assertEquals("", state.numberOfQueens)
        }

    @Test
    fun `form should be valid when player name is filled and queens count is between 4-11`() =
        runTest {
            viewModel.updatePlayerName("TestPlayer")
            viewModel.updateNumberOfQueens("8")
            val state = viewModel.uiState.first()

            assertTrue(state.isFormValid)
        }

    @Test
    fun `form should be invalid when player name is empty`() =
        runTest {
            viewModel.updatePlayerName("")
            viewModel.updateNumberOfQueens("8")
            val state = viewModel.uiState.first()

            assertFalse(state.isFormValid)
        }

    @Test
    fun `form should be invalid when queens count is less than 4`() =
        runTest {
            viewModel.updatePlayerName("TestPlayer")
            viewModel.updateNumberOfQueens("3")
            val state = viewModel.uiState.first()

            assertFalse(state.isFormValid)
        }

    @Test
    fun `form should be invalid when queens count is greater than 15`() =
        runTest {
            viewModel.updatePlayerName("TestPlayer")
            viewModel.updateNumberOfQueens("16")
            val state = viewModel.uiState.first()

            assertFalse(state.isFormValid)
        }

    @Test
    fun `validateAndGetGameData should return data when form is valid`() =
        runTest {
            viewModel.updatePlayerName("TestPlayer")
            viewModel.updateNumberOfQueens("8")

            val result = viewModel.validateAndGetGameData()

            assertNotNull(result)
            assertEquals("TestPlayer", result?.first)
            assertEquals(8, result?.second)
        }

    @Test
    fun `validateAndGetGameData should return null when player name is empty`() =
        runTest {
            viewModel.updatePlayerName("")
            viewModel.updateNumberOfQueens("8")

            val result = viewModel.validateAndGetGameData()

            assertNull(result)
            val state = viewModel.uiState.first()
            assertTrue(state.playerNameError)
            assertFalse(state.numberOfQueensError)
        }

    @Test
    fun `validateAndGetGameData should return null when queens count is invalid`() =
        runTest {
            viewModel.updatePlayerName("TestPlayer")
            viewModel.updateNumberOfQueens("3")

            val result = viewModel.validateAndGetGameData()

            assertNull(result)
            val state = viewModel.uiState.first()
            assertFalse(state.playerNameError)
            assertTrue(state.numberOfQueensError)
        }

    @Test
    fun `validateAndGetGameData should trim player name whitespace`() =
        runTest {
            viewModel.updatePlayerName("  TestPlayer  ")
            viewModel.updateNumberOfQueens("8")

            val result = viewModel.validateAndGetGameData()

            assertNotNull(result)
            assertEquals("TestPlayer", result?.first)
        }

    @Test
    fun `validateAndGetGameData should show errors for both fields when both are invalid`() =
        runTest {
            viewModel.updatePlayerName("")
            viewModel.updateNumberOfQueens("17")

            val result = viewModel.validateAndGetGameData()

            assertNull(result)
            val state = viewModel.uiState.first()
            assertTrue(state.playerNameError)
            assertTrue(state.numberOfQueensError)
        }
}
