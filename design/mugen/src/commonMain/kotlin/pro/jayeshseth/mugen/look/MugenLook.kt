package pro.jayeshseth.mugen.look

import androidx.compose.runtime.Stable
import pro.jayeshseth.mugen.renderers.MugenButtonRenderer
import pro.jayeshseth.mugen.renderers.MugenCardRenderer
import pro.jayeshseth.mugen.renderers.MugenTextRenderer
import pro.jayeshseth.mugen.tokens.MugenColors
import pro.jayeshseth.mugen.tokens.MugenElevation
import pro.jayeshseth.mugen.tokens.MugenMotion
import pro.jayeshseth.mugen.tokens.MugenShapes
import pro.jayeshseth.mugen.tokens.MugenSpacing
import pro.jayeshseth.mugen.tokens.MugenTypography

/**
 * The universal Look contract.
 *
 * Each [MugenLook] bundles the six token families plus one renderer per component the
 * library ships. Concrete Looks (`HazeLook`, `MaterialLook`, etc.) implement this
 * interface and use covariant property overrides to expose their own typed token classes
 * — that's how they introduce bespoke tokens (e.g. `HazeColors.glow`) that don't pollute
 * the base contract.
 *
 * To author a custom Look: implement this interface (or extend an existing class) and
 * provide your own renderer instances. To swap Looks at runtime: hoist a `MugenLook`
 * state and pass it to [pro.jayeshseth.mugen.MugenTheme].
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

    // Renderer slots — one per component currently shipping in mugen
    val buttonRenderer: MugenButtonRenderer
    val cardRenderer: MugenCardRenderer
    val textRenderer: MugenTextRenderer
}
