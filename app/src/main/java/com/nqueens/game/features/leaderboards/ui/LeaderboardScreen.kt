package com.nqueens.game.features.leaderboards.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nqueens.game.R
import com.nqueens.game.core.data.database.entities.NQueensGamesWon
import com.nqueens.game.core.design.theme.ChessGamesTheme
import com.nqueens.game.core.icons.ChessGamesIcons
import com.nqueens.game.core.icons.pieces.BlackQueen
import com.nqueens.game.core.icons.pieces.WhiteQueen
import com.nqueens.game.core.utils.ui.OnBottomReached
import com.nqueens.game.features.nqueens.ui.formatTime
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaderboardScreen(
    onNavigateBack: () -> Unit,
    viewModel: LeaderboardsViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.leaderboards_title),
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back),
                        )
                    }
                },
            )
        },
    ) { paddingValues ->
        LeaderboardsContent(
            uiState = uiState,
            onFilterChange = viewModel::applyQueensFilter,
            modifier = Modifier.padding(paddingValues),
        )
    }
}

@Composable
private fun LeaderboardsContent(
    uiState: LeaderboardsUiState,
    onFilterChange: (Int?) -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        uiState.isLoading -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        uiState.error != null -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = stringResource(R.string.leaderboards_error_title),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.error,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = uiState.error,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }

        uiState.games.isEmpty() -> {
            Box(
                modifier = modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Icon(
                        imageVector = ChessGamesIcons.Pieces.BlackQueen,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.leaderboards_no_games_title),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = stringResource(R.string.leaderboards_no_games_message),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }

        else -> {
            Column(
                modifier = modifier.fillMaxSize(),
            ) {
                // Filter section
                if (uiState.availableQueensCounts.isNotEmpty()) {
                    FilterSection(
                        availableQueensCounts = uiState.availableQueensCounts,
                        selectedFilter = uiState.selectedQueensFilter,
                        onFilterChange = onFilterChange,
                        modifier = Modifier.padding(16.dp),
                    )
                }

                // Games list
                val lazyListState = rememberLazyListState()
                LazyColumn(
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(uiState.games) { game ->
                        LeaderboardGameCard(
                            game = game,
                        )
                    }
                }

                lazyListState.OnBottomReached(10) {
                    uiState.loadMore.invoke()
                }
            }
        }
    }
}

@Composable
private fun FilterSection(
    availableQueensCounts: List<Int>,
    selectedFilter: Int?,
    onFilterChange: (Int?) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.leaderboards_filter_by_board_size),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp),
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            item {
                FilterChip(
                    onClick = { onFilterChange(null) },
                    label = { Text(stringResource(R.string.leaderboards_filter_all)) },
                    selected = selectedFilter == null,
                    colors =
                        FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                )
            }

            items(availableQueensCounts) { queensCount ->
                FilterChip(
                    onClick = { onFilterChange(queensCount) },
                    label = { Text(stringResource(R.string.leaderboards_queens_size, queensCount, queensCount)) },
                    selected = selectedFilter == queensCount,
                    colors =
                        FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                        ),
                )
            }
        }
    }
}

@Composable
private fun LeaderboardGameCard(
    game: NQueensGamesWon,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
            ),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Rank indicator
            Box(
                modifier =
                    Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    imageVector = ChessGamesIcons.Pieces.WhiteQueen,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp),
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Game details
            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = game.playerName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // Board size
                    Text(
                        text = "${game.queensCount}×${game.queensCount}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium,
                    )

                    // Time
                    Text(
                        text = formatTime(game.timeInSeconds),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }

                // Date
                Text(
                    text = formatDate(game.datePlayed),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

private fun formatDate(timestamp: Long): String {
    val dateFormat = SimpleDateFormat("MMM dd, yyyy 'at' HH:mm", Locale.getDefault())
    return dateFormat.format(Date(timestamp))
}

@Preview(showBackground = true)
@Composable
private fun LeaderboardsScreenPreview() {
    ChessGamesTheme {
        Surface {
            val sampleGames =
                listOf(
                    NQueensGamesWon(
                        id = 1,
                        playerName = "Alice",
                        queensCount = 8,
                        timeInSeconds = 125,
                        datePlayed = System.currentTimeMillis(),
                    ),
                    NQueensGamesWon(
                        id = 2,
                        playerName = "Bob",
                        queensCount = 8,
                        timeInSeconds = 156,
                        datePlayed = System.currentTimeMillis() - 3600000,
                    ),
                    NQueensGamesWon(
                        id = 3,
                        playerName = "Charlie",
                        queensCount = 6,
                        timeInSeconds = 89,
                        datePlayed = System.currentTimeMillis() - 7200000,
                    ),
                )

            LeaderboardsContent(
                uiState =
                    LeaderboardsUiState(
                        isLoading = false,
                        games = sampleGames,
                        availableQueensCounts = listOf(6, 8),
                    ),
                onFilterChange = {},
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyLeaderboardsPreview() {
    ChessGamesTheme {
        Surface {
            LeaderboardsContent(
                uiState =
                    LeaderboardsUiState(
                        isLoading = false,
                        games = emptyList(),
                    ),
                onFilterChange = {},
            )
        }
    }
}
