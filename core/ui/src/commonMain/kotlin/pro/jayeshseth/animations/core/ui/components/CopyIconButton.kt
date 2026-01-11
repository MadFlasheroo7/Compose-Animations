package pro.jayeshseth.animations.core.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CopyAll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.chrisbanes.haze.HazeState
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A Composable icon button with a copy icon, integrated with a haze effect.
 *
 * This button utilizes [HazedIconButton] to display a copy icon (`Icons.Rounded.CopyAll`)
 * with a background blur effect controlled by the provided [hazeState]. It's a specialized
 * button for actions that involve copying content.
 *
 * @param onClick The lambda to be executed when the button is clicked.
 * @param hazeState The [HazeState] used to control the background blur effect behind the button.
 * @param modifier The [Modifier] to be applied to this button. Defaults to [Modifier].
 * @param color The color to be used for the icon tint and the button's haze area. Defaults to [Color.Cyan].
 */
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