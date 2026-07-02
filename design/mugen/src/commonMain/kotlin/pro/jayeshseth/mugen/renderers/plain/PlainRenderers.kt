package pro.jayeshseth.mugen.renderers.plain

import pro.jayeshseth.mugen.renderers.MugenRendererSet

/**
 * The built-in zero-dependency [MugenRendererSet] bundled with mugen core.
 *
 * All renderers read tokens from composition locals — they work correctly with
 * *any* [pro.jayeshseth.mugen.look.MugenLook], making `PlainRenderers` a genuine
 * "default style" rather than a hard-coded visual:
 *
 * - `MugenTheme(look = HazeLook())` → Haze colors + Plain drawing
 * - `MugenTheme(look = MyBrandLook())` → brand colors + Plain drawing
 *
 * `PlainRenderers` is also the fallback for every property of [MugenRendererSet], so
 * any custom RendererSet that overrides only some components automatically falls back
 * here for the rest.
 */
object PlainRenderers : MugenRendererSet {
    override val button = PlainButtonRenderer
    override val card   = PlainCardRenderer
    override val text   = PlainTextRenderer
}
