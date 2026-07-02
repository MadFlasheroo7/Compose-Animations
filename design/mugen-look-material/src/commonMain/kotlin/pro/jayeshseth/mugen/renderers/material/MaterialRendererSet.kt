package pro.jayeshseth.mugen.renderers.material

import pro.jayeshseth.mugen.look.material.MaterialLook
import pro.jayeshseth.mugen.renderers.MugenRendererSet

/**
 * The Material drawing layer — pairs with [MaterialLook] to produce flat M3-style
 * solid fills, ripple indication, and tonal surface visuals.
 *
 * Components not overridden here automatically fall back to
 * [pro.jayeshseth.mugen.renderers.plain.PlainRenderers] via the [MugenRendererSet]
 * interface defaults — no changes needed when new mugen components ship.
 *
 * Use [pro.jayeshseth.mugen.look.material.MugenMaterialTheme] for the zero-friction path.
 */
class MaterialRendererSet(look: MaterialLook = MaterialLook()) : MugenRendererSet {
    override val button = MaterialButtonRenderer(look)
    override val card   = MaterialCardRenderer(look)
    override val text   = MaterialTextRenderer(look)
}
