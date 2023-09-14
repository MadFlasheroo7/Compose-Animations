package pro.jayeshseth.animations.util

import androidx.compose.runtime.Composable
import pro.jayeshseth.animations.ui.screens.animateVisibility.DefaultAnimation
import pro.jayeshseth.animations.ui.screens.animateVisibility.ExpandAndShrink
import pro.jayeshseth.animations.ui.screens.animateVisibility.ExpandAndShrinkHorizontally
import pro.jayeshseth.animations.ui.screens.animateVisibility.ExpandAndShrinkVertically
import pro.jayeshseth.animations.ui.screens.animateVisibility.Fade
import pro.jayeshseth.animations.ui.screens.animateVisibility.RandomSlide
import pro.jayeshseth.animations.ui.screens.animateVisibility.Scale
import pro.jayeshseth.animations.ui.screens.animateVisibility.Slide
import pro.jayeshseth.animations.ui.screens.animateVisibility.SlideInHorizontally
import pro.jayeshseth.animations.ui.screens.animateVisibility.SlideInVertically

const val DURATION = 1000

data class AnimationItem(
    val title: String,
    val content: @Composable (isVisible: Boolean) -> Unit
)

/**
 * Alternate Way Of Populating Animations
 */
private val animations: List<AnimationItem> by lazy {
    mutableListOf(
        AnimationItem(
            title = "Default Animation",
        ) { isVisible ->
            DefaultAnimation(isVisible)
        },
        AnimationItem(
            title = "Fade In & Out",
        ) { isVisible ->
            Fade(isVisible)
        },
        AnimationItem(
            title = "Scale",
        ) { isVisible ->
            Scale(isVisible)
        },
        AnimationItem(
            title = "Slide In & Out",
        ) { isVisible ->
            Slide(isVisible)
        },
        AnimationItem(
            title = "Slide In & Out Horizontally",
        ) { isVisible ->
            SlideInHorizontally(isVisible)
        },
        AnimationItem(
            title = "Slide In & Out Vertically",
        ) { isVisible ->
            SlideInVertically(isVisible)
        },
        AnimationItem(
            title = "Expand In & Shrink Out",
        ) { isVisible ->
            ExpandAndShrink(isVisible)
        },
        AnimationItem(
            title = "Expand In & Shrink Out Horizontally",
        ) { isVisible ->
            ExpandAndShrinkHorizontally(isVisible)
        },
        AnimationItem(
            title = "Expand In & Shrink Out Vertically",
        ) { isVisible ->
            ExpandAndShrinkVertically(isVisible)
        },
        AnimationItem(
            title = "Random Slide",
        ) { isVisible ->
            RandomSlide(isVisible)
        },
    )
}