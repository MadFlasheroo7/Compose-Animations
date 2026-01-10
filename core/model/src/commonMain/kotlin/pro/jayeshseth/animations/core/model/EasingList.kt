package pro.jayeshseth.animations.core.model

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

/**
 * Represents a single easing option for animations.
 *
 * This data class pairs an `Easing` function with a human-readable name,
 * making it suitable for use in UI elements like dropdown menus or lists
 * where users can select different animation curves.
 *
 * @property easing The `Easing` object that defines the animation curve.
 * @property name A `String` representing the name of the easing function (e.g., "LinearEasing").
 */
data class EasingOption(
    val easing: Easing,
    val name: String
)

/**
 * A comprehensive list of built-in Jetpack Compose [Easing] functions.
 *
 * Each item in the list is an [EasingOption], which pairs the `Easing` instance with its
 * corresponding string name. This is useful for UI elements like dropdowns or lists
 * where users can select an easing curve to apply to an animation.
 *
 * Includes standard easing types such as Linear, FastOutSlowIn, and various curves
 * like Sine, Cubic, Quint, Circ, Quad, Quart, Expo, Back, Elastic, and Bounce,
 * each with their In, Out, and InOut variations.
 *
 * @see EasingOption
 * @see androidx.compose.animation.core.Easing
 */
val EasingList = mutableListOf(
    EasingOption(
        LinearEasing,
        "LinearEasing"
    ),
    EasingOption(
        FastOutSlowInEasing,
        "FastOutSlowInEasing"
    ),
    EasingOption(
        FastOutLinearInEasing,
        "FastOutLinearInEasing"
    ),
    EasingOption(
        LinearOutSlowInEasing,
        "LinearOutSlowInEasing"
    ),
    EasingOption(Ease, "Ease"),
    EasingOption(EaseIn, "EaseIn"),
    EasingOption(EaseOut, "EaseOut"),
    EasingOption(EaseInSine, "EaseInSine"),
    EasingOption(
        EaseOutSine,
        "EaseOutSine"
    ),
    EasingOption(
        EaseInOutSine,
        "EaseInOutSine"
    ),
    EasingOption(
        EaseInCubic,
        "EaseInCubic"
    ),
    EasingOption(
        EaseInOutCubic,
        "EaseInOutCubic"
    ),
    EasingOption(
        EaseInQuint,
        "EaseInQuint"
    ),
    EasingOption(
        EaseOutQuint,
        "EaseOutQuint"
    ),
    EasingOption(
        EaseInOutQuint,
        "EaseInOutQuint"
    ),
    EasingOption(EaseInCirc, "EaseInCirc"),
    EasingOption(
        EaseOutCirc,
        "EaseOutCirc"
    ),
    EasingOption(
        EaseInOutCirc,
        "EaseInOutCirc"
    ),
    EasingOption(EaseInQuad, "EaseInQuad"),
    EasingOption(
        EaseOutQuad,
        "EaseOutQuad"
    ),
    EasingOption(
        EaseInOutQuad,
        "EaseInOutQuad"
    ),
    EasingOption(
        EaseInQuart,
        "EaseInQuart"
    ),
    EasingOption(
        EaseOutQuart,
        "EaseOutQuart"
    ),
    EasingOption(
        EaseInOutQuart,
        "EaseInOutQuart"
    ),
    EasingOption(EaseInExpo, "EaseInExpo"),
    EasingOption(
        EaseOutExpo,
        "EaseOutExpo"
    ),
    EasingOption(
        EaseInOutExpo,
        "EaseInOutExpo"
    ),
    EasingOption(EaseInBack, "EaseInBack"),
    EasingOption(
        EaseOutBack,
        "EaseOutBack"
    ),
    EasingOption(
        EaseInOutBack,
        "EaseInOutBack"
    ),
    EasingOption(
        EaseInElastic,
        "EaseInElastic"
    ),
    EasingOption(
        EaseOutElastic,
        "EaseOutElastic"
    ),
    EasingOption(
        EaseInOutElastic,
        "EaseInOutElastic"
    ),
    EasingOption(
        EaseInBounce,
        "EaseInBounce"
    ),
    EasingOption(
        EaseOutBounce,
        "EaseOutBounce"
    ),
    EasingOption(
        EaseInOutBounce,
        "EaseInOutBounce"
    )
)