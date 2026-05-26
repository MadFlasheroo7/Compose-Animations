package pro.jayeshseth.mugen.look.material

import androidx.compose.runtime.Stable
import pro.jayeshseth.mugen.look.MugenLook
import pro.jayeshseth.mugen.renderers.MugenButtonRenderer
import pro.jayeshseth.mugen.renderers.MugenCardRenderer
import pro.jayeshseth.mugen.renderers.MugenTextRenderer
import pro.jayeshseth.mugen.renderers.material.MaterialButtonRenderer
import pro.jayeshseth.mugen.renderers.material.MaterialCardRenderer
import pro.jayeshseth.mugen.renderers.material.MaterialTextRenderer

/**
 * A Material 3-flavoured Look — flat container fills, ripple indication, tonal surfaces.
 * Implemented as mugen renderers (not a thin wrapper over `androidx.compose.material3`)
 * so it stays swappable through the same renderer-slot machinery as every other Look.
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

    override val buttonRenderer: MugenButtonRenderer = MaterialButtonRenderer(this)
    override val cardRenderer: MugenCardRenderer = MaterialCardRenderer(this)
    override val textRenderer: MugenTextRenderer = MaterialTextRenderer(this)
}
