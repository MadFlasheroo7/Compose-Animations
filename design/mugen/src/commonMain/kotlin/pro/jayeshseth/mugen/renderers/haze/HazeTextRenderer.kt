package pro.jayeshseth.mugen.renderers.haze

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import pro.jayeshseth.mugen.look.haze.HazeLook
import pro.jayeshseth.mugen.renderers.MugenTextRenderer

class HazeTextRenderer(@Suppress("unused") private val look: HazeLook) : MugenTextRenderer {
    @Composable
    override fun Render(
        text: String,
        style: TextStyle,
        color: Color,
        modifier: Modifier,
    ) {
        Text(
            text = text,
            style = style,
            color = color,
            modifier = modifier,
        )
    }
}
