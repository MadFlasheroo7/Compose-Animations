package pro.jayeshseth.animations.core.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.EaseInCubic
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitHorizontalTouchSlopOrCancellation
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.draw.innerShadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import kotlinx.coroutines.launch
import pro.jayeshseth.animations.core.model.ComposeFriendlyFloat
import pro.jayeshseth.animations.core.model.ComposeFriendlyInt
import pro.jayeshseth.animations.core.ui.modifiers.shimmerBorder
import pro.jayeshseth.animations.core.ui.theme.AnimationsTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StretchySlider(
    hazeState: HazeState,
    value: ComposeFriendlyFloat,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = Color.Cyan,
    steps: ComposeFriendlyInt = { 0 },
    height: Dp = 60.dp,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    val density = LocalDensity.current
    val isLtr = LocalLayoutDirection.current == LayoutDirection.Ltr

    var scaleX by remember { mutableFloatStateOf(1f) }
    var scaleY by remember { mutableFloatStateOf(1f) }
    var translateX by remember { mutableFloatStateOf(0f) }
    var transformOrigin by remember { mutableStateOf(TransformOrigin.Center) }
    val isDragged by interactionSource.collectIsDraggedAsState()

    Slider(
        value = value(),
        onValueChange = onValueChange,
        steps = steps(),
        valueRange = valueRange,
        interactionSource = interactionSource,
        modifier = modifier
            .graphicsLayer {
                this.transformOrigin = transformOrigin
                this.scaleX = scaleX
                this.scaleY = scaleY
                this.translationX = translateX
            }
            .padding(4.dp),
//            .padding(horizontal = 35.dp),
        thumb = {},
        track = { sliderState ->
            val isValueReachedMax by remember(sliderState.value) {
                derivedStateOf {
                    sliderState.value >= valueRange.endInclusive - 0.01f
                }
            }
            val steps by remember {
                derivedStateOf { sliderState.steps }
            }
            val sliderFraction by remember {
                derivedStateOf {
                    (sliderState.value - sliderState.valueRange.start) / (sliderState.valueRange.endInclusive - sliderState.valueRange.start)
                }
            }

            val outerRadius by animateDpAsState(
                targetValue = if (isValueReachedMax) 50.dp else 12.dp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow,
                ),
            )

            val innerRadius by animateDpAsState(
                targetValue = if (isValueReachedMax) 50.dp else 16.dp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow,
                ),
            )

            val alpha by animateFloatAsState(
                targetValue = if (isValueReachedMax) 1f else 0f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow,
                ),
            )

            val spread by animateDpAsState(
                targetValue = if (isValueReachedMax) (if (height > 35.dp) 10.dp else 5.dp) else if (height < 35.dp) 2.dp else 5.dp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow,
                ),
            )

            val radius by animateDpAsState(
                targetValue = if (isValueReachedMax) (if (height > 35.dp) 10.dp else 5.dp) else if (height < 35.dp) 2.dp else 5.dp,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow,
                ),
            )

            val shadowColor by animateColorAsState(
                targetValue = if (isValueReachedMax) color else color.copy(.40f),
                animationSpec = tween(500),
                label = "animated shadow color",
            )

            val animatedSliderFraction by animateFloatAsState(
                targetValue = sliderFraction,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessLow,
                ),
                label = "animatedSliderFraction"
            )

            val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
            val animatedShimmer by infiniteTransition.animateFloat(
                initialValue = -2f,
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(5500, easing = EaseInCubic),
                    repeatMode = RepeatMode.Restart
                ),
                label = "translate"
            )

            val innerShape = RoundedCornerShape(innerRadius)
            val outerShape = RoundedCornerShape(outerRadius)

            Box(
                modifier = Modifier
                    .trackOverslide(value = sliderFraction) { overslide ->
                        transformOrigin = TransformOrigin(
                            pivotFractionX = when (isLtr) {
                                true -> if (sliderFraction < .5f) 2f else -1f
                                false -> if (sliderFraction < .5f) -1f else 2f
                            },
                            pivotFractionY = .5f,
                        )

                        when (sliderFraction) {
                            in 0f..(.5f) -> {
                                scaleY = 1f + (overslide * .2f)
                                scaleX = 1f - (overslide * .2f)
                            }

                            else -> {
                                scaleY = 1f - (overslide * .2f)
                                scaleX = 1f + (overslide * .2f)
                            }
                        }

                        translateX = overslide * with(density) { 24.dp.toPx() }
                    }
                    .fillMaxWidth()
                    .height(height)
                    .shimmerBorder(
                        cornerRadius = outerRadius,
                        animatedTranslation = animatedShimmer,
                        borderWidth = if (isValueReachedMax) 10f else 4f
                    )
                    .border(
                        width = 1.dp,
                        color = shadowColor,
                        shape = innerShape
                    )
                    .padding(4.dp)
                    .dropShadow(innerShape) {
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                color.copy(alpha = 1f),
                                color.copy(alpha = .2f),
                                color.copy(alpha = .4f),
                                color.copy(alpha = .6f),
                                color.copy(alpha = .8f),
                                color.copy(alpha = 1f),
                            )
                        )
                        this.radius = 50.dp.toPx()
                        this.spread = 10f
                        this.alpha = alpha
                    }
                    .clip(shape = outerShape)
                    .hazeEffect(
                        hazeState,
                        HazeStyle(
                            tint = HazeTint(Color.LightGray.copy(.2f)),
                            blurRadius = 40.dp,
                            noiseFactor = 0.1f,
                        )
                    )
                    .innerShadow(
                        RoundedCornerShape(
                            0.dp
                        )
//                        outerShape
                    ) {
//                        alpha = .5f
                        this.radius = 40f
                        this.spread = 20f
                        this.color = Color.LightGray.copy(.2f)
                    }
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    val animatedAlpha by animateFloatAsState(
                        targetValue = if (isDragged) 1f else 0f,
                        animationSpec = tween(durationMillis = 1000)
                    )
                    key(steps) {
                        repeat(steps) {
                            Box(
                                Modifier
                                    .size(4.dp)
                                    .clip(CircleShape)
                                    .graphicsLayer {
                                        this.alpha = animatedAlpha
                                    }
                                    .background(Color.LightGray.copy(.7f))

                            )
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth(animatedSliderFraction)
                        .fillMaxHeight()
                        .clip(outerShape)
                        .hazeEffect(
                            hazeState,
                            HazeStyle(
                                tint = HazeTint(Color.Black.copy(.5f)),
                                blurRadius = 40.dp,
                                noiseFactor = 0.7f,
                            )
                        )
                        .innerShadow(outerShape) {
                            this.color = shadowColor
                            this.radius = radius.toPx()
                            this.spread = spread.toPx()
                        }
                )
            }
        },
    )

}

