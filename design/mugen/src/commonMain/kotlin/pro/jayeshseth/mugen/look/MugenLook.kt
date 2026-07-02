package pro.jayeshseth.mugen.look

import androidx.compose.runtime.Stable
import pro.jayeshseth.mugen.tokens.MugenColors
import pro.jayeshseth.mugen.tokens.MugenElevation
import pro.jayeshseth.mugen.tokens.MugenMotion
import pro.jayeshseth.mugen.tokens.MugenShapes
import pro.jayeshseth.mugen.tokens.MugenSpacing
import pro.jayeshseth.mugen.tokens.MugenTypography

/**
 * The universal Look contract — pure visual identity.
 *
 * A [MugenLook] defines **who** the UI is (colors, shapes, motion, typography, spacing,
 * elevation). It does **not** define **how** components are drawn — that is the
 * responsibility of [pro.jayeshseth.mugen.renderers.MugenRendererSet].
 *
 * Concrete Looks (`HazeLook`, `MaterialLook`, etc.) implement this interface and use
 * covariant property overrides to expose their own typed token classes — that's how they
 * introduce bespoke tokens (e.g. `HazeColors.glow`) without polluting the base contract.
 *
 * To author a custom Look: implement this interface (six token families + a name). No
 * renderer knowledge needed. To swap Looks at runtime: hoist a `MugenLook` state and
 * pass it to [pro.jayeshseth.mugen.MugenTheme].
 */
@Stable
interface MugenLook {
    val name: String

    // Token contracts — every Look provides these, optionally typed to a Look-specific subclass
    val colors: MugenColors
    val shapes: MugenShapes
    val motion: MugenMotion
    val typography: MugenTypography
    val spacing: MugenSpacing
    val elevation: MugenElevation
}

