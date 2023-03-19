package com.example.animations.ui.screens.animateVisibility

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import com.example.animations.ui.composables.CatImage
import com.example.animations.util.DURATION

@Composable
fun Fade(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            animationSpec = tween(DURATION, easing = LinearEasing)
        ),
        exit = fadeOut(
            animationSpec = tween(DURATION, easing = LinearEasing)
        )
    ) {
        CatImage()
    }
}