package pro.jayeshseth.mugen.tokens

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp

/**
 * The universal elevation contract.
 *
 * Each level is an intent ("raised", "floating", "modal"); the renderer for the active Look
 * decides what an elevation *means* visually — flat M3 shadows, Haze glow, Liquid-glass depth.
 *
 * Looks may add bespoke fields (e.g. `HazeElevation.glowSpread` / `glowRadius`, or
 * `MaterialElevation.tonalElevation`).
 */
@Immutable
interface MugenElevation {
    val none: Dp
    val raised: Dp
    val floating: Dp
    val modal: Dp
}
