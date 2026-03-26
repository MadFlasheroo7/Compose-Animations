package pro.jayeshseth.animations.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.rememberHazeState
import pro.jayeshseth.animations.core.model.BackgroundType
import pro.jayeshseth.animations.core.ui.backgrounds.BackgroundRegistrar
import pro.jayeshseth.animations.core.ui.backgrounds.shaders.InkFlowShader

/**
 * A composable that provides a preview container with a dynamic background
 * and a haze effect. It's designed to showcase other composables on top of an
 * animated, blurred background.
 *
 * @param modifier The modifier to be applied to the root container.
 * @param backgroundType The type of background to render. Defaults to the InkFlow shader.
 * @param contentAlignment The alignment of the content within the container.
 * @param content A lambda that receives a `HazeState` and defines the composable content.
 */
@Composable
fun ShaderPreviewContent(
    modifier: Modifier = Modifier,
    backgroundType: BackgroundType = InkFlowShader(),
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable (hazeState: HazeState) -> Unit,
) {
    BackgroundRegistrar.register()
    val hazeState = rememberHazeState()
    Box(
        contentAlignment = contentAlignment,
        modifier = modifier
            .fillMaxSize()
    ) {
        BackgroundRenderer(
            backgroundType = backgroundType,
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(state = hazeState)
        )
        content(hazeState)
    }
}
