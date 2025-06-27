package com.nqueens.game.features.nqueens.ui

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.AlertDialog
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.nqueens.game.R
import com.nqueens.game.core.board.ui.components.BoardView
import com.nqueens.game.core.design.components.CGButton
import com.nqueens.game.core.design.theme.ChessGamesTheme
import com.nqueens.game.core.icons.ChessGamesIcons
import com.nqueens.game.core.icons.pieces.WhiteQueen
import com.nqueens.game.core.utils.ui.rememberAppState
import com.nqueens.game.features.nqueens.domain.NQueensBoardGame
import com.nqueens.game.features.nqueens.ui.state.NQueensBoardUiState
import kotlinx.coroutines.delay
import nl.dionsegijn.konfetti.compose.KonfettiView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NQueensGameScreen(
    playerName: String,
    queensCount: Int,
    onNavigateBack: () -> Unit,
    onNavigateToLeaderboard: () -> Unit,
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

    val appInForeground = rememberAppState()
    if (!appInForeground.value) {
        viewModel.pauseGame()
    }

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
            onNavigateToLeaderboard = onNavigateToLeaderboard,
            modifier = modifier.padding(paddingValues),
        )
    }
}

@Composable
fun NQueensGameScreenContent(
    uiState: NQueensGameUiState,
    onPauseToggle: () -> Unit,
    onResetGame: () -> Unit,
    onNavigateToLeaderboard: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Box(modifier = modifier.fillMaxSize()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            if (isLandscape) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    BoardSection(
                        boardState = uiState.boardState,
                        isGamePaused = uiState.isGamePaused,
                        queensPlaced = uiState.queensPlaced,
                        totalQueens = uiState.totalQueens,
                        isLandScape = true,
                        modifier =
                            Modifier
                                .fillMaxHeight()
                                .aspectRatio(1f)
                                .padding(start = 5.dp),
                    )

                    Column(
                        modifier =
                            Modifier
                                .fillMaxHeight()
                                .weight(1f)
                                .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        GameHeaderSection(
                            playerName = uiState.playerName,
                            timeElapsed = uiState.timeElapsed,
                            isGamePaused = uiState.isGamePaused,
                            isGameCompleted = uiState.isGameCompleted,
                            onResetGame = onResetGame,
                            onPauseToggle = onPauseToggle,
                            isLandScape = true,
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        GameStatusSection(
                            isGameCompleted = uiState.isGameCompleted,
                            totalQueens = uiState.totalQueens,
                            onNewGame = onResetGame,
                            onLeaderboard = onNavigateToLeaderboard,
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            BottomSection(
                                queensPlaced = uiState.queensPlaced,
                                totalQueens = uiState.totalQueens,
                            )
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    GameHeaderSection(
                        playerName = uiState.playerName,
                        timeElapsed = uiState.timeElapsed,
                        isGamePaused = uiState.isGamePaused,
                        isGameCompleted = uiState.isGameCompleted,
                        onResetGame = onResetGame,
                        onPauseToggle = onPauseToggle,
                        isLandScape = false,
                        modifier = Modifier.padding(16.dp),
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    BoardSection(
                        boardState = uiState.boardState,
                        isGamePaused = uiState.isGamePaused,
                        queensPlaced = uiState.queensPlaced,
                        totalQueens = uiState.totalQueens,
                        isLandScape = false,
                        modifier = Modifier.weight(1f),
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    GameStatusSection(
                        isGameCompleted = uiState.isGameCompleted,
                        totalQueens = uiState.totalQueens,
                        onNewGame = onResetGame,
                        onLeaderboard = onNavigateToLeaderboard,
                    )
                }
            }
        }

        if (uiState.isGameCompleted) {
            ConfettiAnimation()
        }
    }
}

@Composable
private fun GameHeaderSection(
    playerName: String,
    timeElapsed: Long,
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
                        .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                val nameMaxWidth =
                    if (isLandScape) {
                        360
                    } else {
                        120
                    }
                PlayerInfoSection(
                    playerName = playerName,
                    nameMaxWidth = nameMaxWidth,
                    modifier = Modifier.weight(1f, fill = false),
                )

                // Game controls (center)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (isGamePaused) {
                        IconButton(
                            onClick = onPauseToggle,
                            enabled = !isGameCompleted,
                        ) {
                            Icon(
                                imageVector = if (isGamePaused) Icons.Default.PlayArrow else Icons.Default.Pause,
                                contentDescription =
                                    if (isGamePaused) {
                                        stringResource(
                                            R.string.resume_game,
                                        )
                                    } else {
                                        stringResource(R.string.pause_game)
                                    },
                                tint = MaterialTheme.colorScheme.primary,
                            )
                        }
                    }

                    IconButton(onClick = onResetGame) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = stringResource(R.string.reset_game),
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
private fun PlayerInfoSection(
    playerName: String,
    modifier: Modifier = Modifier,
    nameMaxWidth: Int = 120,
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
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.widthIn(max = nameMaxWidth.dp),
                )
            }
        }
    }
}

