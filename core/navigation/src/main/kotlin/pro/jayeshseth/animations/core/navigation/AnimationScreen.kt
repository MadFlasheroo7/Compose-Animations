package pro.jayeshseth.animations.core.navigation

import androidx.compose.runtime.Stable

@Stable
data class AnimationScreen(
    val title: String,
    val route: Route,
    val flip: Boolean = false
)