package pro.jayeshseth.animations.core.model

import androidx.compose.runtime.Composable

const val DURATION = 1000

data class AnimationItem(
    val title: String? = null,
    val source: String,
    val content: @Composable (trigger: Boolean) -> Unit
)