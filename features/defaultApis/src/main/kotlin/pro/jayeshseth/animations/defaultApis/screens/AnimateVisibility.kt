package pro.jayeshseth.animations.defaultApis.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pro.jayeshseth.animations.core.model.AnimationContent
import pro.jayeshseth.animations.core.model.AnimationItem
import pro.jayeshseth.animations.core.model.DURATION
import pro.jayeshseth.animations.core.model.OnClickLink
import pro.jayeshseth.animations.core.ui.components.AnimationCard
import pro.jayeshseth.animations.defaultApis.animations.animateVisibility.DefaultAnimation
import pro.jayeshseth.animations.defaultApis.animations.animateVisibility.ExpandAndShrink
import pro.jayeshseth.animations.defaultApis.animations.animateVisibility.ExpandAndShrinkHorizontally
import pro.jayeshseth.animations.defaultApis.animations.animateVisibility.ExpandAndShrinkVertically
import pro.jayeshseth.animations.defaultApis.animations.animateVisibility.Fade
import pro.jayeshseth.animations.defaultApis.animations.animateVisibility.HungryCat
import pro.jayeshseth.animations.defaultApis.animations.animateVisibility.RandomSlide
import pro.jayeshseth.animations.defaultApis.animations.animateVisibility.Scale
import pro.jayeshseth.animations.defaultApis.animations.animateVisibility.Slide
import pro.jayeshseth.animations.defaultApis.animations.animateVisibility.SlideInHorizontally
import pro.jayeshseth.animations.defaultApis.animations.animateVisibility.SlideInVertically
import pro.jayeshseth.commoncomponents.StatusBarAwareThemedLazyColumn

@Composable
fun VisibilityAnimation(onClickLink: OnClickLink) {
    StatusBarAwareThemedLazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
            .animateContentSize(
                tween(DURATION, easing = LinearEasing)
            )
    ) {
        item { Spacer(Modifier.statusBarsPadding()) }
        itemsIndexed(animations) { index, animation ->
            AnimationCard(
                index = index,
                onClickLink = { onClickLink(animation.source) },
                animationContent = AnimationContent(
                    title = animation.title,
                    content = {
                        animation.content(it)
                    }
                )
            )
        }
        item { Spacer(Modifier.navigationBarsPadding()) }
    }
}

/**
 * list of visibility animations
 */
private val animations: List<AnimationItem> by lazy {
    mutableListOf(
        AnimationItem(
            title = "Default Animation",
            source = "defaultApis/animateVisibility/DefaultAnimation.kt"
        ) { isVisible ->
            DefaultAnimation(isVisible)
        },
        AnimationItem(
            title = "Fade In & Out",
            source = "defaultApis/animateVisibility/FadeAnimation.kt"
        ) { isVisible ->
            Fade(isVisible)
        },
        AnimationItem(
            title = "Scale",
            source = "defaultApis/animateVisibility/ScaleAnimation.kt"
        ) { isVisible ->
            Scale(isVisible)
        },
        AnimationItem(
            title = "Slide In & Out",
            source = "defaultApis/animateVisibility/SlideAnimation.kt"
        ) { isVisible ->
            Slide(isVisible)
        },
        AnimationItem(
            title = "Slide In & Out Horizontally",
            source = "defaultApis/animateVisibility/SlideAnimation.kt"
        ) { isVisible ->
            SlideInHorizontally(isVisible)
        },
        AnimationItem(
            title = "Slide In & Out Vertically",
            source = "defaultApis/animateVisibility/SlideAnimation.kt"
        ) { isVisible ->
            SlideInVertically(isVisible)
        },
        AnimationItem(
            title = "Expand In & Shrink Out",
            source = "defaultApis/animateVisibility/ExpandAndShrinkAnimation.kt"
        ) { isVisible ->
            ExpandAndShrink(isVisible)
        },
        AnimationItem(
            title = "Expand In & Shrink Out Horizontally",
            source = "defaultApis/animateVisibility/ExpandAndShrinkAnimation.kt"
        ) { isVisible ->
            ExpandAndShrinkHorizontally(isVisible)
        },
        AnimationItem(
            title = "Expand In & Shrink Out Vertically",
            source = "defaultApis/animateVisibility/ExpandAndShrinkAnimation.kt"
        ) { isVisible ->
            ExpandAndShrinkVertically(isVisible)
        },
        AnimationItem(
            title = "Random Slide",
            source = "defaultApis/animateVisibility/SlideAnimation.kt"
        ) { isVisible ->
            RandomSlide(isVisible)
        },
        AnimationItem(source = "defaultApis/animateVisibility/HungryCat.kt") { HungryCat() }
    )
}