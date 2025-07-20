package pro.jayeshseth.animations.ui.playground.animationSpecs.tweenAndSpring

import androidx.compose.runtime.Stable
import pro.jayeshseth.animations.util.DampingRatioOption
import pro.jayeshseth.animations.util.EasingOption
import pro.jayeshseth.animations.util.StiffnessOption
import kotlin.enums.EnumEntries

@Stable
data class TweenAndSpringSpecState(
    val specs: EnumEntries<TweenNSpringSpec>,
    val selectedSpec: TweenNSpringSpec,
    val durationMillis: Int,
    val delayMillis: Int,
    val cubicA: Float,
    val cubicB: Float,
    val cubicC: Float,
    val cubicD: Float,
    val easingList: List<EasingOption>,
    val easing: EasingOption,
    val dampingRatioList: List<DampingRatioOption>,
    val dampingRatio: DampingRatioOption,
    val stiffnessList: List<StiffnessOption>,
    val stiffness: StiffnessOption,
    val useCustomEasing: Boolean = false,
    val useCustomSpring: Boolean = false,
    val applySpecToPath: Boolean = false,
    val customDampingRatio: Float = 0.1f,
    val customStiffness: Float = 100f,
    val useCustomDampingRatioAndStiffness: Boolean = false,
)
