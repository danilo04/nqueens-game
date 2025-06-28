package com.nqueens.game.core.utils.haptic

/**
 * Interface for providing haptic feedback functionality.
 * This abstraction allows for testing with dummy implementations.
 */
interface HapticFeedbackInterface {
    /**
     * Provides haptic feedback when the game becomes blocked (queens are attacking each other).
     */
    fun provideErrorFeedback()
}
