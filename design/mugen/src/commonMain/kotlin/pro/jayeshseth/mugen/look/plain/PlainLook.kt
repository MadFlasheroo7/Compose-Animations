package pro.jayeshseth.mugen.look.plain

import androidx.compose.runtime.Stable
import pro.jayeshseth.mugen.look.MugenLook

/**
 * The built-in zero-dependency Look bundled with mugen core.
 *
 * `PlainLook` serves two roles:
 * 1. **Default Look** — `MugenTheme()` defaults to `PlainLook` so library consumers get a
 *    working UI without importing any extra dependency.
 * 2. **Renderer fallback** — [pro.jayeshseth.mugen.renderers.plain.PlainRenderers] renders
 *    all components using the *active* look's base tokens (read from composition locals),
 *    so `PlainLook + PlainRenderers` gives a clean neutral output while
 *    `HazeLook + PlainRenderers` gives Haze *colors* with Plain *drawing*.
 *
 * Custom Look authors that only want to adjust the palette can extend `PlainLook` and
 * override any of the six token properties — they get Plain rendering for free until they
 * pair a custom [pro.jayeshseth.mugen.renderers.MugenRendererSet].
 */
@Stable
class PlainLook(
    override val colors: PlainColors = PlainColors(),
    override val shapes: PlainShapes = PlainShapes(),
    override val motion: PlainMotion = PlainMotion(),
    override val typography: PlainTypography = PlainTypography(),
    override val spacing: PlainSpacing = PlainSpacing(),
    override val elevation: PlainElevation = PlainElevation(),
) : MugenLook {
    override val name: String = "Plain"
}
