package pro.jayeshseth.animations.core.model

import animations.core.model.generated.resources.Res
import animations.core.model.generated.resources.code
import animations.core.model.generated.resources.faders
import animations.core.model.generated.resources.link
import org.jetbrains.compose.resources.DrawableResource

/**
 * Represents the different tabs available in the animation detail screen.
 * Each tab has an associated icon and a title.
 *
 * This sealed class is used to define a fixed set of tabs, ensuring type safety
 * and allowing for exhaustive `when` expressions.
 *
 * @property icon The drawable resource for the tab's icon.
 * @property title The string title of the tab.
 */
sealed class AnimationTabs(val icon: DrawableResource, val title: String) {
    data object Settings : AnimationTabs(Res.drawable.faders, "Settings")
    data object Code : AnimationTabs(Res.drawable.code, "Code")
    data object Source : AnimationTabs(Res.drawable.link, "Source")
}

fun animationTabsList(): List<AnimationTabs> = listOf(
    AnimationTabs.Settings,
    AnimationTabs.Code,
    AnimationTabs.Source
)