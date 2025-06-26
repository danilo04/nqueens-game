package com.nqueens.game.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = NQueensGamesWon.TABLE_NAME,
    indices = [Index(value = [NQueensGamesWon.COLUMN_QUEENS_COUNT])],
)
data class NQueensGamesWon(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    @ColumnInfo(name = NQueensGamesWon.COLUMN_PLAYER_NAME)
    val playerName: String,
    @ColumnInfo(name = NQueensGamesWon.COLUMN_QUEENS_COUNT)
    val queensCount: Int,
    @ColumnInfo(name = NQueensGamesWon.COLUMN_TIME_IN_SECONDS)
    val timeInSeconds: Long,
    @ColumnInfo(name = NQueensGamesWon.COLUMN_DATE)
    val datePlayed: Long,
) {
    companion object {
        const val TABLE_NAME = "nqueens_games_won"
        const val COLUMN_PLAYER_NAME = "player_name"
        const val COLUMN_QUEENS_COUNT = "queens_count"
        const val COLUMN_TIME_IN_SECONDS = "time_in_seconds"
        const val COLUMN_DATE = "date_played"
    }
}
