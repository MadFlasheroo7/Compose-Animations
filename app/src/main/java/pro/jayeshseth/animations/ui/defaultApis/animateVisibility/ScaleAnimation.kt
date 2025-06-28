package pro.jayeshseth.animations.ui.defaultApis.animateVisibility

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import pro.jayeshseth.animations.ui.composables.CatImage
import pro.jayeshseth.animations.util.DURATION

@Composable
fun Scale(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = scaleIn(animationSpec = tween(DURATION, easing = LinearEasing)),
        exit = scaleOut(animationSpec = tween(DURATION, easing = LinearEasing))
    ) {
        CatImage()
    }
}