@Composable
fun Modifier.trackOverslide(
    value: Float,
    onNewOverslideAmount: (Float) -> Unit,
): Modifier {

    val valueState = rememberUpdatedState(value)
    val scope = rememberCoroutineScope()
    val overslideAmountAnimatable = remember { Animatable(0f, .0001f) }
    var length by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(Unit) {
        snapshotFlow { overslideAmountAnimatable.value }.collect {
            onNewOverslideAmount(CustomEasing.transform(it / length))
        }
    }

    val isLtr = LocalLayoutDirection.current == LayoutDirection.Ltr

    return onSizeChanged { length = it.width.toFloat() }
        .pointerInput(Unit) {
            awaitEachGesture {
                val down = awaitFirstDown()
                // User has touched the screen

                awaitHorizontalTouchSlopOrCancellation(down.id) { _, _ -> }
                // User has moved the minimum horizontal amount to recognize a drag

                var overslideAmount = 0f

                // Start tracking horizontal drag amount
                horizontalDrag(down.id) {
                    // Negate the change in X when Rtl language is used
                    val deltaX = it.positionChange().x * if (isLtr) 1f else -1f

                    // Clamp overslide amount
                    overslideAmount = when (valueState.value) {
                        0f -> (overslideAmount + deltaX).coerceAtMost(0f)
                        1f -> (overslideAmount + deltaX).coerceAtLeast(1f)
                        else -> 0f
                    }

                    // Animate to new overslide amount
                    scope.launch {
                        overslideAmountAnimatable.animateTo(overslideAmount)
                    }
                }
                // User has lifted finger off the screen
                // Drag has stopped

                // Animate overslide to 0, with a bounce
                scope.launch {
                    overslideAmountAnimatable.animateTo(
                        targetValue = 0f,
                        animationSpec = spring(
                            dampingRatio = .45f,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
            }
        }
}

@Preview
@Composable
private fun PreviewStretchySlider() {
    var value by remember { mutableFloatStateOf(50f) }
    val cardStyle = HazeStyle(
        tint = HazeTint(Color.Black.copy(.4f)),
        blurRadius = 50.dp,
        noiseFactor = 0.1f,
    )
    AnimationsTheme {
        ShaderPreviewContent {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val interactionSource = remember { MutableInteractionSource() }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .hazeEffect(
                            it,
                            cardStyle
                        )
                        .padding(vertical = 50.dp, horizontal = 50.dp)
                ) {
                    Text(
                        "${value.toInt()}",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    StretchySlider(
                        value = { value },
                        valueRange = 0f..100f,
                        hazeState = it,
                        interactionSource = interactionSource,
                        onValueChange = {
                            value = it
                        }
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .hazeEffect(
                            it,
                            cardStyle
                        )
                        .padding(vertical = 50.dp, horizontal = 50.dp)
                ) {
                    StretchySlider(
                        value = { value },
                        interactionSource = interactionSource,
                        color = Color.White,
                        steps = { 10 },
                        valueRange = 0f..100f,
                        height = 29.dp,
                        hazeState = it,
                        onValueChange = {
                            value = it
                        }
                    )
                }
            }
        }
    }
}

val CustomEasing: Easing = CubicBezierEasing(0.5f, 0.5f, 1.0f, 0.25f)