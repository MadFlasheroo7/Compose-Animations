package pro.jayeshseth.mugen.renderers

import androidx.compose.runtime.Stable
import pro.jayeshseth.mugen.renderers.plain.PlainRenderers

/**
 * The drawing layer of a visual style — a collection of one renderer per component mugen
 * ships. Completely independent of [pro.jayeshseth.mugen.look.MugenLook]: a Look defines
 * *who* the UI is (tokens), a RendererSet defines *how* components are drawn.
 *
 * **All properties default to [PlainRenderers]**, so existing RendererSet implementations
 * are never broken when new components ship:
 *
 * ```
 * // Adding MugenChip tomorrow — the only change here:
 * val chip: MugenChipRenderer get() = PlainRenderers.chip
 * // HazeRendererSet, MaterialRendererSet, any custom set — zero changes needed.
 * // They all fall back to PlainRenderers.chip automatically.
 * ```
 *
 * To provide a Look-specific drawing style, implement this interface and override only
 * the components your style covers. Pair with a [pro.jayeshseth.mugen.look.MugenLook]
 * via [pro.jayeshseth.mugen.MugenTheme]:
 *
 * ```
 * MugenTheme(look = HazeLook(), renderers = HazeRendererSet()) { … }
 * ```
 */
@Stable
interface MugenRendererSet {
    /** Renderer for [pro.jayeshseth.mugen.components.MugenButton]. */
    val button: MugenButtonRenderer get() = PlainRenderers.button

    /** Renderer for [pro.jayeshseth.mugen.components.MugenCard]. */
    val card: MugenCardRenderer get() = PlainRenderers.card

    /** Renderer for [pro.jayeshseth.mugen.components.MugenText]. */
    val text: MugenTextRenderer get() = PlainRenderers.text
}
