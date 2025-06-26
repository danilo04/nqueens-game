package com.nqueens.game

enum class Screens(
    val route: String,
) {
    MENU("menu"),
    START_N_QUEENS_GAME("start_n_queens_game"),
    N_QUEENS_GAME("n_queens_game/{playerName}/{queensCount}"),
    LEADERBOARDS("leaderboards"),
}

object NQueensGameArgs {
    const val PLAYER_NAME = "playerName"
    const val QUEENS_COUNT = "queensCount"

    fun createRoute(
        playerName: String,
        queensCount: Int,
    ): String = "n_queens_game/$playerName/$queensCount"
}
