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
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing

val EasingList = mutableListOf(
    Pair(LinearEasing, "LinearEasing"),
    Pair(FastOutSlowInEasing, "FastOutSlowInEasing"),
    Pair(FastOutLinearInEasing, "FastOutLinearInEasing"),
    Pair(LinearOutSlowInEasing, "LinearOutSlowInEasing"),
    Pair(Ease, "Ease"),
    Pair(EaseIn, "EaseIn"),
    Pair(EaseOut, "EaseOut"),
    Pair(EaseInSine, "EaseInSine"),
    Pair(EaseOutSine, "EaseOutSine"),
    Pair(EaseInOutSine, "EaseInOutSine"),
    Pair(EaseInCubic, "EaseInCubic"),
    Pair(EaseInOutCubic, "EaseInOutCubic"),
    Pair(EaseInQuint, "EaseInQuint"),
    Pair(EaseOutQuint, "EaseOutQuint"),
    Pair(EaseInOutQuint, "EaseInOutQuint"),
    Pair(EaseInCirc, "EaseInCirc"),
    Pair(EaseOutCirc, "EaseOutCirc"),
    Pair(EaseInOutCirc, "EaseInOutCirc"),
    Pair(EaseInQuad, "EaseInQuad"),
    Pair(EaseOutQuad, "EaseOutQuad"),
    Pair(EaseInOutQuad, "EaseInOutQuad"),
    Pair(EaseInQuart, "EaseInQuart"),
    Pair(EaseOutQuart, "EaseOutQuart"),
    Pair(EaseInOutQuart, "EaseInOutQuart"),
    Pair(EaseInExpo, "EaseInExpo"),
    Pair(EaseOutExpo, "EaseOutExpo"),
    Pair(EaseInOutExpo, "EaseInOutExpo"),
    Pair(EaseInBack, "EaseInBack"),
    Pair(EaseOutBack, "EaseOutBack"),
    Pair(EaseInOutBack, "EaseInOutBack"),
    Pair(EaseInElastic, "EaseInElastic"),
    Pair(EaseOutElastic, "EaseOutElastic"),
    Pair(EaseInOutElastic, "EaseInOutElastic"),
    Pair(EaseInBounce, "EaseInBounce"),
    Pair(EaseOutBounce, "EaseOutBounce"),
    Pair(EaseInOutBounce, "EaseInOutBounce")
)