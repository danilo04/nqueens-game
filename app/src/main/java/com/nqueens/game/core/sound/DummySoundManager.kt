package com.nqueens.game.core.sound

/**
 * A dummy implementation of sound functionality for testing purposes.
 * This implementation does nothing, ensuring tests don't rely on actual audio playback.
 */
class DummySoundManager : SoundManagerInterface {
    override fun playPutPieceSound() {
        // Do nothing in tests
    }

    override fun release() {
        // Do nothing in tests
    }
}

/**
 * Interface for sound management to allow for testing with dummy implementations.
 */
interface SoundManagerInterface {
    fun playPutPieceSound()

    fun release()
}
