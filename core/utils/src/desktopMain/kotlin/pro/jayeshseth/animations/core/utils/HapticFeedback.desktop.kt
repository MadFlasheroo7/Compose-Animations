package pro.jayeshseth.animations.core.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberHapticFeedback(): HapticFeedback {
    return remember {
        IosHapticFeedback()
    }
}

class IosHapticFeedback : HapticFeedback {
    override fun performTick() {
        TODO("Not yet implemented")
    }

}