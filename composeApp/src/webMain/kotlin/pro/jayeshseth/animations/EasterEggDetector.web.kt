package pro.jayeshseth.animations

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import pro.jayeshseth.animations.core.model.EasterEggRepository

@Composable
actual fun EasterEggDetector(
    enabled: Boolean,
    easterEggRepo: EasterEggRepository?,
    onEasterEggTriggered: () -> Unit,
) {
    LaunchedEffect(Unit) {
        easterEggRepo?.markTriggered()
    }
}