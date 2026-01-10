package pro.jayeshseth.animations.core.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import org.jetbrains.compose.ui.tooling.preview.Preview

/**
 * A Composable that displays a title and a switch, arranged horizontally.
 * The switch uses a haze effect.
 *
 * @param title The text to display next to the switch.
 * @param hazeState The [HazeState] to apply the blur effect to the switch.
 * @param checked The current state of the switch (true for on, false for off).
 * @param onCheckedChanged A lambda function that is invoked when the user toggles the switch.
 *                         It receives the new checked state as a Boolean.
 * @param modifier The [Modifier] to be applied to the row containing the title and switch.
 * @param enabled A boolean to enable or disable the switch.
 * @param style The [TextStyle] to be applied to the title text.
 */
@Composable
fun Toggler(
    title: String,
    hazeState: HazeState,
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    style: TextStyle = LocalTextStyle.current
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, style = style)
        Spacer(modifier = Modifier.padding(8.dp))
        HazedSwitch(
            hazeState = hazeState,
            toggled = checked,
            onToggleChanged = onCheckedChanged,
            enabled = enabled
        )
    }

//    Rebugger(
//        composableName = "Toggler",
//        trackMap = mapOf(
//            "title" to title,
//            "checked" to checked,
//            "onCheckedChanged" to onCheckedChanged,
//            "modifier" to modifier,
//            "enabled" to enabled,
//            "Arrangement.SpaceBetween" to Arrangement.SpaceBetween,
//            "Alignment.CenterVertically" to Alignment.CenterVertically,
//        ),
//    )
}

@Preview
@Composable
private fun PreviewToggler() {
    var toggled by remember { mutableStateOf(true) }
    ShaderPreviewContent() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .size(500.dp)
                .hazeEffect(
                    state = it,
                    HazeStyle(
                        tint = HazeTint(Color.Black.copy(.4f)),
                        blurRadius = 50.dp,
                        noiseFactor = .1f,
                    )
                )
        ) {
            Toggler(
                title = "Toggler",
                hazeState = it,
                checked = toggled,
                onCheckedChanged = { toggled = it },
                enabled = true
            )
            Toggler(
                title = "Toggler",
                hazeState = it,
                checked = toggled,
                onCheckedChanged = { toggled = it },
                enabled = true
            )
        }
    }

}