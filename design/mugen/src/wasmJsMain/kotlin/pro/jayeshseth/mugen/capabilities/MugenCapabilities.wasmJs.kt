package pro.jayeshseth.mugen.capabilities

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
actual fun resolveMugenCapabilities(): MugenCapabilities = MugenCapabilities(
    supportsRuntimeShaders = false,  // No AGSL on the wasm Skia path yet
    supportsBlur = true,
    supportsInnerShadow = true,
    supportsHaze = false,            // depends on AGSL → falls back to flat fill
    deviceCornerRadius = 0.dp,
)