@Composable
private fun BottomSection(
    queensPlaced: Int,
    totalQueens: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Image(
            imageVector = ChessGamesIcons.Pieces.WhiteQueen,
            contentDescription = null,
            modifier =
                Modifier
                    .size(72.dp)
                    .padding(2.dp),
        )
        val queensLeft = totalQueens - queensPlaced
        Text(
            text =
                pluralStringResource(
                    id = R.plurals.queens_left,
                    count = queensLeft,
                    queensLeft,
                ),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun TimerSection(
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
                text = stringResource(R.string.time_label),
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
    isLandScape: Boolean,
    queensPlaced: Int,
    totalQueens: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier.weight(1f),
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
                            text = stringResource(R.string.game_paused),
                            modifier = Modifier.padding(24.dp),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
        }

        if (!isLandScape) {
            BottomSection(
                queensPlaced = queensPlaced,
                totalQueens = totalQueens,
                modifier =
                    Modifier
                        .padding(8.dp),
            )
        }
    }
}

@Composable
private fun GameCompletionDialog(
    totalQueens: Int,
    onNewGame: () -> Unit,
    onLeaderboard: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false),
        confirmButton = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth(),
            ) {
                CGButton(
                    onClick = onLeaderboard,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(stringResource(R.string.view_leaderboards))
                }
                CGButton(
                    onClick = onNewGame,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(stringResource(R.string.new_game))
                }
            }
        },
        title = {
            Text(
                text = stringResource(R.string.congratulations_title),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = stringResource(R.string.puzzle_completed_message, totalQueens),
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                )
            }
        },
    )
}

@Composable
private fun ConfettiAnimation() {
    val party =
        Party(
            speed = 0f,
            maxSpeed = 30f,
            damping = 0.9f,
            spread = 45,
            colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
            emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
            position = Position.Relative(0.5, 0.3),
        )

    KonfettiView(
        modifier = Modifier.fillMaxSize(),
        parties = listOf(party),
    )
}

@Composable
fun GameStatusSection(
    isGameCompleted: Boolean,
    totalQueens: Int,
    onNewGame: () -> Unit,
    onLeaderboard: () -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(isGameCompleted) {
        if (isGameCompleted) {
            delay(800) // Wait 800 milliseconds before showing the dialog
            showDialog = true
        }
    }

    AnimatedVisibility(
        visible = showDialog && isGameCompleted,
    ) {
        GameCompletionDialog(
            totalQueens = totalQueens,
            onNewGame = {
                showDialog = false
                onNewGame()
            },
            onLeaderboard = {
                showDialog = false
                onLeaderboard()
            },
            onDismiss = {
                showDialog = false
            },
        )
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
            onNavigateToLeaderboard = { },
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
            onNavigateToLeaderboard = {},
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
            onNavigateToLeaderboard = { },
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
            onNavigateToLeaderboard = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun QueensLeftSectionPreview() {
    ChessGamesTheme {
        BottomSection(queensPlaced = 5, totalQueens = 8)
    }
}
