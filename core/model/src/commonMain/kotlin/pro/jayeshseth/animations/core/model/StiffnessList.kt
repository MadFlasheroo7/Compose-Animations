package pro.jayeshseth.animations.core.model

import androidx.compose.animation.core.Spring

/**
 * Represents a stiffness level option for a spring animation.
 *
 * This data class is used to associate a predefined float value for stiffness,
 * typically from [androidx.compose.animation.core.Spring.Stiffness], with a
 * user-friendly, human-readable name.
 *
 * @param stiffness The float value representing the stiffness level.
 * @param name The display name for the stiffness level (e.g., "Low", "Medium", "High").
 */
data class StiffnessOption(
    val stiffness: Float,
    val name: String
)

/**
 * A mutable list of predefined stiffness options for spring animations.
 * Each option pairs a stiffness value from [androidx.compose.animation.core.Spring]
 * with a human-readable name. This list is used to populate UI elements
 * that allow users to select a desired stiffness level.
 */
val StiffnessList = mutableListOf(
    StiffnessOption(
        Spring.StiffnessVeryLow,
        "Very Low"
    ),
    StiffnessOption(
        Spring.StiffnessLow,
        "Low"
    ),
    StiffnessOption(
        Spring.StiffnessMedium,
        "Medium"
    ),
    StiffnessOption(
        Spring.StiffnessMediumLow,
        "Medium Low"
    ),
    StiffnessOption(
        Spring.StiffnessHigh,
        "High"
    ),
)
