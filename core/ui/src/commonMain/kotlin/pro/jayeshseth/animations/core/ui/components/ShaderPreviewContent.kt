package pro.jayeshseth.animations.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mikepenz.hypnoticcanvas.shaderBackground
import com.mikepenz.hypnoticcanvas.shaders.InkFlow
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState

@Composable
fun ShaderPreviewContent(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable (hazeState: HazeState) -> Unit,
) {
    val hazeState = rememberHazeState()
    Box(
        contentAlignment = contentAlignment,
        modifier = modifier
            .fillMaxSize()
    ) {
        Box(
            Modifier
                .fillMaxSize()
                .hazeSource(state = hazeState)
                .shaderBackground(InkFlow, speed = 0.2f)

        )
        content(hazeState)
    }
}