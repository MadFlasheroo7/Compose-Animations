package pro.jayeshseth.mugen.look.material

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
 * A Material 3-flavoured Look — flat container fills, ripple indication, tonal surfaces.
 *
 * `MaterialLook` is a pure **visual identity** — it carries tokens only. The drawing logic
 * lives in `MaterialRendererSet` (same module). Pair them via [pro.jayeshseth.mugen.MugenTheme]
 * or the convenience [MugenMaterialTheme] wrapper:
 *
 * ```
 * MugenMaterialTheme { … }
 * // or explicit:
 * MugenTheme(look = MaterialLook(), renderers = MaterialRendererSet()) { … }
 * ```
 */
@Stable
class MaterialLook(
    override val colors: MaterialColors = MaterialColors(),
    override val shapes: MaterialShapes = MaterialShapes(),
    override val motion: MaterialMotion = MaterialMotion(),
    override val typography: MaterialTypography = MaterialTypography(),
    override val spacing: MaterialSpacing = MaterialSpacing(),
    override val elevation: MaterialElevation = MaterialElevation(),
) : MugenLook {
    override val name: String = "Material"

    override val defaults: MugenDefaultsRegistry = mugenDefaults {
        register(
            MugenButtonDefaults(
                minHeight = 40.dp,
                minWidth = 64.dp,
                contentPadding = PaddingValues(horizontal = spacing.lg, vertical = spacing.sm),
                shape = shapes.lg,
            )
        )
        register(
            MugenCardDefaults(
                contentPadding = PaddingValues(spacing.lg),
                shape = shapes.md,
            )
        )
        register(MugenTextDefaults())
        register(
            MugenChipDefaults(
                contentPadding = PaddingValues(horizontal = spacing.md, vertical = spacing.xs),
                shape = shapes.sm,
            )
        )
    }
}
