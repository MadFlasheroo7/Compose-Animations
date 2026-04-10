package pro.jayeshseth.animations.core.model

import androidx.compose.runtime.Composable

const val DURATION = 1000

/**
 * Represents a single animation item to be displayed in the list.
 *
 * This data class holds all the necessary information for a single animation showcase,
 * including its title, a reference to its source code, and the Composable content
 * that renders the animation.
 *
 * @param title An optional title for the animation. Displayed as a header for the item.
 * @param source Link to the source code of the animation
 * @param content A Composable lambda function that renders the animation. It accepts a `trigger`
 *                Boolean which can be used to start or reset the animation state.
 */
data class AnimationItem(
    val title: String? = null,
    val source: String,
    val content: @Composable (trigger: Boolean) -> Unit
)