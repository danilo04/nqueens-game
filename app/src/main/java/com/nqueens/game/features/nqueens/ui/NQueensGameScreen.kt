package com.nqueens.game.features.nqueens.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nqueens.game.R
import com.nqueens.game.core.board.ui.components.BoardView
import com.nqueens.game.core.design.theme.ChessGamesTheme
import com.nqueens.game.features.nqueens.domain.NQueensBoardGame
import com.nqueens.game.features.nqueens.ui.state.NQueensBoardUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NQueensGameScreen(
    playerName: String,
    queensCount: Int,
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel =
        hiltViewModel<NQueensGameViewModel, NQueensGameViewModel.NQueensGameViewModelFactory>(
            key = "NQueensGame_${playerName}_$queensCount",
            creationCallback = { factory ->
                factory.create(playerName, queensCount)
            },
        )
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.main_menu_title))
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
        NQueensGameScreenContent(
            uiState = uiState.value,
            onPauseToggle = {
                if (uiState.value.isGamePaused) {
                    viewModel.resumeGame()
                } else {
                    viewModel.pauseGame()
                }
            },
            onResetGame = viewModel::resetGame,
            modifier = modifier.padding(paddingValues),
        )
    }
}

@Composable
fun NQueensGameScreenContent(
    uiState: NQueensGameUiState,
    onPauseToggle: () -> Unit,
    onResetGame: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        if (isLandscape) {
            // Landscape layout: Board on left, info on right
            Row(
                modifier = Modifier.fillMaxSize(),
            ) {
                // Board section (left side) - takes available height
                BoardSection(
                    boardState = uiState.boardState,
                    isGamePaused = uiState.isGamePaused,
                    modifier =
                        Modifier
                            .fillMaxHeight()
                            .aspectRatio(1f)
                            .padding(start = 5.dp),
                )

                // Info section (right side)
                Column(
                    modifier =
                        Modifier
                            .fillMaxHeight()
                            .weight(1f)
                            .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    // Game header info
                    GameHeaderSection(
                        playerName = uiState.playerName,
                        timeElapsed = uiState.timeElapsed,
                        queensPlaced = uiState.queensPlaced,
                        totalQueens = uiState.totalQueens,
                        isGamePaused = uiState.isGamePaused,
                        isGameCompleted = uiState.isGameCompleted,
                        onPauseToggle = onPauseToggle,
                        onResetGame = onResetGame,
                        isLandScape = isLandscape,
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Game status section
                    GameStatusSection(
                        isGameCompleted = uiState.isGameCompleted,
                        totalQueens = uiState.totalQueens,
                    )
                }
            }
        } else {
            // Portrait layout: Original vertical layout
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                // Top section with player info and timer (chess.com style)
                GameHeaderSection(
                    playerName = uiState.playerName,
                    timeElapsed = uiState.timeElapsed,
                    queensPlaced = uiState.queensPlaced,
                    totalQueens = uiState.totalQueens,
                    isGamePaused = uiState.isGamePaused,
                    isGameCompleted = uiState.isGameCompleted,
                    onPauseToggle = onPauseToggle,
                    onResetGame = onResetGame,
                    isLandScape = isLandscape,
                    modifier = Modifier.padding(16.dp),
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Board section
                BoardSection(
                    boardState = uiState.boardState,
                    isGamePaused = uiState.isGamePaused,
                    modifier = Modifier.weight(1f),
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Game status section
                GameStatusSection(
                    isGameCompleted = uiState.isGameCompleted,
                    totalQueens = uiState.totalQueens,
                )
            }
        }
    }
}

@Composable
fun GameHeaderSection(
    playerName: String,
    timeElapsed: Long,
    queensPlaced: Int,
    totalQueens: Int,
    isGamePaused: Boolean,
    isGameCompleted: Boolean,
    isLandScape: Boolean,
    onPauseToggle: () -> Unit,
    onResetGame: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors =
            CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceContainer,
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        val isLandScapeModifier =
            if (isLandScape) {
                modifier.padding(horizontal = 16.dp)
            } else {
                modifier
            }
        Column(
            modifier = isLandScapeModifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Player info section (left)
                PlayerInfoSection(
                    playerName = playerName,
                    queensPlaced = queensPlaced,
                    totalQueens = totalQueens,
                )

                // Game controls (center)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = onPauseToggle,
                        enabled = !isGameCompleted,
                    ) {
                        Icon(
                            imageVector = if (isGamePaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                            contentDescription = if (isGamePaused) "Resume" else "Pause",
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(onClick = onResetGame) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Reset Game",
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                }

                if (!isLandScape) {
                    // Timer section (right)
                    TimerSection(
                        timeElapsed = timeElapsed,
                        isGamePaused = isGamePaused,
                        isGameCompleted = isGameCompleted,
                    )
                }
            }

            if (isLandScape) {
                // Timer section (centered in landscape)
                TimerSection(
                    timeElapsed = timeElapsed,
                    isGamePaused = isGamePaused,
                    isGameCompleted = isGameCompleted,
                    modifier = Modifier.padding(bottom = 16.dp),
                )
            }
        }
    }
}

