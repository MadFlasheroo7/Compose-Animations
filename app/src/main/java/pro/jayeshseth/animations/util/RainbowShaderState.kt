package pro.jayeshseth.animations.util

import androidx.compose.runtime.Stable

@Stable
data class RainbowCircleState(
    val size: Float,
    val red: Float,
    val blue: Float,
    val green: Float,
    val alpha: Float,
    val speed: Float,
    val layers: Float,
    val brightness: Float,
    val pattern: Float,
    val expand: Float
)
