package pro.jayeshseth.animations.core.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CopyAll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import dev.chrisbanes.haze.HazeState

@Composable
fun CopyIconButton(
    onClick: () -> Unit,
    hazeState: HazeState,
    modifier: Modifier = Modifier,
    color: Color = Color.Cyan
) {
    HazedIconButton(
        onClick = onClick,
        modifier = modifier,
        hazeState = hazeState,
        color = color
    ) {
        Icon(
            Icons.Rounded.CopyAll, "",
            tint = color
        )
    }
}

@Preview
@Composable
private fun PreviewCopyButton() {
    ShaderPreviewContent {

        CopyIconButton({}, it)
    }
}