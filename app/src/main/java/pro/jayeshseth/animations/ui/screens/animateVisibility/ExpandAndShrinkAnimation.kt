package pro.jayeshseth.animations.ui.screens.animateVisibility

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandIn
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.runtime.Composable
import pro.jayeshseth.animations.ui.composables.CatImage
import pro.jayeshseth.animations.util.DURATION

@Composable
fun ExpandAndShrink(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = expandIn(animationSpec = tween(DURATION, easing = LinearEasing)),
        exit = shrinkOut(animationSpec = tween(DURATION, easing = LinearEasing))
    ) {
        CatImage()
    }
}

@Composable
fun ExpandAndShrinkHorizontally(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = expandHorizontally(animationSpec = tween(DURATION, easing = LinearEasing)),
        exit = shrinkHorizontally(animationSpec = tween(DURATION, easing = LinearEasing))
    ) {
        CatImage()
    }
}

@Composable
fun ExpandAndShrinkVertically(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = expandVertically(animationSpec = tween(DURATION, easing = LinearEasing)),
        exit = shrinkVertically(animationSpec = tween(DURATION, easing = LinearEasing))
    ) {
        CatImage()
    }
}