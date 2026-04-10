package pro.jayeshseth.animations.core.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

@Composable
actual fun rememberHapticFeedback(): HapticFeedback {
    val context = LocalContext.current
    return remember {
        AndroidHapticFeedback(context)
    }
}

class AndroidHapticFeedback(private val context: Context) : HapticFeedback {
    private val vibrator = ContextCompat.getSystemService(context, Vibrator::class.java)

    override fun performTick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            vibrator?.vibrate(
                VibrationEffect.createPredefined(VibrationEffect.EFFECT_TICK)
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(10)
        }
    }
}