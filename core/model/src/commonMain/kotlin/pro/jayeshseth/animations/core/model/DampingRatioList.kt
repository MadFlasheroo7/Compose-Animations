package pro.jayeshseth.animations.core.model

import androidx.compose.animation.core.Spring

/**
 * Represents a selectable option for a spring animation's damping ratio.
 * This class couples a specific damping ratio value with a human-readable name.
 *
 * @property dampingRatio The damping ratio value for the spring animation.
 *                        This value is typically one of the constants provided by [androidx.compose.animation.core.Spring.DampingRatio].
 * @property name A user-friendly name for the damping ratio, e.g., "High Bouncy".
 */
data class DampingRatioOption(
    val dampingRatio: Float,
    val name: String
)

/**
 * A lazily initialized list of predefined damping ratio options for spring animations.
 *
 * This list provides common configurations for the bounciness of a spring animation,
 * mapping [Spring] damping ratio constants to human-readable names. It is intended
 * to be used in UI selectors where users can choose the desired animation behavior.
 *
 * The list includes:
 * - [Spring.DampingRatioHighBouncy]
 * - [Spring.DampingRatioMediumBouncy]
 * - [Spring.DampingRatioLowBouncy]
 * - [Spring.DampingRatioNoBouncy]
 *
 * @see DampingRatioOption
 * @see androidx.compose.animation.core.Spring
 */
val DampingRatioList by lazy {
    mutableListOf(
        DampingRatioOption(
            Spring.DampingRatioHighBouncy,
            "High Bouncy"
        ),
        DampingRatioOption(
            Spring.DampingRatioMediumBouncy,
            "Medium Bouncy"
        ),
        DampingRatioOption(
            Spring.DampingRatioLowBouncy,
            "Low Bouncy"
        ),
        DampingRatioOption(
            Spring.DampingRatioNoBouncy,
            "No Bouncy"
        ),
    )
}
