package pro.jayeshseth.animations.defaultApis.animations.animateVisibility

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import pro.jayeshseth.animations.core.model.DURATION
import pro.jayeshseth.animations.core.ui.components.CatImage

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