@Composable
fun PlayerInfoSection(
    playerName: String,
    queensPlaced: Int,
    totalQueens: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Player avatar placeholder
            Box(
                modifier =
                    Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = playerName.take(1).uppercase(),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column {
                Text(
                    text = playerName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Text(
                    text = "$queensPlaced/$totalQueens Queens",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
fun TimerSection(
    timeElapsed: Long,
    isGamePaused: Boolean,
    isGameCompleted: Boolean,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors =
            CardDefaults.cardColors(
                containerColor =
                    when {
                        isGameCompleted -> MaterialTheme.colorScheme.primaryContainer
                        isGamePaused -> MaterialTheme.colorScheme.errorContainer
                        else -> MaterialTheme.colorScheme.surface
                    },
            ),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Time",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Text(
                text = formatTime(timeElapsed),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color =
                    when {
                        isGameCompleted -> MaterialTheme.colorScheme.onPrimaryContainer
                        isGamePaused -> MaterialTheme.colorScheme.onErrorContainer
                        else -> MaterialTheme.colorScheme.onSurface
                    },
            )
        }
    }
}

@Composable
fun BoardSection(
    boardState: NQueensBoardUiState,
    isGamePaused: Boolean,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        BoardView(
            boardState = boardState,
            modifier = Modifier,
        )

        // Pause overlay
        if (isGamePaused) {
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(
                            Color.Black.copy(alpha = 0.5f),
                            RoundedCornerShape(12.dp),
                        ),
                contentAlignment = Alignment.Center,
            ) {
                Card(
                    colors =
                        CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surface,
                        ),
                ) {
                    Text(
                        text = "PAUSED",
                        modifier = Modifier.padding(24.dp),
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
            }
        }
    }
}

@Composable
fun GameStatusSection(
    isGameCompleted: Boolean,
    totalQueens: Int,
    modifier: Modifier = Modifier,
) {
    if (isGameCompleted) {
        Card(
            modifier = modifier.fillMaxWidth(),
            colors =
                CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                ),
        ) {
            Text(
                text = "🎉 Congratulations! You've placed all $totalQueens queens!",
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            )
        }
    }
}

fun formatTime(seconds: Long): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "${minutes.toString().padStart(2, '0')}:${remainingSeconds.toString().padStart(2, '0')}"
}

@Preview(showBackground = true)
@Composable
fun NQueensGameScreenPreview() {
    val mockGame = NQueensBoardGame(8)
    val mockGameState = NQueensBoardUiState(mockGame)

    val mockUiState =
        NQueensGameUiState(
            playerName = "Alice",
            boardState = mockGameState,
            timeElapsed = 125L, // 2:05
            queensPlaced = 3,
            totalQueens = 8,
        )

    ChessGamesTheme {
        NQueensGameScreenContent(
            uiState = mockUiState,
            onPauseToggle = { },
            onResetGame = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NQueensGameScreenPausedPreview() {
    val mockGame = NQueensBoardGame(8)
    val mockGameState = NQueensBoardUiState(mockGame)

    val mockUiState =
        NQueensGameUiState(
            playerName = "Bob",
            boardState = mockGameState,
            timeElapsed = 245L, // 4:05
            isGamePaused = true,
            queensPlaced = 5,
            totalQueens = 8,
        )

    ChessGamesTheme {
        NQueensGameScreenContent(
            uiState = mockUiState,
            onPauseToggle = { },
            onResetGame = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NQueensGameScreenCompletedPreview() {
    val mockGame = NQueensBoardGame(8)
    val mockGameState = NQueensBoardUiState(mockGame)

    val mockUiState =
        NQueensGameUiState(
            playerName = "Charlie",
            boardState = mockGameState,
            timeElapsed = 180L, // 3:00
            isGameCompleted = true,
            queensPlaced = 8,
            totalQueens = 8,
        )

    ChessGamesTheme {
        NQueensGameScreenContent(
            uiState = mockUiState,
            onPauseToggle = { },
            onResetGame = { },
        )
    }
}

@Preview(
    showBackground = true,
    widthDp = 800,
    heightDp = 480,
    name = "Landscape Preview",
)
@Composable
fun NQueensGameScreenLandscapePreview() {
    val mockGame = NQueensBoardGame(8)
    val mockGameState = NQueensBoardUiState(mockGame)

    val mockUiState =
        NQueensGameUiState(
            playerName = "Alice",
            boardState = mockGameState,
            timeElapsed = 125L, // 2:05
            queensPlaced = 3,
            totalQueens = 8,
        )

    ChessGamesTheme {
        NQueensGameScreenContent(
            uiState = mockUiState,
            onPauseToggle = { },
            onResetGame = { },
        )
    }
}
