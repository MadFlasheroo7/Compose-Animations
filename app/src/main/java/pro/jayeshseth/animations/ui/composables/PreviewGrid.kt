package pro.jayeshseth.animations.ui.composables

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import pro.jayeshseth.animations.core.ui.components.CatImage
import pro.jayeshseth.animations.ui.playground.animationSpecs.tweenAndSpring.TweenAndSpringSpecState
import pro.jayeshseth.animations.ui.playground.animationSpecs.tweenAndSpring.TweenNSpringSpec

@Composable
fun PreviewGrid(
    state: TweenAndSpringSpecState,
    replay: Boolean,
    modifier: Modifier = Modifier
) {
    val scale = remember { Animatable(0f) }
    val rotation = remember { Animatable(0f) }
    val opacity = remember { Animatable(0f) }
    val saturation = remember { Animatable(0f) }

    val easing by remember(state, replay) {
        mutableStateOf(
            if (state.useCustomEasing) CubicBezierEasing(
                state.cubicA,
                state.cubicB,
                state.cubicC,
                state.cubicD
            )
            else state.easing.easing
        )
    }
    val dampingRatio by remember(state, replay) {
        mutableFloatStateOf(
            if (state.useCustomDampingRatioAndStiffness) state.customDampingRatio
            else state.dampingRatio.dampingRatio
        )
    }
    val stiffness by remember(state, replay) {
        mutableFloatStateOf(
            if (state.useCustomDampingRatioAndStiffness) state.customStiffness
            else state.stiffness.stiffness
        )
    }

    if (state.selectedSpec == TweenNSpringSpec.Tween) {
        // tween
        LaunchedEffect(state, replay) {
            scale.snapTo(0f)
            scale.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = state.durationMillis,
                    delayMillis = state.delayMillis,
                    easing = easing
                )
            )
        }
        LaunchedEffect(state, replay) {
            rotation.snapTo(0f)
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = tween(
                    durationMillis = state.durationMillis,
                    delayMillis = state.delayMillis,
                    easing = easing
                )
            )
        }
        LaunchedEffect(state, replay) {
            opacity.snapTo(0f)
            opacity.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = state.durationMillis,
                    delayMillis = state.delayMillis,
                    easing = easing
                )
            )
        }
        LaunchedEffect(state, replay) {
            saturation.snapTo(0f)
            saturation.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = state.durationMillis,
                    delayMillis = state.delayMillis,
                    easing = easing
                )
            )
        }
    } else {
        LaunchedEffect(state, replay) {
            scale.snapTo(0f)
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = dampingRatio,
                    stiffness = stiffness
                )
            )
        }
        LaunchedEffect(state, replay) {
            rotation.snapTo(0f)
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = spring(
                    dampingRatio = dampingRatio,
                    stiffness = stiffness
                )
            )
        }
        LaunchedEffect(state, replay) {
            opacity.snapTo(0f)
            opacity.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = dampingRatio,
                    stiffness = stiffness
                )
            )
        }
        LaunchedEffect(state, replay) {
            saturation.snapTo(0f)
            saturation.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = dampingRatio,
                    stiffness = stiffness
                )
            )
        }
    }

    Column(
        modifier = modifier.aspectRatio(1f)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            // scale
            BorderedBox {
                CatImage(
                    modifier = Modifier
                        .graphicsLayer {
                            scaleX = scale.value
                            scaleY = scale.value
                        }
                        .padding(30.dp)
                )
            }

            // rotate
            BorderedBox {
                CatImage(
                    modifier = Modifier
                        .graphicsLayer { rotationZ = rotation.value }
                        .padding(30.dp)
                )
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            // alpha
            BorderedBox {
                CatImage(
                    modifier = Modifier
                        .graphicsLayer { alpha = opacity.value }
                        .padding(30.dp)
                )
            }

            // color filter
            BorderedBox {
                CatImage(
                    modifier = Modifier
                        .graphicsLayer {
                            colorFilter =
                                ColorFilter.colorMatrix(
                                    ColorMatrix()
                                        .apply { setToSaturation(saturation.value) }
                                )
                        }
                        .padding(30.dp)
                )
            }
        }
    }
}


@Composable
private fun BorderedBox(
    modifier: Modifier = Modifier,
    content: @Composable (BoxScope.() -> Unit)
) {
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .border(
                BorderStroke(2.dp, Color.Black),
            ),
        contentAlignment = Alignment.Center,
        content = content
    )
}
