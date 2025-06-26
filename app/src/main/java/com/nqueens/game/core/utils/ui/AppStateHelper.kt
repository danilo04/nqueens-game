package com.nqueens.game.core.utils.ui

import android.app.Activity
import androidx.activity.compose.LocalActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner

@Composable
fun rememberAppState(): MutableState<Boolean> {
    val context: Activity? = LocalActivity.current
    val appInForeground = remember { mutableStateOf(true) }

    DisposableEffect(context) {
        if (context == null) return@DisposableEffect onDispose { }

        val observer =
            object : LifecycleEventObserver {
                override fun onStateChanged(
                    source: LifecycleOwner,
                    event: Lifecycle.Event,
                ) {
                    when (event) {
                        Lifecycle.Event.ON_PAUSE -> {
                            appInForeground.value = false
                        }
                        Lifecycle.Event.ON_RESUME -> {
                            appInForeground.value = true
                        }
                        else -> {}
                    }
                }
            }
        val lifecycle = (context as? LifecycleOwner)?.lifecycle
        lifecycle?.addObserver(observer)

        onDispose {
            lifecycle?.removeObserver(observer)
        }
    }

    return appInForeground
}
