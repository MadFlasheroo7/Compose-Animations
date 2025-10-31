package pro.jayeshseth.animations.ui.defaultApis.animateVisibility

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import pro.jayeshseth.animations.core.model.DURATION
import pro.jayeshseth.animations.ui.composables.CatImage

@Composable
fun Fade(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            animationSpec = tween(DURATION, easing = LinearEasing)
        ),
        exit = fadeOut(
            animationSpec = tween(DURATION, easing = LinearEasing)
        )
    ) {
        CatImage()
    }
}