package pro.jayeshseth.animations.util

import androidx.compose.animation.core.Spring

data class StiffnessOption(
    val stiffness: Float,
    val name: String
)

val StiffnessList = mutableListOf(
    StiffnessOption(Spring.StiffnessVeryLow, "Very Low"),
    StiffnessOption(Spring.StiffnessLow, "Low"),
    StiffnessOption(Spring.StiffnessMedium, "Medium"),
    StiffnessOption(Spring.StiffnessMediumLow, "Medium Low"),
    StiffnessOption(Spring.StiffnessHigh, "High"),
)
