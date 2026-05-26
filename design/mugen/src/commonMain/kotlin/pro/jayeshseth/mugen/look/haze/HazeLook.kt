package pro.jayeshseth.mugen.look.haze

import androidx.compose.runtime.Stable
import pro.jayeshseth.mugen.look.MugenLook
import pro.jayeshseth.mugen.renderers.MugenButtonRenderer
import pro.jayeshseth.mugen.renderers.MugenCardRenderer
import pro.jayeshseth.mugen.renderers.MugenTextRenderer
import pro.jayeshseth.mugen.renderers.haze.HazeButtonRenderer
import pro.jayeshseth.mugen.renderers.haze.HazeCardRenderer
import pro.jayeshseth.mugen.renderers.haze.HazeTextRenderer

/**
 * The "signature" Haze look — glassmorphic surfaces, glowing shadows, animated corner morph,
 * shimmer borders, inner shadows on press.
 *
 * Covariant overrides on the token properties expose [HazeColors] / [HazeMotion] / etc.
 * to anyone holding a [HazeLook] reference — so the typed renderers below can read
 * bespoke tokens like `colors.glow`, `motion.shimmerSweep`, `elevation.glowSpread`.
 *
 * @param colors  Haze color palette + bespoke `glow` / `shimmer` / `hazeTint` / `innerShadow`.
 * @param shapes  Haze shapes + bespoke `interactiveMorphRest` / `interactiveMorphActive`.
 * @param motion  Haze motion + bespoke `shimmerSweep` / `cornerMorph` / `glowPulse`.
 */
@Stable
class HazeLook(
    override val colors: HazeColors = HazeColors(),
    override val shapes: HazeShapes = HazeShapes(),
    override val motion: HazeMotion = HazeMotion(),
    override val typography: HazeTypography = HazeTypography(),
    override val spacing: HazeSpacing = HazeSpacing(),
    override val elevation: HazeElevation = HazeElevation(),
) : MugenLook {
    override val name: String = "Haze"

    override val buttonRenderer: MugenButtonRenderer = HazeButtonRenderer(this)
    override val cardRenderer: MugenCardRenderer = HazeCardRenderer(this)
    override val textRenderer: MugenTextRenderer = HazeTextRenderer(this)
}
