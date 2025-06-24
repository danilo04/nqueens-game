package com.nqueens.game.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nqueens.game.features.mainmenu.ui.MainMenuAction
import com.nqueens.game.features.mainmenu.ui.MainMenuScreen
import com.nqueens.game.features.nqueensgame.ui.NQueensBoardGameScreen
import com.nqueens.game.features.nqueensgame.ui.StartGameScreen
import com.nqueens.game.features.nqueensgame.ui.StartGameState

@Composable
fun NQueensGameNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screens.MENU.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screens.MENU.route) {
            MainMenuScreen { action ->
                when (action) {
                    MainMenuAction.Exit -> {

                    }
                    MainMenuAction.OpenLeaderboards -> {

                    }
                    MainMenuAction.StartNewGame -> {
                        navController.navigate(Screens.START_GAME.route)
                    }
                }
            }
        }
        composable(Screens.START_GAME.route) {
            StartGameScreen(
                state = StartGameState(),
                onPlayerNameChange = {},
                onNumberOfQueensChange = {},
                onNavigateBack = {},
                onStartGame = {_, _ ->
                    navController.navigate(Screens.N_QUEENS_GAME.route)
                }
            )
        }
        composable(Screens.N_QUEENS_GAME.route) {
            NQueensBoardGameScreen()
        }
        composable(Screens.LEADERBOARDS.route) {

        }
    }
}