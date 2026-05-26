package pro.jayeshseth.mugen.capabilities

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Snapshot of what visual features the host target supports. Renderers query this
 * (via [pro.jayeshseth.mugen.locals.LocalMugenCapabilities]) and gracefully degrade —
 * a Look that wants a runtime-shader effect on a target without shader support should
 * fall back to a simpler visual rather than crash.
 *
 * Resolved per-target via the [resolveMugenCapabilities] composable.
 */
@Immutable
data class MugenCapabilities(
    val supportsRuntimeShaders: Boolean,
    val supportsBlur: Boolean,
    val supportsInnerShadow: Boolean,
    val supportsHaze: Boolean,
    /** Device's physical screen corner radius. Zero when the value isn't reachable. */
    val deviceCornerRadius: Dp = 0.dp,
)

/**
 * Returns the [MugenCapabilities] of the current platform / device.
 * Implemented per-target in `androidMain`, `iosMain`, `desktopMain`, `wasmJsMain`, `jsMain`.
 */
@Composable
expect fun resolveMugenCapabilities(): MugenCapabilities
