package pro.jayeshseth.mugen.look.haze

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pro.jayeshseth.mugen.tokens.MugenShapes

/**
 * Haze shapes — slightly more rounded than Material defaults. Adds a bespoke
 * [interactiveMorphRest] / [interactiveMorphActive] pair driving the signature
 * "corner flattens to 18dp on press" animation in [pro.jayeshseth.mugen.renderers.haze.HazeButtonRenderer].
 */
@Immutable
class HazeShapes(
    override val xs: CornerBasedShape = RoundedCornerShape(8.dp),
    override val sm: CornerBasedShape = RoundedCornerShape(12.dp),
    override val md: CornerBasedShape = RoundedCornerShape(18.dp),
    override val lg: CornerBasedShape = RoundedCornerShape(24.dp),
    override val xl: CornerBasedShape = RoundedCornerShape(32.dp),
    override val pill: CornerBasedShape = RoundedCornerShape(100.dp),

    // ----- Haze-only -----
    /** Corner radius when an interactive surface is at rest. */
    val interactiveMorphRest: Dp = 100.dp,

    /** Corner radius the surface morphs to when pressed / hovered. */
    val interactiveMorphActive: Dp = 18.dp,
) : MugenShapes
