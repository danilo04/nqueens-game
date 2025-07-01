package com.nqueens.game.features.leaderboards.ui

import app.cash.turbine.test
import com.nqueens.game.BaseUnitTest
import com.nqueens.game.core.data.database.entities.NQueensGamesWon
import com.nqueens.game.core.data.repositories.NQueensGamesWonRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class LeaderboardsViewModelTest : BaseUnitTest() {
    @Mock
    private lateinit var nQueensGamesWonRepository: NQueensGamesWonRepository

    private lateinit var viewModel: LeaderboardsViewModel

    private val mockGames =
        listOf(
            NQueensGamesWon(
                id = 1L,
                playerName = "Alice",
                queensCount = 8,
                timeInSeconds = 120L,
                datePlayed = 1609459200000L,
            ),
            NQueensGamesWon(
                id = 2L,
                playerName = "Bob",
                queensCount = 4,
                timeInSeconds = 45L,
                datePlayed = 1609459260000L,
            ),
            NQueensGamesWon(
                id = 3L,
                playerName = "Charlie",
                queensCount = 8,
                timeInSeconds = 90L,
                datePlayed = 1609459320000L,
            ),
        )

    private val mockQueensCounts = listOf(4, 6, 8, 10)

    @Before
    fun setUp() {
        // Mock repository methods
        whenever(nQueensGamesWonRepository.getAllGamesOffsetFlow(any()))
            .thenReturn(flowOf(mockGames))
        whenever(nQueensGamesWonRepository.getGamesOffsetByQueensCountFlow(any(), any()))
            .thenReturn(flowOf(mockGames.filter { it.queensCount == 8 }))
        whenever(nQueensGamesWonRepository.getDistinctQueensCountsFlow())
            .thenReturn(flowOf(mockQueensCounts))

        // Mock suspend function
        runTest {
            whenever(nQueensGamesWonRepository.getDistinctQueensCounts())
                .thenReturn(mockQueensCounts)
        }

        viewModel =
            LeaderboardsViewModel(
                nQueensGamesWonRepository = nQueensGamesWonRepository,
                ioThreadDispatcher = mainCoroutineRule.dispatcher,
            )
    }

    @Test
    fun `initial state should be loading`() =
        runTest {
            viewModel.uiState.test {
                val initialState = awaitItem()
                assertThat(initialState.isLoading).isTrue()
                assertThat(initialState.games).isEmpty()
                assertThat(initialState.selectedQueensFilter).isNull()
                assertThat(initialState.availableQueensCounts).isEmpty()
                assertThat(initialState.error).isNull()
            }
        }

    @Test
    fun `when initialized, should load all games and available queens counts`() =
        runTest {
            // Skip the loading state and get the loaded state
            viewModel.uiState.test {
                skipItems(1) // Skip initial loading state

                val loadedState = awaitItem()
                assertThat(loadedState.isLoading).isFalse()
                assertThat(loadedState.games).isEqualTo(mockGames)
                assertThat(loadedState.selectedQueensFilter).isNull()
                assertThat(loadedState.availableQueensCounts).isEqualTo(mockQueensCounts)
                assertThat(loadedState.error).isNull()
            }
        }

    @Test
    fun `when applying queens filter, should filter games by queens count`() =
        runTest {
            // Wait for initial loading to complete
            viewModel.uiState.test {
                skipItems(1) // Skip initial loading state
                awaitItem() // Wait for loaded state

                // Apply filter for 8 queens
                viewModel.applyQueensFilter(8)

                val filteredState = awaitItem()
                assertThat(filteredState.isLoading).isFalse()
                assertThat(filteredState.selectedQueensFilter).isEqualTo(8)
                assertThat(filteredState.games).isEqualTo(mockGames.filter { it.queensCount == 8 })
                assertThat(filteredState.availableQueensCounts).isEqualTo(mockQueensCounts)
            }
        }

    @Test
    fun `when applying null filter, should show all games`() =
        runTest {
            // First apply a filter
            viewModel.applyQueensFilter(8)

            viewModel.uiState.test {
                // Skip to the current state
                skipItems(1)

                // Apply null filter to show all games
                viewModel.applyQueensFilter(null)

                val allGamesState = awaitItem()
                assertThat(allGamesState.isLoading).isFalse()
                assertThat(allGamesState.selectedQueensFilter).isNull()
                assertThat(allGamesState.games).isEqualTo(mockGames)
                assertThat(allGamesState.availableQueensCounts).isEqualTo(mockQueensCounts)
            }
        }

    @Test
    fun `when changing filter multiple times, should update state correctly`() =
        runTest {
            viewModel.uiState.test {
                skipItems(1) // Skip initial loading state
                awaitItem() // Initial loaded state

                // Apply filter for 4 queens
                viewModel.applyQueensFilter(4)
                val fourQueensState = awaitItem()
                assertThat(fourQueensState.selectedQueensFilter).isEqualTo(4)

                // Apply filter for 8 queens
                viewModel.applyQueensFilter(8)
                val eightQueensState = awaitItem()
                assertThat(eightQueensState.selectedQueensFilter).isEqualTo(8)

                // Remove filter
                viewModel.applyQueensFilter(null)
                val noFilterState = awaitItem()
                assertThat(noFilterState.selectedQueensFilter).isNull()
            }
        }

    @Test
    fun `loadMore function should be available in ui state`() =
        runTest {
            viewModel.uiState.test {
                skipItems(1) // Skip initial loading state

                val loadedState = awaitItem()
                // assertThat(loadedState.loadMore).isNotNull()

                // Should be able to call loadMore without exception
                loadedState.loadMore()
            }
        }

    @Test
    fun `when repository returns empty list, should handle gracefully`() =
        runTest {
            // Mock empty results
            whenever(nQueensGamesWonRepository.getAllGamesOffsetFlow(any()))
                .thenReturn(flowOf(emptyList()))
            whenever(nQueensGamesWonRepository.getDistinctQueensCountsFlow())
                .thenReturn(flowOf(emptyList()))

            whenever(nQueensGamesWonRepository.getDistinctQueensCounts())
                .thenReturn(emptyList())

            val emptyViewModel =
                LeaderboardsViewModel(
                    nQueensGamesWonRepository = nQueensGamesWonRepository,
                    ioThreadDispatcher = mainCoroutineRule.dispatcher,
                )

            emptyViewModel.uiState.test {
                skipItems(1) // Skip initial loading state

                val emptyState = awaitItem()
                assertThat(emptyState.isLoading).isFalse()
                assertThat(emptyState.games).isEmpty()
                assertThat(emptyState.availableQueensCounts).isEmpty()
                assertThat(emptyState.selectedQueensFilter).isNull()
            }
        }

    @Test
    fun `when filter is applied for queens count not in available list, should still work`() =
        runTest {
            viewModel.uiState.test {
                skipItems(1) // Skip initial loading state
                awaitItem() // Initial loaded state

                // Apply filter for queens count not in the available list
                viewModel.applyQueensFilter(12)

                val filteredState = awaitItem()
                assertThat(filteredState.selectedQueensFilter).isEqualTo(12)
                // The repository should handle this case and return appropriate results
            }
        }

    @Test
    fun `ui state should maintain available queens counts when filter changes`() =
        runTest {
            viewModel.uiState.test {
                skipItems(1) // Skip initial loading state
                val initialState = awaitItem()
                val initialQueensCounts = initialState.availableQueensCounts

                // Apply various filters
                viewModel.applyQueensFilter(8)
                val filteredState1 = awaitItem()
                assertThat(filteredState1.availableQueensCounts).isEqualTo(initialQueensCounts)

                viewModel.applyQueensFilter(4)
                val filteredState2 = awaitItem()
                assertThat(filteredState2.availableQueensCounts).isEqualTo(initialQueensCounts)

                viewModel.applyQueensFilter(null)
                val unfilteredState = awaitItem()
                assertThat(unfilteredState.availableQueensCounts).isEqualTo(initialQueensCounts)
            }
        }

    @Test
    fun `board size info should be constructed correctly`() =
        runTest {
            viewModel.uiState.test {
                skipItems(1) // Skip initial loading state
                val loadedState = awaitItem()

                // Verify BoardSizeInfo is constructed correctly
                assertThat(loadedState.availableQueensCounts).isEqualTo(mockQueensCounts)
                assertThat(loadedState.selectedQueensFilter).isNull()

                // Apply filter and verify BoardSizeInfo updates
                viewModel.applyQueensFilter(8)
                val filteredState = awaitItem()
                assertThat(filteredState.availableQueensCounts).isEqualTo(mockQueensCounts)
                assertThat(filteredState.selectedQueensFilter).isEqualTo(8)
            }
        }
}
