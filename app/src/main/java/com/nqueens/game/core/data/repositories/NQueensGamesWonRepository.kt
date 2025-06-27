package com.nqueens.game.core.data.repositories

import com.nqueens.game.core.data.database.daos.NQueensGamesWonDao
import com.nqueens.game.core.data.database.entities.NQueensGamesWon
import com.nqueens.game.core.utils.di.IOThreadDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NQueensGamesWonRepository
    @Inject
    constructor(
        private val nQueensGamesWonDao: NQueensGamesWonDao,
        @IOThreadDispatcher private val ioThreadDispatcher: CoroutineDispatcher,
    ) {
        suspend fun insert(game: NQueensGamesWon) =
            withContext(ioThreadDispatcher) {
                nQueensGamesWonDao.insertGame(game)
            }
    }
