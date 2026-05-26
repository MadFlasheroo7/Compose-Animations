package pro.jayeshseth.mugen.locals

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import pro.jayeshseth.mugen.renderers.MugenButtonRenderer
import pro.jayeshseth.mugen.renderers.MugenCardRenderer
import pro.jayeshseth.mugen.renderers.MugenTextRenderer

/**
 * Component-specific defaults for [pro.jayeshseth.mugen.components.MugenButton].
 *
 * Color / motion / typography / elevation tokens are owned by the active
 * [pro.jayeshseth.mugen.look.MugenLook] — they are NOT duplicated here. This data class
 * carries only what is button-specific (size, shape, padding) plus an optional
 * [renderer] sub-tree override.
 */
@Immutable
data class MugenButtonDefaults(
    val minHeight: Dp,
    val minWidth: Dp,
    val contentPadding: PaddingValues,
    val shape: CornerBasedShape,
    /** Sub-tree renderer override. When non-null, beats the active Look's button renderer. */
    val renderer: MugenButtonRenderer? = null,
)

@Immutable
data class MugenCardDefaults(
    val contentPadding: PaddingValues,
    val shape: CornerBasedShape,
    val renderer: MugenCardRenderer? = null,
)

@Immutable
data class MugenTextDefaults(
    val renderer: MugenTextRenderer? = null,
)
