package pro.jayeshseth.mugen.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import pro.jayeshseth.mugen.locals.LocalMugenColors
import pro.jayeshseth.mugen.locals.LocalMugenLook
import pro.jayeshseth.mugen.locals.LocalMugenTextDefaults
import pro.jayeshseth.mugen.locals.LocalMugenTypography
import pro.jayeshseth.mugen.renderers.MugenTextRenderer

/**
 * Reads typography from [LocalMugenTypography] by default; the [style] param overrides for
 * a single call. Color defaults to the active Look's `onSurface`.
 */
@Composable
fun MugenText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle? = null,
    color: Color = Color.Unspecified,
    renderer: MugenTextRenderer? = null,
) {
    val defaults = LocalMugenTextDefaults.current
    val activeRenderer = renderer
        ?: defaults.renderer
        ?: LocalMugenLook.current.textRenderer
    val effectiveStyle = style ?: LocalMugenTypography.current.bodyMedium
    val effectiveColor = if (color == Color.Unspecified) LocalMugenColors.current.onSurface else color
    activeRenderer.Render(
        text = text,
        style = effectiveStyle,
        color = effectiveColor,
        modifier = modifier,
    )
}
