package com.nqueens.game.core.data.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nqueens.game.core.data.database.entities.NQueensGamesWon
import kotlinx.coroutines.flow.Flow

@Dao
interface NQueensGamesWonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGame(game: NQueensGamesWon)

    @Query("SELECT * FROM ${NQueensGamesWon.TABLE_NAME} ORDER BY ${NQueensGamesWon.COLUMN_DATE} DESC")
    fun getAllGames(): List<NQueensGamesWon>

    @Query(
        "SELECT * FROM ${NQueensGamesWon.TABLE_NAME} ORDER BY ${NQueensGamesWon.COLUMN_TIME_IN_SECONDS} ASC, ${
            NQueensGamesWon.COLUMN_DATE} DESC LIMIT :limit OFFSET :offset",
    )
    fun getAllGamesOffsetFlow(
        offset: Int,
        limit: Int,
    ): Flow<List<NQueensGamesWon>>

    @Query(
        "SELECT * FROM ${NQueensGamesWon.TABLE_NAME} WHERE ${NQueensGamesWon.COLUMN_PLAYER_NAME} = :playerName ORDER BY ${NQueensGamesWon.COLUMN_DATE} DESC",
    )
    fun getGamesByPlayer(playerName: String): List<NQueensGamesWon>

    @Query(
        "SELECT DISTINCT ${NQueensGamesWon.COLUMN_QUEENS_COUNT} FROM ${NQueensGamesWon.TABLE_NAME} ORDER BY ${NQueensGamesWon.COLUMN_QUEENS_COUNT} ASC",
    )
    fun getDistinctQueensCountsFlow(): Flow<List<Int>>

    @Query(
        "SELECT DISTINCT ${NQueensGamesWon.COLUMN_QUEENS_COUNT} FROM ${NQueensGamesWon.TABLE_NAME} ORDER BY ${NQueensGamesWon.COLUMN_QUEENS_COUNT} ASC",
    )
    fun getDistinctQueensCounts(): List<Int>

    @Query(
        "SELECT * FROM ${NQueensGamesWon.TABLE_NAME} WHERE ${NQueensGamesWon.COLUMN_QUEENS_COUNT} = :queensCount " +
            "ORDER BY ${NQueensGamesWon.COLUMN_TIME_IN_SECONDS} ASC, ${NQueensGamesWon.COLUMN_DATE} DESC",
    )
    fun getGamesByQueensCount(queensCount: Int): List<NQueensGamesWon>

    @Query(
        "SELECT * FROM ${NQueensGamesWon.TABLE_NAME} WHERE ${NQueensGamesWon.COLUMN_QUEENS_COUNT} = :queensCount " +
            "ORDER BY ${NQueensGamesWon.COLUMN_TIME_IN_SECONDS} ASC, ${NQueensGamesWon.COLUMN_DATE} DESC LIMIT :limit OFFSET :offset",
    )
    fun getGamesOffsetByQueensCountFlow(
        queensCount: Int,
        offset: Int,
        limit: Int,
    ): Flow<List<NQueensGamesWon>>
}
