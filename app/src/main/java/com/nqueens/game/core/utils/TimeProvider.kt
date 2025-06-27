package com.nqueens.game.core.utils

import java.util.Calendar
import java.util.TimeZone
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TimeProvider
    @Inject
    constructor() {
        fun currentUTCEpoch(): Long = Calendar.getInstance(TimeZone.getTimeZone("UTC")).timeInMillis
    }
