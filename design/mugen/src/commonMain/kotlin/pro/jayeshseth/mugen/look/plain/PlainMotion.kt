package pro.jayeshseth.mugen.look.plain

import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Immutable
import pro.jayeshseth.mugen.tokens.MugenMotion

/** Plain motion — standard tween and spring specs, no bespoke animation tokens. */
@Immutable
class PlainMotion(
    override val fast: FiniteAnimationSpec<Float> = tween(durationMillis = 150),
    override val standard: FiniteAnimationSpec<Float> = tween(durationMillis = 300),
    override val slow: FiniteAnimationSpec<Float> = tween(durationMillis = 500),
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
) : MugenMotion
