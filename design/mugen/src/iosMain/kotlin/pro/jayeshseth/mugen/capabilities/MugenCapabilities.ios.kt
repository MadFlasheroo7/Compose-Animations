package pro.jayeshseth.mugen.capabilities

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
actual fun resolveMugenCapabilities(): MugenCapabilities = MugenCapabilities(
    supportsRuntimeShaders = true,   // Skia/Metal backend supports AGSL on iOS
    supportsBlur = true,
    supportsInnerShadow = true,
    supportsHaze = true,
    // Dynamic Island / notch corner radius not exposed via standard public API; left at 0.
    deviceCornerRadius = 0.dp,
)
