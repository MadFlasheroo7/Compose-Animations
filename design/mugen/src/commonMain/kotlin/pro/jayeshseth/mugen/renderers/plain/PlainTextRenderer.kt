package pro.jayeshseth.mugen.renderers.plain

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import pro.jayeshseth.mugen.renderers.MugenTextRenderer

/**
 * Plain text renderer — delegates directly to Compose's [Text]. Stateless and look-agnostic;
 * color and style are resolved upstream by [pro.jayeshseth.mugen.components.MugenText].
 */
object PlainTextRenderer : MugenTextRenderer {
    @Composable
    override fun Render(
        text: String,
        style: TextStyle,
        color: Color,
        modifier: Modifier,
    ) {
        Text(text = text, style = style, color = color, modifier = modifier)
    }
}
