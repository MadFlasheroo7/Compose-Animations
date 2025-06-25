package pro.jayeshseth.animations.util

import androidx.compose.runtime.Stable

@Stable
data class InterstellarSpaceState(
//    val iterations: Int = 17,
    val formuparam: Float = 0.53f,
//    val volSteps: Int = 20,
    val stepsize: Float = 0.1f,
    val zoom: Float = 0.800f,
    val tile: Float = 0.850f,
    val speed: Float = 0.010f,
    val brightness: Float = 0.0015f,
    val darkmatter: Float = 0.300f,
    val distFading: Float = 0.730f,
    val saturation: Float = 0.850f
)