package com.example.animations.ui.screens.animateVisibility

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import com.example.animations.ui.composables.CatImage
import com.example.animations.util.DURATION

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Scale(isVisible: Boolean) {
    AnimatedVisibility(
        visible = isVisible,
        enter = scaleIn(animationSpec = tween(DURATION, easing = LinearEasing)),
        exit = scaleOut(animationSpec = tween(DURATION, easing = LinearEasing))
    ) {
        CatImage()
    }
}