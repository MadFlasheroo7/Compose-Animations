package pro.jayeshseth.animations.core.navigation

import androidx.compose.runtime.Stable

/**
 * Represents a screen in the navigation graph, specifically for an animation demonstration.
 * This data class holds the necessary information to display a screen in a list and navigate to it.
 *
 * @property title The human-readable title of the screen, displayed in the UI (e.g., in a list of animations).
 * @property route The [Route] object that defines the navigation destination for this screen.
 * @property flip A boolean flag used for visual effects. Defaults to false.
 */
@Stable
data class AnimationScreen(
    val title: String,
    val route: Route,
    val flip: Boolean = false
)