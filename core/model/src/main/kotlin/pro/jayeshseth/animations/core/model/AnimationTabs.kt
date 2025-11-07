package pro.jayeshseth.animations.core.model

import androidx.annotation.DrawableRes

sealed class AnimationTabs(@DrawableRes val icon: Int, val title: String) {
    data object Settings : AnimationTabs(R.drawable.faders, "Settings")
    data object Code : AnimationTabs(R.drawable.code, "Code")
    data object Source : AnimationTabs(R.drawable.link, "Source")
}

fun animationTabsList(): List<AnimationTabs> = listOf(
    AnimationTabs.Settings,
    AnimationTabs.Code,
    AnimationTabs.Source
)