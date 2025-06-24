package com.nqueens.game.features.mainmenu.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nqueens.game.R
import com.nqueens.game.ui.components.GameButton
import com.nqueens.game.ui.theme.NQueensGameTheme

sealed interface MainMenuAction {
    data object StartNewGame : MainMenuAction
    data object OpenLeaderboards : MainMenuAction
    data object Exit : MainMenuAction
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainMenuScreen(
    onAction: (MainMenuAction) -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.main_menu_title))
                },
            )
        }
    ) { paddingValues ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            GameButton(
                onClick = { onAction(MainMenuAction.StartNewGame) },
            ) {
                Text(
                    text = stringResource(id = R.string.main_menu_new_game),
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            GameButton(
                onClick = { onAction(MainMenuAction.OpenLeaderboards) },
            ) {
                Text(
                    text = stringResource(id = R.string.main_menu_leaderboards),
                    fontSize = 18.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = { onAction(MainMenuAction.Exit) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.main_menu_exit),
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainMenuScreenPreview() {
    NQueensGameTheme {
        Surface {
            MainMenuScreen {}
        }
    }
}

