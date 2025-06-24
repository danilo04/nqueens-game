package com.nqueens.game

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nqueens.game.features.mainmenu.ui.MainMenuScreen

@Composable
fun ChessGamesNavHost(
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
            MainMenuScreen {  }
        }
        composable(Screens.BOARD_GAME.route) {

        }
        composable(Screens.LEADERBOARDS.route) {

        }
    }
}