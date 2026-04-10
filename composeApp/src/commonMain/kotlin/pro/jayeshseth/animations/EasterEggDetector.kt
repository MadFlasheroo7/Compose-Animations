package pro.jayeshseth.animations

import androidx.compose.runtime.Composable
import pro.jayeshseth.animations.core.model.EasterEggRepository

@Composable
expect fun EasterEggDetector(
    enabled: Boolean,
    easterEggRepo: EasterEggRepository?,
    onEasterEggTriggered: () -> Unit,
)
