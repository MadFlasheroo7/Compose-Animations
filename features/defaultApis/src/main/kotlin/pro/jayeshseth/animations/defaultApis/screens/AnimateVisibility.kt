package pro.jayeshseth.animations.defaultApis.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.HazeState
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
import pro.jayeshseth.animations.defaultApis.utils.BASE_FEATURE_ROUTE
import pro.jayeshseth.commoncomponents.StatusBarAwareThemedLazyColumn

@Composable
fun VisibilityAnimations(
    hazeState: HazeState,
    onClickLink: OnClickLink) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(horizontal = 20.dp),
        modifier = Modifier
            .fillMaxSize()
            .animateContentSize(
                tween(DURATION, easing = LinearEasing)
            )
    ) {
        item { Spacer(Modifier.statusBarsPadding()) }
        itemsIndexed(animations(
            hazeState
        )) { index, animation ->
            AnimationCard(
                index = index,
                hazeState = hazeState,
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
private fun animations(hazeState: HazeState): List<AnimationItem> {
    return mutableListOf(
        AnimationItem(
            title = "Default Animation",
            source = "${BASE_FEATURE_ROUTE}/animations/animateVisibility/DefaultAnimation.kt"
        ) { isVisible ->
            DefaultAnimation(isVisible)
        },
        AnimationItem(
            title = "Fade In & Out",
            source = "${BASE_FEATURE_ROUTE}/animations/animateVisibility/FadeAnimation.kt"
        ) { isVisible ->
            Fade(isVisible)
        },
        AnimationItem(
            title = "Scale",
            source = "${BASE_FEATURE_ROUTE}/animations/animateVisibility/ScaleAnimation.kt"
        ) { isVisible ->
            Scale(isVisible)
        },
        AnimationItem(
            title = "Slide In & Out",
            source = "${BASE_FEATURE_ROUTE}/animations/animateVisibility/SlideAnimation.kt"
        ) { isVisible ->
            Slide(isVisible)
        },
        AnimationItem(
            title = "Slide In & Out Horizontally",
            source = "${BASE_FEATURE_ROUTE}/animations/animateVisibility/SlideAnimation.kt"
        ) { isVisible ->
            SlideInHorizontally(isVisible)
        },
        AnimationItem(
            title = "Slide In & Out Vertically",
            source = "${BASE_FEATURE_ROUTE}/animations/animateVisibility/SlideAnimation.kt"
        ) { isVisible ->
            SlideInVertically(isVisible)
        },
        AnimationItem(
            title = "Expand In & Shrink Out",
            source = "${BASE_FEATURE_ROUTE}/animations/animateVisibility/ExpandAndShrinkAnimation.kt"
        ) { isVisible ->
            ExpandAndShrink(isVisible)
        },
        AnimationItem(
            title = "Expand In & Shrink Out Horizontally",
            source = "${BASE_FEATURE_ROUTE}/animations/animateVisibility/ExpandAndShrinkAnimation.kt"
        ) { isVisible ->
            ExpandAndShrinkHorizontally(isVisible)
        },
        AnimationItem(
            title = "Expand In & Shrink Out Vertically",
            source = "${BASE_FEATURE_ROUTE}/animations/animateVisibility/ExpandAndShrinkAnimation.kt"
        ) { isVisible ->
            ExpandAndShrinkVertically(isVisible)
        },
        AnimationItem(
            title = "Random Slide",
            source = "${BASE_FEATURE_ROUTE}/animations/animateVisibility/SlideAnimation.kt"
        ) { isVisible ->
            RandomSlide(isVisible)
        },
        AnimationItem(source = "${BASE_FEATURE_ROUTE}/animations/animateVisibility/HungryCat.kt") { HungryCat(hazeState) }
    )
}