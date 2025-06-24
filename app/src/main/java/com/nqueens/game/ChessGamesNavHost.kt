package com.nqueens.game

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nqueens.game.features.mainmenu.ui.MainMenuScreen
import com.nqueens.game.features.nqueens.ui.NQueensGameScreen
import com.nqueens.game.features.nqueens.ui.StartNQueensGameScreen

@Composable
fun ChessGamesNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screens.MENU.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Screens.MENU.route) {
            MainMenuScreen {
                navController.navigate(Screens.START_N_QUEENS_GAME.route)
            }
        }
        composable(Screens.START_N_QUEENS_GAME.route) {
            StartNQueensGameScreen(
                onNavigateBack = { navController.navigate(Screens.MENU.route) },
                onStartGame = { playerName, queensCount ->
                    navController.navigate(Screens.N_QUEENS_GAME.route)
                },
            )
        }
        composable(Screens.N_QUEENS_GAME.route) {
            NQueensGameScreen {
                navController.popBackStack()
            }
        }
        composable(Screens.LEADERBOARDS.route) {
        }
    }
}
