package pro.jayeshseth.animations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
actual fun rememberEasterEggPermissionState(): EasterEggPermissionState {
    return remember { EasterEggPermissionState().apply { isPermissionGranted = true } }
}