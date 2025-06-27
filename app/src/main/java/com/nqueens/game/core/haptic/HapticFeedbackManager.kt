package com.nqueens.game.core.haptic

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HapticFeedbackManager
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : HapticFeedbackInterface {
        private val vibrator: Vibrator? by lazy {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                context.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }
        }

        override fun provideErrorFeedback() {
            vibrator?.let { vib ->
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    // Use VibrationEffect for API 26+
                    val effect = VibrationEffect.createOneShot(FEEDBACK_DURATION, AMPLITUDE)
                    vib.vibrate(effect)
                } else {
                    // Fallback for older devices
                    @Suppress("DEPRECATION")
                    vib.vibrate(FEEDBACK_DURATION)
                }
            }
        }

        companion object {
            private const val FEEDBACK_DURATION = 100L
            private const val AMPLITUDE = 40
        }
    }
