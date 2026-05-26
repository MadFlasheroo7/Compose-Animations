package pro.jayeshseth.mugen.capabilities

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
actual fun resolveMugenCapabilities(): MugenCapabilities = MugenCapabilities(
    supportsRuntimeShaders = false,
    supportsBlur = true,
    supportsInnerShadow = true,
    supportsHaze = false,
    deviceCornerRadius = 0.dp,
)
