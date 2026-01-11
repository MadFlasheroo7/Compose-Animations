package pro.jayeshseth.animations.core.model

import androidx.compose.runtime.Stable

/**
 * Represents the state of the animation controller UI.
 *
 * This data class holds all the configurable parameters that define how an animation
 * behaves and appears. It is marked as [Stable] to allow for efficient recomposition
 * by the Compose runtime.
 *
 * @property delay The delay in milliseconds before the animation starts.
 * @property shepardTone If `true`, a Shepard tone effect is enabled during the animation.
 * @property vibrationEffect If `true`, a vibration effect is triggered with the animation.
 * @property blurEffect If `true`, a blur effect is applied to the animated element.
 * @property showShadow If `true`, a shadow is rendered behind the animated element.
 * @property initialValue The starting value for the animation property (e.g., scale, position).
 * @property initialValueSteps The number of discrete steps for the initial value slider.
 * @property initialValueRange The allowed range for the [initialValue].
 * @property blurValue The magnitude of the blur effect, if enabled.
 * @property blurValueSteps The number of discrete steps for the blur value slider.
 * @property blurValueRange The allowed range for the [blurValue].
 * @property tweenDuration The duration of the animation in milliseconds for tween-based animations.
 * @property selectedIndex The index of the currently selected animation type (e.g., Tween, Spring).
 * @property easingList The list of available easing functions for tween animations.
 * @property easing The currently selected [EasingOption] for tween animations.
 * @property dampingRatioList The list of available damping ratio options for spring animations.
 * @property dampingRatio The currently selected [DampingRatioOption] for spring animations.
 * @property stiffnessList The list of available stiffness options for spring animations.
 * @property stiffness The currently selected [StiffnessOption] for spring animations.
 */
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