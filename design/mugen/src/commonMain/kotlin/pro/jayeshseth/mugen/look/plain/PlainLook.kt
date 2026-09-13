package pro.jayeshseth.mugen.look.plain

import androidx.compose.runtime.Stable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
import pro.jayeshseth.mugen.components.MugenChipDefaults
import pro.jayeshseth.mugen.locals.MugenButtonDefaults
import pro.jayeshseth.mugen.locals.MugenCardDefaults
import pro.jayeshseth.mugen.locals.MugenDefaultsRegistry
import pro.jayeshseth.mugen.locals.MugenTextDefaults
import pro.jayeshseth.mugen.locals.mugenDefaults
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

    override val defaults: MugenDefaultsRegistry = mugenDefaults {
        register(
            MugenButtonDefaults(
                minHeight = 48.dp,
                minWidth = 64.dp,
                contentPadding = PaddingValues(horizontal = spacing.lg, vertical = spacing.md),
                shape = shapes.lg,
            )
        )
        register(
            MugenCardDefaults(
                contentPadding = PaddingValues(spacing.lg),
                shape = shapes.lg,
            )
        )
        register(MugenTextDefaults())
        register(
            MugenChipDefaults(
                contentPadding = PaddingValues(horizontal = spacing.md, vertical = spacing.sm),
                shape = shapes.sm,
            )
        )
    }
}
