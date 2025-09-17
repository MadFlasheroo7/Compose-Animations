package pro.jayeshseth.animations.util

import androidx.compose.animation.core.Spring

data class DampingRatioOption(
    val dampingRatio: Float,
    val name: String
)

val DampingRatioList by lazy {
    mutableListOf(
        DampingRatioOption(Spring.DampingRatioHighBouncy, "High Bouncy"),
        DampingRatioOption(Spring.DampingRatioMediumBouncy, "Medium Bouncy"),
        DampingRatioOption(Spring.DampingRatioLowBouncy, "Low Bouncy"),
        DampingRatioOption(Spring.DampingRatioNoBouncy, "No Bouncy"),
    )
}
