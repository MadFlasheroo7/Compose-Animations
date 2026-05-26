package pro.jayeshseth.mugen.capabilities

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
actual fun resolveMugenCapabilities(): MugenCapabilities = MugenCapabilities(
    supportsRuntimeShaders = true,   // Skia backend on JVM supports AGSL
    supportsBlur = true,
    supportsInnerShadow = true,
    supportsHaze = true,
    deviceCornerRadius = 0.dp,        // desktop = no rounded device corners
)
