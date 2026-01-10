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

/**
 * A composable that provides a preview container with a dynamic shader background
 * and a haze effect. It's designed to showcase other composables on top of an
 * animated, blurred background.
 *
 * The background uses an `InkFlow` shader for a fluid, animated visual. The haze
 * effect is provided by `HazeState`, which is passed to the content lambda,
 * allowing child composables to apply a corresponding blur.
 *
 * @param modifier The modifier to be applied to the root container.
 * @param contentAlignment The alignment of the content within the container. Defaults to `Alignment.Center`.
 * @param content A lambda that receives a `HazeState` and defines the composable content to be
 *                displayed on top of the shader background. The `HazeState` should be used with the
 *                `.haze()` modifier on the content's child composables to create the blur effect.
 */
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