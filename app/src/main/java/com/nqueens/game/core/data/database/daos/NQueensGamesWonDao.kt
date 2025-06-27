package com.nqueens.game.core.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nqueens.game.core.data.database.entities.NQueensGamesWon

@Dao
interface NQueensGamesWonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: NQueensGamesWon)

    @Query("SELECT * FROM ${NQueensGamesWon.TABLE_NAME} ORDER BY ${NQueensGamesWon.COLUMN_DATE} DESC")
    fun getAllGames(): List<NQueensGamesWon>

    @Query(
        "SELECT * FROM ${NQueensGamesWon.TABLE_NAME} WHERE ${NQueensGamesWon.COLUMN_PLAYER_NAME} = :playerName ORDER BY ${NQueensGamesWon.COLUMN_DATE} DESC",
    )
    fun getGamesByPlayer(playerName: String): List<NQueensGamesWon>

    @Query(
        "SELECT * FROM ${NQueensGamesWon.TABLE_NAME} WHERE ${NQueensGamesWon.COLUMN_QUEENS_COUNT} = :queensCount ORDER BY ${NQueensGamesWon.COLUMN_DATE} DESC",
    )
    fun getGamesByQueensCount(queensCount: Int): List<NQueensGamesWon>
}
