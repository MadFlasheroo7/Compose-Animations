package pro.jayeshseth.animations.ui.defaultApis.animateVisibility

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import pro.jayeshseth.animations.ui.composables.CatImage

/**
 * Default Visibility Animation it uses
 *
 * **enter transition** - ```fadeIn() + expandIn()```
 *
 * **exit transition** - ```shrinkOut() + fadeOut()```
 */
@Composable
fun DefaultAnimation(isVisible: Boolean) {
    AnimatedVisibility(visible = isVisible) {
        CatImage()
    }
}