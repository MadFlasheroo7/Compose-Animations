package pro.jayeshseth.mugen.look.haze

import androidx.compose.animation.core.DurationBasedAnimationSpec
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import pro.jayeshseth.mugen.tokens.MugenMotion

/**
 * Haze motion — slow + bouncy + expressive. Adds Haze-only animation specs that
 * the renderers drive directly: [shimmerSweep], [cornerMorph], [glowPulse].
 */
@Immutable
class HazeMotion(
    override val fast: FiniteAnimationSpec<Float> = tween(durationMillis = 180),
    override val standard: FiniteAnimationSpec<Float> = tween(durationMillis = 320),
    override val slow: FiniteAnimationSpec<Float> = tween(durationMillis = 540),
    override val snappy: SpringSpec<Float> = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium,
    ),
    override val bouncy: SpringSpec<Float> = spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow,
    ),
    override val gentle: SpringSpec<Float> = spring(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessVeryLow,
    ),

    // ----- Haze-only -----
    /** The slow shimmer band that sweeps along the border of interactive surfaces. */
    val shimmerSweep: DurationBasedAnimationSpec<Float> = tween(
        durationMillis = 5500,
        easing = EaseInCubic,
    ),

    /** Spring that drives the corner-radius morph on press. */
    val cornerMorph: SpringSpec<Dp> = spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow,
    ),

    /** Spring that drives the outer-glow shadow alpha. */
    val glowPulse: SpringSpec<Float> = spring(
        dampingRatio = Spring.DampingRatioLowBouncy,
        stiffness = Spring.StiffnessLow,
    ),
) : MugenMotion
