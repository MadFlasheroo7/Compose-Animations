package pro.jayeshseth.mugen.renderers.haze

import pro.jayeshseth.mugen.look.haze.HazeLook
import pro.jayeshseth.mugen.renderers.MugenRendererSet

/**
 * The Haze drawing layer — pairs with [HazeLook] to produce the signature glassmorphic,
 * glow-shadow, corner-morphing visual identity.
 *
 * Each renderer holds a typed [HazeLook] reference to access bespoke tokens
 * (`colors.glow`, `motion.cornerMorph`, `elevation.glowSpread`, etc.) that don't exist
 * on the base [pro.jayeshseth.mugen.tokens.MugenColors] / [pro.jayeshseth.mugen.tokens.MugenMotion]
 * contracts.
 *
 * Components not overridden here (added in future mugen-core releases) automatically fall
 * back to [pro.jayeshseth.mugen.renderers.plain.PlainRenderers] via the [MugenRendererSet]
 * interface defaults — no changes needed to this class when new components ship.
 *
 * Use [HazeTheme] for the zero-friction path:
 * ```
 * HazeTheme { MugenButton(onClick = {}) { MugenText("Hi") } }
 * ```
 */
class HazeRendererSet(look: HazeLook = HazeLook()) : MugenRendererSet {
    override val button = HazeButtonRenderer(look)
    override val card   = HazeCardRenderer(look)
    override val text   = HazeTextRenderer(look)
}
