package pro.jayeshseth.mugen.renderers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Stable
interface MugenTextRenderer {
    @Composable
    fun Render(
        text: String,
        style: TextStyle,
        color: Color,
        modifier: Modifier,
    )
}
