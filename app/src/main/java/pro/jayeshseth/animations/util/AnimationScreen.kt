package pro.jayeshseth.animations.util

import androidx.compose.runtime.Composable


data class AnimationScreen(
    val title: String,
    val content: @Composable (isVisible: Boolean) -> Unit
)
