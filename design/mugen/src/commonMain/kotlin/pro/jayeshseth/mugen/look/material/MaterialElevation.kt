package pro.jayeshseth.mugen.look.material

import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import pro.jayeshseth.mugen.tokens.MugenElevation

/**
 * Material 3 elevation values, plus a Material-only [tonalElevation] scale that
 * MaterialLook renderers use to apply a tonal-surface tint.
 */
@Immutable
class MaterialElevation(
    override val none: Dp = 0.dp,
    override val raised: Dp = 1.dp,
    override val floating: Dp = 6.dp,
    override val modal: Dp = 12.dp,

    // Material-only — tonal elevation tint amounts (M3 uses tonal overlay rather than just shadow).
    val tonalNone: Dp = 0.dp,
    val tonalRaised: Dp = 1.dp,
    val tonalFloating: Dp = 3.dp,
    val tonalModal: Dp = 8.dp,
) : MugenElevation
