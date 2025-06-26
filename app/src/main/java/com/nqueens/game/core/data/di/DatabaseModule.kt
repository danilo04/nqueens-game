package com.nqueens.game.core.data.di

import android.content.Context
import com.nqueens.game.core.data.database.ChessGamesDatabase
import com.nqueens.game.core.data.database.daos.NQueensGamesWonDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun provideChessGamesDatabase(
        @ApplicationContext context: Context,
    ): ChessGamesDatabase = ChessGamesDatabase.buildDatabase(context)

    @Provides
    fun provideNQueensGamesWonDao(database: ChessGamesDatabase): NQueensGamesWonDao = database.nQueensGamesWonDao()
}
