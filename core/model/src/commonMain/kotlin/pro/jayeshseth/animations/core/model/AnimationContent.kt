package pro.jayeshseth.animations.core.model

import androidx.compose.runtime.Composable

/**
 * A data class to hold the content for a specific animation demonstration.
 *
 * This class encapsulates the information needed to display a single animation example
 * within a list or a detail view. It includes an optional title and the actual
 * composable content that showcases the animation.
 *
 * @param title An optional title for the animation. This can be used as a header or label
 *              for the animation content. Defaults to null if not provided.
 * @param content A composable lambda function that defines the animation.
 *                It takes a `Boolean` `trigger` parameter, which can be used to
 *                start, stop, or toggle the animation state. This allows the parent
 *                composable to control the animation's execution.
 */
data class AnimationContent(
    val title: String? = null,
    val content: @Composable (trigger: Boolean) -> Unit
)
