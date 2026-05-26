package pro.jayeshseth.mugen.tokens

import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.Immutable

/**
 * The universal shape contract. A semantic 6-step scale every Look provides.
 * Looks may add bespoke shapes (e.g. `HazeShapes.interactiveMorph` for the press-animated corner).
 */
@Immutable
interface MugenShapes {
    val xs: CornerBasedShape
    val sm: CornerBasedShape
    val md: CornerBasedShape
    val lg: CornerBasedShape
    val xl: CornerBasedShape

    /** Fully rounded — short edges become semicircles. */
    val pill: CornerBasedShape
}
