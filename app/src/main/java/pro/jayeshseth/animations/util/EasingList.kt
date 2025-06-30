package pro.jayeshseth.animations.util

import androidx.compose.animation.core.Ease
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseInBack
import androidx.compose.animation.core.EaseInBounce
import androidx.compose.animation.core.EaseInCirc
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.EaseInElastic
import androidx.compose.animation.core.EaseInExpo
import androidx.compose.animation.core.EaseInOutBack
import androidx.compose.animation.core.EaseInOutBounce
import androidx.compose.animation.core.EaseInOutCirc
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.EaseInOutElastic
import androidx.compose.animation.core.EaseInOutExpo
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.EaseInOutQuart
import androidx.compose.animation.core.EaseInOutQuint
import androidx.compose.animation.core.EaseInOutSine
import androidx.compose.animation.core.EaseInQuad
import androidx.compose.animation.core.EaseInQuart
import androidx.compose.animation.core.EaseInQuint
import androidx.compose.animation.core.EaseInSine
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.EaseOutBack
import androidx.compose.animation.core.EaseOutBounce
import androidx.compose.animation.core.EaseOutCirc
import androidx.compose.animation.core.EaseOutElastic
import androidx.compose.animation.core.EaseOutExpo
import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.EaseOutQuart
import androidx.compose.animation.core.EaseOutQuint
import androidx.compose.animation.core.EaseOutSine
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing

data class EasingOption(
    val easing: Easing,
    val name: String
)

val EasingList = mutableListOf(
    EasingOption(LinearEasing, "LinearEasing"),
    EasingOption(FastOutSlowInEasing, "FastOutSlowInEasing"),
    EasingOption(FastOutLinearInEasing, "FastOutLinearInEasing"),
    EasingOption(LinearOutSlowInEasing, "LinearOutSlowInEasing"),
    EasingOption(Ease, "Ease"),
    EasingOption(EaseIn, "EaseIn"),
    EasingOption(EaseOut, "EaseOut"),
    EasingOption(EaseInSine, "EaseInSine"),
    EasingOption(EaseOutSine, "EaseOutSine"),
    EasingOption(EaseInOutSine, "EaseInOutSine"),
    EasingOption(EaseInCubic, "EaseInCubic"),
    EasingOption(EaseInOutCubic, "EaseInOutCubic"),
    EasingOption(EaseInQuint, "EaseInQuint"),
    EasingOption(EaseOutQuint, "EaseOutQuint"),
    EasingOption(EaseInOutQuint, "EaseInOutQuint"),
    EasingOption(EaseInCirc, "EaseInCirc"),
    EasingOption(EaseOutCirc, "EaseOutCirc"),
    EasingOption(EaseInOutCirc, "EaseInOutCirc"),
    EasingOption(EaseInQuad, "EaseInQuad"),
    EasingOption(EaseOutQuad, "EaseOutQuad"),
    EasingOption(EaseInOutQuad, "EaseInOutQuad"),
    EasingOption(EaseInQuart, "EaseInQuart"),
    EasingOption(EaseOutQuart, "EaseOutQuart"),
    EasingOption(EaseInOutQuart, "EaseInOutQuart"),
    EasingOption(EaseInExpo, "EaseInExpo"),
    EasingOption(EaseOutExpo, "EaseOutExpo"),
    EasingOption(EaseInOutExpo, "EaseInOutExpo"),
    EasingOption(EaseInBack, "EaseInBack"),
    EasingOption(EaseOutBack, "EaseOutBack"),
    EasingOption(EaseInOutBack, "EaseInOutBack"),
    EasingOption(EaseInElastic, "EaseInElastic"),
    EasingOption(EaseOutElastic, "EaseOutElastic"),
    EasingOption(EaseInOutElastic, "EaseInOutElastic"),
    EasingOption(EaseInBounce, "EaseInBounce"),
    EasingOption(EaseOutBounce, "EaseOutBounce"),
    EasingOption(EaseInOutBounce, "EaseInOutBounce")
)