package pro.jayeshseth.mugen.tokens

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * The universal color contract every [pro.jayeshseth.mugen.look.MugenLook] must satisfy.
 *
 * Looks may extend this interface in their own concrete classes (e.g. `HazeColors`,
 * `MaterialColors`) to add bespoke color tokens that are specific to that Look. Generic
 * cross-Look code reads only this base surface; Look-typed renderers read the rich surface.
 */
@Immutable
interface MugenColors {
    /** The dominant brand color. */
    val primary: Color

    /** Content drawn on top of [primary]. */
    val onPrimary: Color

    /** The secondary brand color used for accents and highlights. */
    val accent: Color

    /** Content drawn on top of [accent]. */
    val onAccent: Color

    /** The surface used by cards, sheets, and primary backgrounds. */
    val surface: Color

    /** Content drawn on top of [surface]. */
    val onSurface: Color

    /** Variant surface used for secondary fills (dividers backgrounds, subtle containers). */
    val surfaceVariant: Color

    /** Content drawn on top of [surfaceVariant]. */
    val onSurfaceVariant: Color

    /** Outline color used for strokes and borders. */
    val outline: Color

    /** Destructive / error color. */
    val danger: Color

    /** Cautionary color. */
    val warning: Color

    /** Confirmatory color. */
    val success: Color
}
