package com.nqueens.game.features.nqueensgame.ui

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nqueens.game.R
import com.nqueens.game.ui.components.GameButton
import com.nqueens.game.ui.components.GameTextField
import com.nqueens.game.ui.components.QuantityTextField
import com.nqueens.game.ui.theme.NQueensGameTheme

data class StartGameState(
    val playerName: String = "",
    val numberOfQueens: String = "",
    val showQueensError: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartGameScreen(
    state: StartGameState = StartGameState(),
    onPlayerNameChange: (String) -> Unit,
    onNumberOfQueensChange: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onStartGame: (String, Int) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(R.string.new_game))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.navigate_back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = stringResource(R.string.start_a_new_game),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Player name input
            GameTextField(
                value = state.playerName,
                onValueChange = onPlayerNameChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = stringResource(R.string.player_name))
                },
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Number of queens input
            QuantityTextField(
                value = state.numberOfQueens,
                onValueChange = onNumberOfQueensChange,
                modifier = Modifier.fillMaxWidth(),
                placeholder = {
                    Text(text = stringResource(R.string.number_of_queens))
                },
                isError = state.showQueensError,
                minValue = 4  // Minimum queens according to your error message
            )
            
            if (state.showQueensError) {
                Text(
                    text = stringResource(R.string.queens_minimum_error),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, top = 4.dp)
                )
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Start Game button
            GameButton(
                onClick = {
                    val queensNumber = state.numberOfQueens.toIntOrNull() ?: 0
                    onStartGame(state.playerName, queensNumber)
                },
            ) {
                Text(
                    text = stringResource(R.string.start_game),
                    fontSize = 18.sp
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StartGameScreenPreview() {
    val state = StartGameState(
        playerName = "",
        numberOfQueens = "",
        showQueensError = true
    )
    
    NQueensGameTheme {
        StartGameScreen(
            state = state,
            onPlayerNameChange = {},
            onNumberOfQueensChange = {},
            onNavigateBack = {},
            onStartGame = { _, _ -> }
        )
    }
}

