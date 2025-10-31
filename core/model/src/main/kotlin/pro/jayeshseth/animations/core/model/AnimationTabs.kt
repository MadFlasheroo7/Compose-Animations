package pro.jayeshseth.animations.core.model

import androidx.annotation.DrawableRes

sealed class AnimationTabs(@DrawableRes val icon: Int, val title: String) {
    data object Settings : AnimationTabs(0, "Settings")
    data object Code : AnimationTabs(0, "Code")
    data object Source : AnimationTabs(0, "Source")
}

fun animationTabsList(): List<AnimationTabs> = listOf(
    AnimationTabs.Settings,
    AnimationTabs.Code,
    AnimationTabs.Source
)