package pro.jayeshseth.animations.core.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pro.jayeshseth.animations.core.model.BackgroundType

/**
 * Renders the given [backgroundType] as a full-bleed background composable.
 *
 * Delegates to the [BackgroundType]'s own [BackgroundType.Shader.Content],
 * [BackgroundType.Canvas.Content], or [BackgroundType.Image.Content] method,
 * so each background type is fully self-rendering with no casting required.
 *
 * @param backgroundType The active background configuration to render.
 * @param modifier Modifier applied to the background root (typically [Modifier.fillMaxSize]).
 */
@Composable
fun BackgroundRenderer(
    backgroundType: BackgroundType,
    modifier: Modifier = Modifier
) {
    when (backgroundType) {
        is BackgroundType.Shader -> backgroundType.Content(modifier)
        is BackgroundType.Canvas -> backgroundType.Content(modifier)
        is BackgroundType.Image -> backgroundType.Content(modifier)
    }
}
