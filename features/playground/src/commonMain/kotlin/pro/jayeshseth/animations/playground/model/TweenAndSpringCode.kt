package pro.jayeshseth.animations.playground.model

import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.runtime.Immutable
import kotlin.math.roundToInt

@Immutable
class TweenAndSpringCode(
    val state: TweenAndSpringSpecState,
) {
    val dampingRatioString =
        if (state.useCustomDampingRatioAndStiffness) formatFloat(state.customDampingRatio) else when (state.dampingRatio.dampingRatio) {
            Spring.DampingRatioHighBouncy -> "Spring.DampingRatioHighBouncy"
            Spring.DampingRatioMediumBouncy -> "Spring.DampingRatioMediumBouncy"
            Spring.DampingRatioLowBouncy -> "Spring.DampingRatioLowBouncy"
            Spring.DampingRatioNoBouncy -> "Spring.DampingRatioNoBouncy"
            else -> formatFloat(state.customDampingRatio)
        }
    val stiffnessString =
        if (state.useCustomDampingRatioAndStiffness) formatFloat(state.customStiffness) else when (state.stiffness.stiffness) {
            Spring.StiffnessVeryLow -> "Spring.StiffnessVeryLow"
            Spring.StiffnessLow -> "Spring.StiffnessLow"
            Spring.StiffnessMedium -> "Spring.StiffnessMedium"
            Spring.StiffnessHigh -> "Spring.StiffnessHigh"
            else -> formatFloat(state.customStiffness)
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

private fun formatFloat(value: Float): String {
    return "${value.roundToDecimal(2)}f"
}

fun Float.roundToDecimal(decimals: Int): String {
    var multiplier = 1.0
    repeat(decimals) { multiplier *= 10 }
    val rounded = (this * multiplier).roundToInt() / multiplier
    return rounded.toString()
}