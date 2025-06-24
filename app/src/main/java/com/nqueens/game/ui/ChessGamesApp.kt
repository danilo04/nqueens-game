package com.nqueens.game.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nqueens.game.ui.theme.ChessGamesTheme

@Composable
fun ChessGamesApp() {
    ChessGamesTheme {
//        val navController = rememberNavController()
        //val navigator = NavControllerNavigator(navController)

//        CompositionLocalProvider(LocalNavigator provides navigator) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            ChessGamesNavHost()
        }
    }
}