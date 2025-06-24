package com.nqueens.game.features.nqueens.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.nqueens.game.R
import com.nqueens.game.core.design.components.CGButton
import com.nqueens.game.core.design.components.CGQuantityTextField
import com.nqueens.game.core.design.components.CGTextField
import com.nqueens.game.core.design.theme.ChessGamesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartNQueensGameScreen(
    onNavigateBack: () -> Unit,
    onStartGame: (String, Int) -> Unit,
    viewModel: StartNQueensGameViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    StartNQueensGameScreenContent(
        uiState = uiState,
        onPlayerNameChange = viewModel::updatePlayerName,
        onNumberOfQueensChange = viewModel::updateNumberOfQueens,
        onNavigateBack = onNavigateBack,
        onStartGameClick = {
            viewModel.validateAndGetGameData()?.let { (playerName, queensCount) ->
                onStartGame(playerName, queensCount)
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
private fun StartGameScreenPreview() {
    ChessGamesTheme {
        StartNQueensGameScreenContent(
            uiState =
                StartGameUiState(
                    playerName = "",
                    numberOfQueens = "",
                    playerNameError = true,
                    numberOfQueensError = true,
                    isFormValid = false,
                ),
            onPlayerNameChange = {},
            onNumberOfQueensChange = {},
            onNavigateBack = {},
            onStartGameClick = {},
        )
    }
}

// Content function for preview and testing
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StartNQueensGameScreenContent(
    uiState: StartGameUiState,
    onPlayerNameChange: (String) -> Unit,
    onNumberOfQueensChange: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onStartGameClick: () -> Unit,
) {
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
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.start_a_new_game),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Player name input
            CGTextField(
                value = uiState.playerName,
                label = stringResource(R.string.player_name),
                onValueChange = onPlayerNameChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = stringResource(R.string.player_name))
                },
                isError = uiState.playerNameError,
            )

            if (uiState.playerNameError) {
                Text(
                    text = stringResource(R.string.player_name_required),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, top = 4.dp),
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Number of queens input
            CGQuantityTextField(
                value = uiState.numberOfQueens,
                onValueChange = onNumberOfQueensChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = stringResource(R.string.number_of_queens))
                },
                isError = uiState.numberOfQueensError,
                minValue = StartNQueensGameViewModel.MIN_QUEENS,
                maxValue = StartNQueensGameViewModel.MAX_QUEENS,
            )

            if (uiState.numberOfQueensError) {
                Text(
                    text =
                        stringResource(
                            R.string.queens_range_error,
                            StartNQueensGameViewModel.MIN_QUEENS,
                            StartNQueensGameViewModel.MAX_QUEENS,
                        ),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.error,
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, top = 4.dp),
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            // Start Game button
            CGButton(
                onClick = onStartGameClick,
                enabled = uiState.isFormValid,
            ) {
                Text(
                    text = stringResource(R.string.start_game),
                    fontSize = 18.sp,
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
