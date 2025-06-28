package com.nqueens.game.core.utils.sound

import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.RawRes
import com.nqueens.game.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SoundManager
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : SoundManagerInterface {
        private var mediaPlayer: MediaPlayer? = null

        override fun playPutPieceSound() {
            playSound(R.raw.put_piece)
        }

        private fun playSound(
            @RawRes soundResId: Int,
        ) {
            try {
                // Release any existing MediaPlayer
                mediaPlayer?.release()

                // Create and configure new MediaPlayer
                mediaPlayer = MediaPlayer.create(context, soundResId)
                mediaPlayer?.setOnCompletionListener { mp ->
                    mp.release()
                    mediaPlayer = null
                }

                // Play the sound
                mediaPlayer?.start()
            } catch (e: Exception) {
                // Silently handle any audio playback errors
                // This ensures the game continues working even if audio fails
                mediaPlayer?.release()
                mediaPlayer = null
            }
        }

        override fun release() {
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }
