package com.nqueens.game.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.nqueens.game.ui.theme.NQueensGameTheme

@Composable
fun NQueensGameApp() {
    NQueensGameTheme {
        val navController = rememberNavController()
        //val navigator = NavControllerNavigator(navController)

//        CompositionLocalProvider(LocalNavigator provides navigator) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            NQueensGameNavHost()
        }
    }
}