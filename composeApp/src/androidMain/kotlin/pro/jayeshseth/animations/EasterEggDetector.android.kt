package pro.jayeshseth.animations

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pro.jayeshseth.animations.core.model.EasterEggRepository

private const val TAG = "EasterEggDetector"

@SuppressLint("MissingPermission")
@Composable
actual fun EasterEggDetector(
    enabled: Boolean,
    easterEggRepo: EasterEggRepository?,
    onEasterEggTriggered: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    DisposableEffect(enabled) {
        var job: kotlinx.coroutines.Job? = null
        if (enabled) {
            Log.d(TAG, "Starting detection...")

            job = scope.launch(Dispatchers.IO) {
                try {
                    detectClapsDebug(
                        onClapDetected = {
                            Log.d(TAG, "CLAP DETECTED!")
                            onEasterEggTriggered()
                        },
                        onLog = { message ->
                            Log.d(TAG, "LOG: $message")
                        },
                        onError = { error ->
                            Log.e(TAG, "Detection error: $error")
                        }
                    )
                } catch (e: Exception) {
                    Log.e(TAG, "Error in detection: ${e.message}", e)
                }
            }
        }

        onDispose {
            Log.d(TAG, "Stopping detection...")
            job?.cancel()
        }
    }
}
