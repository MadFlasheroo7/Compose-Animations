package pro.jayeshseth.animations.masterCustomization.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import dev.chrisbanes.haze.HazeState
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.ui.components.HazedSwitch
import pro.jayeshseth.animations.core.ui.components.ShaderPreviewContent

@Composable
fun DemoSwitch(
    hazeState: HazeState,
    toggled: Boolean,
    onToggleChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    HazedSwitch(hazeState, toggled, onToggleChanged, modifier = modifier)
}

@Preview
@Composable
fun PreviewSwitch() {
    var toggled by remember { mutableStateOf(true) }
    ShaderPreviewContent() {
//        Box(
//            contentAlignment = Alignment.Center,
//            modifier = Modifier
//                .size(500.dp)
//                .hazeEffect(
//                    state = it,
//                    HazeStyle(
//                        tint = HazeTint(Color.Black.copy(.4f)),
//                        blurRadius = 50.dp,
//                        noiseFactor = .1f,
//                    )
//                )
//        ) {
//        }
        DemoSwitch(it, toggled, { toggled = it })
    }
}