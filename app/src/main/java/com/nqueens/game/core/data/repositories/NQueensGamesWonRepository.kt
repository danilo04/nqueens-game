package com.nqueens.game.core.data.repositories

import com.nqueens.game.core.data.database.daos.NQueensGamesWonDao
import com.nqueens.game.core.data.database.entities.NQueensGamesWon
import com.nqueens.game.core.utils.di.IOThreadDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
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

        fun getAllGamesOffsetFlow(page: Int = 0): Flow<List<NQueensGamesWon>> =
            nQueensGamesWonDao
                .getAllGamesOffsetFlow(offset = PAGE_SIZE * page, limit = PAGE_SIZE)
                .flowOn(ioThreadDispatcher)

        fun getGamesOffsetByQueensCountFlow(
            queensCount: Int,
            page: Int = 0,
        ): Flow<List<NQueensGamesWon>> =
            nQueensGamesWonDao
                .getGamesOffsetByQueensCountFlow(
                    queensCount = queensCount,
                    offset = PAGE_SIZE * page,
                    limit = PAGE_SIZE,
                ).flowOn(ioThreadDispatcher)

        fun getDistinctQueensCountsFlow(): Flow<List<Int>> =
            nQueensGamesWonDao.getDistinctQueensCountsFlow().flowOn(ioThreadDispatcher)

        suspend fun getDistinctQueensCounts(): List<Int> =
            withContext(ioThreadDispatcher) {
                nQueensGamesWonDao.getDistinctQueensCounts()
            }

        companion object {
            private const val PAGE_SIZE = 15
        }
    }
