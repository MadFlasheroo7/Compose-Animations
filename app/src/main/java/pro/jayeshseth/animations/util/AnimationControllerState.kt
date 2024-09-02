package pro.jayeshseth.animations.util

import androidx.compose.animation.core.Easing

private typealias EasingPair = Pair<Easing, String>
private typealias DampingRatioPair = Pair<Float, String>
private typealias StiffnessPair = Pair<Float, String>

data class AnimationControllerState(
    val shepardTone: Boolean,
    val vibrationEffect: Boolean,
    val showShadow: Boolean,
    val initialValue: Float,
    val initialValueSteps: Float,
    val initialValueRange: ClosedFloatingPointRange<Float>,
    val tweenDuration: Int,
    val selectedIndex: Int,
    val easingList: List<EasingPair>,
    val easing: EasingPair,
    val dampingRatioList: List<DampingRatioPair>,
    val dampingRatio: DampingRatioPair,
    val stiffnessList: List<StiffnessPair>,
    val stiffness: StiffnessPair,
)