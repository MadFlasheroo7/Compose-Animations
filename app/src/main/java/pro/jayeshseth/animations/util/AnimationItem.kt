package pro.jayeshseth.animations.util

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import pro.jayeshseth.animations.ui.screens.AnimationCard
import pro.jayeshseth.animations.ui.animations.animateVisibility.DefaultAnimation
import pro.jayeshseth.animations.ui.animations.animateVisibility.ExpandAndShrink
import pro.jayeshseth.animations.ui.animations.animateVisibility.ExpandAndShrinkHorizontally
import pro.jayeshseth.animations.ui.animations.animateVisibility.ExpandAndShrinkVertically
import pro.jayeshseth.animations.ui.animations.animateVisibility.Fade
import pro.jayeshseth.animations.ui.animations.animateVisibility.HungryCat
import pro.jayeshseth.animations.ui.animations.animateVisibility.RandomSlide
import pro.jayeshseth.animations.ui.animations.animateVisibility.Scale
import pro.jayeshseth.animations.ui.animations.animateVisibility.Slide
import pro.jayeshseth.animations.ui.animations.animateVisibility.SlideInHorizontally
import pro.jayeshseth.animations.ui.animations.animateVisibility.SlideInVertically

const val DURATION = 1000

data class AnimationItem(
    val title: String? = null,
    val source: String,
    val content: @Composable (isVisible: Boolean) -> Unit
)


//@Composable
//fun ldd(modifier: Modifier = Modifier) {
//    LazyColumn {
//        items(animations) { animation->
//            AnimationCard(
//
//                animationScreen = AnimationScreen(
//                    title = animation.title,
//                    content = {
//                        animation.content(it)
//                    }
//                )
//            )
//        }
//    }
//}