package pro.jayeshseth.animations.masterCustomization.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
import org.jetbrains.compose.ui.tooling.preview.Preview
import pro.jayeshseth.animations.core.ui.components.InteractiveButton
import pro.jayeshseth.animations.core.ui.components.PrimaryInteractiveButton
import pro.jayeshseth.animations.core.ui.components.ShaderPreviewContent

@Composable
fun DemoInteractiveButtons(
    hazeState: HazeState,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        modifier = modifier
            .padding(32.dp)
//            .padding(16.dp)
//            .aspectRatio(1f)
    ) {
        PrimaryInteractiveButton(
            hazeState = hazeState,
            text = "Click Me",
            scale = 1f,
            blur = 0f,
            onClick = {

            }
        )
        InteractiveButton(
            hazeState = hazeState,
            text = "Click Me",
            onClick = {

            }
        )
    }
}

@Preview
@Composable
fun PreviewInteractiveButtons() {
    ShaderPreviewContent {
        DemoInteractiveButtons(
            hazeState = it
        )
    }
}
