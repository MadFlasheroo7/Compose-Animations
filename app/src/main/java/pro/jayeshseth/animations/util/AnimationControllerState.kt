package pro.jayeshseth.animations.util

import androidx.compose.animation.core.Easing
import androidx.compose.runtime.Stable

private typealias EasingPair = Pair<Easing, String>
private typealias DampingRatioPair = Pair<Float, String>
private typealias StiffnessPair = Pair<Float, String>

@Stable
data class AnimationControllerState(
    val shepardTone: Boolean,
    val vibrationEffect: Boolean,
    val blurEffect: Boolean,
    val showShadow: Boolean,
    val initialValue: Float,
    val initialValueSteps: Float,
    val initialValueRange: ClosedFloatingPointRange<Float>,
    val blurValue: Float,
    val blurValueSteps: Float,
    val blurValueRange: ClosedFloatingPointRange<Float>,
    val tweenDuration: Int,
    val selectedIndex: Int,
    val easingList: List<EasingPair>,
    val easing: EasingPair,
    val dampingRatioList: List<DampingRatioPair>,
    val dampingRatio: DampingRatioPair,
    val stiffnessList: List<StiffnessPair>,
    val stiffness: StiffnessPair,
)