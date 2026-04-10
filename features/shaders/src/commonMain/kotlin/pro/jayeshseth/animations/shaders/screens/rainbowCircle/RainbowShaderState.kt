package pro.jayeshseth.animations.shaders.screens.rainbowCircle

import androidx.compose.runtime.Stable

@Stable
data class RainbowCircleState(
    val reduceDots: Boolean = true,
    val size: Float = 2f,
    val red: Float = 0f,
    val blue: Float = 4.0f,
    val green: Float = 2.0f,
    val alpha: Float = 1.0f,
    val speed: Float = 0.05f,
    val layers: Float = 100.0f,
    val brightness: Float = 30000.0f,
    val pattern: Float = 5.3f,
    val expand: Float = 1.0f
)
