package pro.jayeshseth.animations.util

import androidx.compose.runtime.Composable

data class AnimationContent(
    val title: String? = null,
    val content: @Composable (isVisible: Boolean) -> Unit
)
