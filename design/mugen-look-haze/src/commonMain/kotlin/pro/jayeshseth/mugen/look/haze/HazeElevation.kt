package pro.jayeshseth.mugen.look.haze

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pro.jayeshseth.mugen.tokens.MugenElevation

/**
 * Haze elevation. The base interface measures elevation in `dp`; HazeElevation adds
 * the bespoke [glowSpread] / [glowRadius] / [hazeBlur] driving its renderer's
 * glow + glassmorphism look.
 */
@Immutable
class HazeElevation(
    override val none: Dp = 0.dp,
    override val raised: Dp = 2.dp,
    override val floating: Dp = 8.dp,
    override val modal: Dp = 24.dp,

    // ----- Haze-only -----
    /** Outer-glow spread (px) on interactive surfaces. */
    val glowSpread: Float = 20f,

    /** Outer-glow blur radius (px) on interactive surfaces. */
    val glowRadius: Float = 20f,

    /** Outer-glow blur radius on the *outer* ambient ring. */
    val glowSpread2: Float = 1500f,

    /** Haze blur radius applied behind glassmorphic surfaces. */
    val hazeBlur: Dp = 80.dp,

    /** Inner-shadow spread (px) on pressed surfaces. */
    val innerShadowSpread: Float = 10f,

    /** Inner-shadow blur radius (px) on pressed surfaces. */
    val innerShadowRadius: Float = 10f,
) : MugenElevation
