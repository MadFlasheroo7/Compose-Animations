package pro.jayeshseth.animations.masterCustomization.components

import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.chrisbanes.haze.HazeState
import pro.jayeshseth.animations.core.ui.components.ShaderPreviewContent
import pro.jayeshseth.animations.masterCustomization.screens.LocalSharedTransitionScope


@Composable
fun ShaderPreviewContentWithSharedTransitionScope(
    modifier: Modifier = Modifier,
    contentAlignment: Alignment = Alignment.Center,
    content: @Composable (sharedTransitionScope: SharedTransitionScope, hazeState: HazeState) -> Unit,
) {
    SharedTransitionLayout {
        CompositionLocalProvider(
            LocalSharedTransitionScope provides this
        ) {
            ShaderPreviewContent(
                modifier = modifier,
                contentAlignment = contentAlignment,
                content = {
                    content(this@SharedTransitionLayout, it)
                }
            )
        }
    }
}