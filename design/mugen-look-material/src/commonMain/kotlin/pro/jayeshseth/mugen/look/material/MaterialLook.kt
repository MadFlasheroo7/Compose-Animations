package pro.jayeshseth.mugen.look.material

import androidx.compose.runtime.Stable
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
}
