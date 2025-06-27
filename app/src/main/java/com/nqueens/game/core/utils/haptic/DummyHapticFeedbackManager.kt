package com.nqueens.game.core.utils.haptic

/**
 * A dummy implementation of haptic feedback functionality for testing purposes.
 * This implementation does nothing, ensuring tests don't rely on actual haptic feedback.
 */
class DummyHapticFeedbackManager : HapticFeedbackInterface {
    override fun provideErrorFeedback() {
        // Do nothing in tests
    }
}
