package pro.jayeshseth.animations.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pro.jayeshseth.animations.ui.screens.animateVisibility.DefaultAnimation
import pro.jayeshseth.animations.ui.screens.animateVisibility.ExpandAndShrink
import pro.jayeshseth.animations.ui.screens.animateVisibility.ExpandAndShrinkHorizontally
import pro.jayeshseth.animations.ui.screens.animateVisibility.ExpandAndShrinkVertically
import pro.jayeshseth.animations.ui.screens.animateVisibility.Fade
import pro.jayeshseth.animations.ui.screens.animateVisibility.HungryCat
import pro.jayeshseth.animations.ui.screens.animateVisibility.RandomSlide
import pro.jayeshseth.animations.ui.screens.animateVisibility.Scale
import pro.jayeshseth.animations.ui.screens.animateVisibility.Slide
import pro.jayeshseth.animations.ui.screens.animateVisibility.SlideInHorizontally
import pro.jayeshseth.animations.ui.screens.animateVisibility.SlideInVertically
import pro.jayeshseth.animations.util.AnimationScreen
import pro.jayeshseth.animations.util.DURATION


@Composable
fun VisibilityAnimation() {
    LazyColumn {
        item {
            Column(
                verticalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier
                    .systemBarsPadding()
                    .padding(horizontal = 20.dp)
                    .animateContentSize(
                        tween(DURATION, easing = LinearEasing)
                    )
            ) {
                AnimationCard(
                    animationScreen = AnimationScreen(title = "Default Animation") {
                        DefaultAnimation(isVisible = it)
                    }
                )
                AnimationCard(
                    animationScreen = AnimationScreen(title = "Fade In & Out") {
                        Fade(isVisible = it)
                    }
                )
                AnimationCard(
                    animationScreen = AnimationScreen(title = "Scale") {
                        Scale(isVisible = it)
                    }
                )
                AnimationCard(
                    animationScreen = AnimationScreen(title = "Slide In & Out Vertically") {
                        SlideInVertically(isVisible = it)
                    }
                )
                AnimationCard(
                    animationScreen = AnimationScreen(title = "Slide In & Out") {
                        Slide(isVisible = it)
                    }
                )
                AnimationCard(
                    animationScreen = AnimationScreen(title = "Slide In & Out Horizontally") {
                        SlideInHorizontally(isVisible = it)
                    }
                )
                AnimationCard(
                    animationScreen = AnimationScreen(title = "Expand In & Shrink Out") {
                        ExpandAndShrink(isVisible = it)
                    }
                )
                AnimationCard(
                    animationScreen = AnimationScreen(title = "Expand In & Shrink Out Horizontally") {
                        ExpandAndShrinkHorizontally(isVisible = it)
                    }
                )
                AnimationCard(
                    animationScreen = AnimationScreen(title = "Expand In & Shrink Out Vertically") {
                        ExpandAndShrinkVertically(isVisible = it)
                    }
                )
                AnimationCard(
                    animationScreen = AnimationScreen(title = "Random Slide") {
                        RandomSlide(isVisible = it)
                    }
                )
                HungryCat()
            }
        }
    }
}