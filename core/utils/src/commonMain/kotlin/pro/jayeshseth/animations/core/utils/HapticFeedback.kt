package pro.jayeshseth.animations.core.utils

import androidx.compose.runtime.Composable

interface HapticFeedback {
    fun performTick()
}

@Composable
expect fun rememberHapticFeedback(): HapticFeedback