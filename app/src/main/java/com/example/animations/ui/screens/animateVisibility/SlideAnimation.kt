package com.example.animations.ui.screens.animateVisibility

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntOffset
import com.example.animations.ui.composables.CatImage
import com.example.animations.util.DURATION

@Composable
fun Slide(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideIn(animationSpec = tween(DURATION, easing = LinearEasing)) {
            IntOffset(it.width / 4, 100)
        },
        exit = slideOut(animationSpec = tween(DURATION, easing = LinearEasing)) {
            IntOffset(-180, 50)
        }
    ) {
        CatImage()
    }
}

@Composable
fun SlideInHorizontally(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(animationSpec = tween(DURATION, easing = LinearEasing)) {
            -it
        },
        exit = slideOutHorizontally(animationSpec = tween(DURATION, easing = LinearEasing)) {
            it
        }
    ) {
        CatImage()
    }
}

@Composable
fun SlideInVertically(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInVertically(animationSpec = tween(DURATION, easing = LinearEasing)) {
            -it / 3
        },
        exit = slideOutVertically(animationSpec = tween(DURATION, easing = LinearEasing)) {
            it
        }
    ) {
        CatImage()
    }
}

@Composable
fun RandomSlide(isVisible: Boolean) {
    val randX = (-9999..9999).random()
    val randY = (-9999..9999).random()

    AnimatedVisibility(
        visible = isVisible,
        enter = slideIn(animationSpec = tween(DURATION, easing = LinearEasing)) {
            IntOffset(randX, randY)
        } + fadeIn(),
        exit = slideOut(animationSpec = tween(DURATION, easing = LinearEasing)) {
            IntOffset(randX, randY)
        } + fadeOut()
    ) {
        CatImage()
    }
}