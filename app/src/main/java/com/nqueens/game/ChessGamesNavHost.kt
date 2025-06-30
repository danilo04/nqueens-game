package com.nqueens.game

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nqueens.game.features.leaderboards.ui.LeaderboardScreen
import com.nqueens.game.features.mainmenu.ui.MainMenuAction
import com.nqueens.game.features.mainmenu.ui.MainMenuScreen
import com.nqueens.game.features.nqueens.ui.NQueensGameScreen
import com.nqueens.game.features.nqueens.ui.StartNQueensGameScreen

@Composable
fun ChessGamesNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screens.MENU.route,
    onExit: () -> Unit = {},
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        composable(Screens.MENU.route) {
            MainMenuScreen { action ->
                when (action) {
                    MainMenuAction.StartNewGame -> {
                        navController.navigate(Screens.START_N_QUEENS_GAME.route)
                    }
                    MainMenuAction.OpenLeaderboards -> {
                        navController.navigate(Screens.LEADERBOARDS.route)
                    }
                    MainMenuAction.Exit -> {
                        onExit()
                    }
                }
            }
        }

        composable(Screens.START_N_QUEENS_GAME.route) {
            StartNQueensGameScreen(
                onNavigateBack = { navController.navigate(Screens.MENU.route) },
                onStartGame = { playerName, queensCount ->
                    navController.navigate(NQueensGameArgs.createRoute(playerName, queensCount))
                },
            )
        }
        composable(
            route = Screens.N_QUEENS_GAME.route,
            arguments =
                listOf(
                    navArgument(NQueensGameArgs.PLAYER_NAME) { type = NavType.StringType },
                    navArgument(NQueensGameArgs.QUEENS_COUNT) { type = NavType.IntType },
                ),
        ) { backStackEntry ->
            val playerName = backStackEntry.arguments?.getString(NQueensGameArgs.PLAYER_NAME)
            val queensCount = backStackEntry.arguments?.getInt(NQueensGameArgs.QUEENS_COUNT)
            NQueensGameScreen(
                playerName = playerName ?: "Player",
                queensCount = queensCount ?: 8,
                onNavigateBack = { navController.popBackStack(Screens.MENU.route, false) },
                onNavigateToLeaderboard = {
                    navController.navigate(Screens.LEADERBOARDS.route) {
                        popUpTo(Screens.MENU.route) {
                            inclusive = false
                        }
                    }
                },
            )
        }

        composable(Screens.LEADERBOARDS.route) {
            LeaderboardScreen(
                onNavigateBack = { navController.popBackStack() },
            )
        }
    }
}
