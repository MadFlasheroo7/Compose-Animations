package pro.jayeshseth.animations.playground.model

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.runtime.Immutable
import kotlin.text.format

@Immutable
class TweenAndSpringCode(
    val state: TweenAndSpringSpecState,
) {
    val dampingRatioString =
        if (state.useCustomDampingRatioAndStiffness) "%.2ff".format(state.customDampingRatio) else when (state.dampingRatio.dampingRatio) {
            Spring.DampingRatioHighBouncy -> "Spring.DampingRatioHighBouncy"
            Spring.DampingRatioMediumBouncy -> "Spring.DampingRatioMediumBouncy"
            Spring.DampingRatioLowBouncy -> "Spring.DampingRatioLowBouncy"
            Spring.DampingRatioNoBouncy -> "Spring.DampingRatioNoBouncy"
            else -> "%.2ff".format(state.customDampingRatio)
        }
    val stiffnessString =
        if (state.useCustomDampingRatioAndStiffness) "%.2ff".format(state.customStiffness) else when (state.stiffness.stiffness) {
            Spring.StiffnessVeryLow -> "Spring.StiffnessVeryLow"
            Spring.StiffnessLow -> "Spring.StiffnessLow"
            Spring.StiffnessMedium -> "Spring.StiffnessMedium"
            Spring.StiffnessHigh -> "Spring.StiffnessHigh"
            else -> "%.2ff".format(state.customStiffness)
        }

    fun tweenCode(): String {
        return """
            tween(
                durationMillis = ${state.durationMillis},
                delayMillis = ${state.delayMillis},
                easing = ${
            if (state.useCustomEasing.not()) state.easing.name else CubicBezierEasing(
                state.cubicA,
                state.cubicB,
                state.cubicC,
                state.cubicD,
            )
        }
            )
        """.trimIndent()
    }

    fun springCode(): String {
        return """
            spring(
                dampingRatio = $dampingRatioString,
                stiffness = $stiffnessString
            )
        """.trimIndent()
    }
}