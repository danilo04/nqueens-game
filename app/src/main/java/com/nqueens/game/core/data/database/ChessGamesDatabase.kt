package com.nqueens.game.core.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nqueens.game.core.data.database.daos.NQueensGamesWonDao
import com.nqueens.game.core.data.database.entities.NQueensGamesWon

private const val DATABASE_VERSION = 1

@Database(
    entities = [
        NQueensGamesWon::class,
    ],
    version = DATABASE_VERSION,
)
abstract class ChessGamesDatabase : RoomDatabase() {
    abstract fun nQueensGamesWonDao(): NQueensGamesWonDao

    companion object {
        @Volatile
        private var instance: ChessGamesDatabase? = null

        fun buildDatabase(context: Context): ChessGamesDatabase =
            instance ?: synchronized(this) {
                val newInstance =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            ChessGamesDatabase::class.java,
                            "chess_games.database",
                        ).build()
                instance = newInstance
                newInstance
            }
    }
}
