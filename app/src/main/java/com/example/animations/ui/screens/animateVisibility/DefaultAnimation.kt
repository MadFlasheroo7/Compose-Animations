package com.example.animations.ui.screens.animateVisibility

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.runtime.Composable
import com.example.animations.ui.composables.CatImage

@Composable
fun DefaultAnimation(isVisible: Boolean) {
    AnimatedVisibility(visible = isVisible) {
        CatImage()
    }
}