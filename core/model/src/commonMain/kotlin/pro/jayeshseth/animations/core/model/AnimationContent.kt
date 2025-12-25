package pro.jayeshseth.animations.core.model

import androidx.compose.runtime.Composable

data class AnimationContent(
    val title: String? = null,
    val content: @Composable (trigger: Boolean) -> Unit
)
