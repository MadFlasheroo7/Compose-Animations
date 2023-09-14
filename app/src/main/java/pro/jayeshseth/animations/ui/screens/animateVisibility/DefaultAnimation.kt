package pro.jayeshseth.animations.ui.screens.animateVisibility

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import pro.jayeshseth.animations.ui.composables.CatImage

@Composable
fun DefaultAnimation(isVisible: Boolean) {
    AnimatedVisibility(visible = isVisible) {
        CatImage()
    }
}