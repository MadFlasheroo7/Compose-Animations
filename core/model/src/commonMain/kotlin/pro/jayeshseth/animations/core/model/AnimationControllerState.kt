package pro.jayeshseth.animations.core.model

import androidx.compose.runtime.Stable

@Stable
data class AnimationControllerState(
    val delay: Long,
    val shepardTone: Boolean,
    val vibrationEffect: Boolean,
    val blurEffect: Boolean,
    val showShadow: Boolean,
    val initialValue: Float,
    val initialValueSteps: Int,
    val initialValueRange: ClosedFloatingPointRange<Float>,
    val blurValue: Float,
    val blurValueSteps: Int,
    val blurValueRange: ClosedFloatingPointRange<Float>,
    val tweenDuration: Int,
    val selectedIndex: Int,
    val easingList: List<EasingOption>,
    val easing: EasingOption,
    val dampingRatioList: List<DampingRatioOption>,
    val dampingRatio: DampingRatioOption,
    val stiffnessList: List<StiffnessOption>,
    val stiffness: StiffnessOption,
)