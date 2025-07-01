package com.nqueens.game

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * MainCoroutineRule installs a TestCoroutineDispatcher for Disptachers.Main.
 *
 * You may call [DelayController] methods on [StandardMainCoroutineScopeRule] and they will control the
 * virtual-clock.
 *
 * By default, [StandardMainCoroutineScopeRule] will be in a *resumed* state.
 */
@ExperimentalCoroutinesApi
class StandardMainCoroutineScopeRule : TestWatcher() {
    lateinit var dispatcher: TestDispatcher

    override fun starting(description: Description) {
        super.starting(description)
        dispatcher = StandardTestDispatcher()
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
        dispatcher.cancel()
    }
}
