package pro.jayeshseth.animations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

@Stable
class EasterEggPermissionState {
    var isPermissionGranted by mutableStateOf(false)
    var isPermanentlyDenied by mutableStateOf(false)
    var dontShowEverAgain by mutableStateOf(false)
    var requestPermission: () -> Unit = {}
    var markDontShowEverAgain: () -> Unit = {}
    var openAppSettings: () -> Unit = {}
}

@Composable
expect fun rememberEasterEggPermissionState(): EasterEggPermissionState