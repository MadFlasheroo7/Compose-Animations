package pro.jayeshseth.mugen.look.material

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Immutable
import pro.jayeshseth.mugen.tokens.MugenMotion

/**
 * Material 3 motion — short durations + fast-out-slow-in easing.
 */
@Immutable
class MaterialMotion(
    override val fast: FiniteAnimationSpec<Float> = tween(150, easing = FastOutSlowInEasing),
    override val standard: FiniteAnimationSpec<Float> = tween(250, easing = FastOutSlowInEasing),
    override val slow: FiniteAnimationSpec<Float> = tween(400, easing = FastOutSlowInEasing),
    override val snappy: SpringSpec<Float> = spring(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessMedium,
    ),
    override val bouncy: SpringSpec<Float> = spring(
        dampingRatio = Spring.DampingRatioMediumBouncy,
        stiffness = Spring.StiffnessMedium,
    ),
    override val gentle: SpringSpec<Float> = spring(
        dampingRatio = Spring.DampingRatioNoBouncy,
        stiffness = Spring.StiffnessLow,
    ),
) : MugenMotion
